package ru.kpfu.homeworks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.homeworks.databinding.ItemBinding

class Holder(item: View) : RecyclerView.ViewHolder(item) {

     val viewBinding: ItemBinding = ItemBinding.bind(item)

    fun onBindNewsItem(item: Model, isFullWidth: Boolean, inputNumber: Int) {
        if (isFullWidth) {
            viewBinding.image.setImageResource(
                if (adapterPosition % 2 == 0) R.drawable.item1 else R.drawable.item6
            )
        } else if (isSpecialItem(inputNumber, adapterPosition)) {
            viewBinding.image.setImageResource(
                if (isSpecialType2(item)) R.drawable.item2
                else if (isSpecialType2(item)) R.drawable.item5
                else R.drawable.item3
            )
        } else {
            item.image?.let { res ->
                viewBinding.image.setImageResource(res)
            }
        }
    }

    private fun isSpecialItem(inputNumber: Int, position: Int): Boolean {
        val remainder = position % 6
        return (inputNumber % 6 == 3 && remainder == 1) ||
                (inputNumber % 6 == 4 && remainder == 1)
    }

    private fun isSpecialType2(item: Model): Boolean {
        return when (item.image) {
            R.drawable.item2, R.drawable.item5 -> true
            else -> false
        }
    }
}