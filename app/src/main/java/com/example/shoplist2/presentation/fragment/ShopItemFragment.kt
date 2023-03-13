package com.example.shoplist2.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist2.R
import com.example.shoplist2.domain.ShopList
import com.example.shoplist2.presentation.secondActivity.viewmodel.ViewModelSecondActivity
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etCount: EditText
    private lateinit var etName: EditText
    private lateinit var buttonSave: Button

    private var screenMode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopList.UNDEFINE_ID

    private lateinit var onEditingFinishingListener: OnEditingFinishingListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnEditingFinishingListener) {
            onEditingFinishingListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }


    private lateinit var viewModel: ViewModelSecondActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.shop_item_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[ViewModelSecondActivity::class.java]

        initViews(view)
        addTextChangeListeners()
        showError()
        launchRightMode()

    }

    private fun launchRightMode() {
        when (screenMode) {
            EDIT_MODE -> launchEditMode()
            ADD_MODE -> launchAddMode()
        }
    }

    private fun showError() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val massage = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = massage
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val massage = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = massage
        }
        viewModel.shouldCloseActivity.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }


    private fun launchEditMode() {
        viewModel.getItemsId(shopItemId)
        viewModel.shopList.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }
        buttonSave.setOnClickListener {
            viewModel.editItems(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        buttonSave.setOnClickListener {
            viewModel.addItem(etName.text?.toString(), etCount.text?.toString())
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etName = view.findViewById(R.id.et_name)
        etCount = view.findViewById(R.id.et_count)
        buttonSave = view.findViewById(R.id.save_button)
    }


    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("param screen mode absent")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if (screenMode == EDIT_MODE) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param shop item id is absent")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, ShopList.UNDEFINE_ID)
        }
        /* if (screenMode != EDIT_MODE && screenMode != ADD_MODE) {
             throw RuntimeException("param screen mode absent")
         }
         if (screenMode == EDIT_MODE && shopItemId == ShopList.UNDEFINE_ID) {
             throw RuntimeException("Param shop item id is absent")
         }
 */
    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    interface OnEditingFinishingListener {
        fun onEditingFinished()
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_screen_mode"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"

        private const val MODE_UNKNOWN = "mode_unknown"

        fun newInstanceAddItem(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, ADD_MODE)
                }
            }
        }

        fun newInstanceEditItem(shopItemId: Int): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, EDIT_MODE)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }
    }



}