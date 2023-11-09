package ru.kpfu.homewroks.ui.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.homeworks.databinding.ItemDateBinding
import java.util.Date

class DateItem(
    private val viewBinding : ItemDateBinding
): RecyclerView.ViewHolder(viewBinding.root) {
    fun bindItem(data: String) {
        with(viewBinding) {
            tvDate.text = data
        }
    }
}