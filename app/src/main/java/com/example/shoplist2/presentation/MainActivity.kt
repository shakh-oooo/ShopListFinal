package com.example.shoplist2.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist2.R
import com.example.shoplist2.presentation.fragment.ShopItemFragment
import com.example.shoplist2.presentation.rcview.ShopListAdapter
import com.example.shoplist2.presentation.secondActivity.ShopItemActivity
import com.example.shoplist2.presentation.viewmodel.MainViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishingListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var adaptervar: ShopListAdapter

    private var shopItemContainer: FragmentContainerView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shopItemContainer = findViewById(R.id.shop_item_container)

        setupRcView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            adaptervar.submitList(it)
        }

        val buttonAdd = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAdd.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.newIntentAddItem(this)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    override fun onEditingFinished() {
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    private fun launchFragment(fragment: Fragment) {
        supportFragmentManager.popBackStack() //закрыть все фрагменты
        supportFragmentManager.beginTransaction().replace(R.id.shop_item_container, fragment)
            .addToBackStack(null).commit()
    }


    private fun setupRcView() {
        val rcView = findViewById<RecyclerView>(R.id.rv_shop_list)
        adaptervar = ShopListAdapter()
        rcView.adapter = adaptervar

        rcView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.MAX_SIZE_POOL
        )
        rcView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.VIEW_TYPE_DISABLED, ShopListAdapter.MAX_SIZE_POOL
        )

        setupLongClick()
        setupClick()
        setupSwipe(rcView)

    }

    private fun setupSwipe(rcView: RecyclerView?) {
        val helper =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val item = adaptervar.currentList[viewHolder.adapterPosition]
                    viewModel.delete(item)
                }

            }
        val itemTouch = ItemTouchHelper(helper)
        itemTouch.attachToRecyclerView(rcView)
    }

    private fun setupLongClick() {
        adaptervar.setLongClickListener = {
            viewModel.editTrueFalse(it)
        }
    }

    private fun setupClick() {
        adaptervar.setOnClickListener = {
            if (isOnePaneMode()) {
                val newIntent = ShopItemActivity.newIntentEditItem(this, it.id)
                startActivity(newIntent)
            } else {
                launchFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
    }


}