package com.example.shoplist2.presentation.secondActivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoplist2.R
import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.presentation.fragment.ShopItemFragment

class ShopItemActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishingListener {

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopList.UNDEFINE_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        parseIntent()

        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            EDIT_MODE -> ShopItemFragment.newInstanceEditItem(shopItemId)
            ADD_MODE -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("param screen mode absent")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("param screen mode absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, ShopList.UNDEFINE_ID)
        }
    }

    override fun onEditingFinished() {
        finish()
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_screen_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"

        private const val MODE_UNKNOWN = "mode_unknown"

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, ADD_MODE)
            return intent
        }

        fun newIntentEditItem(context: Context, idd: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, EDIT_MODE)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, idd)
            return intent
        }
    }


}