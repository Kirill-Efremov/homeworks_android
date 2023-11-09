package ru.kpfu.homewroks.ui.holder

import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.homeworks.databinding.ItemButtonBinding
import ru.kpfu.homewroks.adapter.BookAdapter
import ru.kpfu.homewroks.ui.BottomSheetFragment

class ButtonItem (
    private val viewBinding: ItemButtonBinding,
    private val fragmentManager: FragmentManager,
    private val adapter: BookAdapter
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bindItem() {
        with(viewBinding){
            btnAddBook.setOnClickListener {
                val bottomSheetFragment = BottomSheetFragment(adapter)
                bottomSheetFragment.show(fragmentManager, bottomSheetFragment.tag)
            }
        }
    }
}