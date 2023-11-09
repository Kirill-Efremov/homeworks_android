package ru.kpfu.homewroks.adapter.diffutils

import androidx.recyclerview.widget.DiffUtil
import ru.kpfu.homewroks.model.BookModel
import ru.kpfu.homewroks.model.ItemModel

class BookNewsUtils(
    private val oldItemsList: List<ItemModel>,
    private val newItemsList: List<ItemModel>,
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItemsList.size

    override fun getNewListSize(): Int = newItemsList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        if (oldItem is BookModel && newItem is BookModel) {
            return oldItem.booksId == newItem.booksId
        }

        return false
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        if (oldItem is BookModel && newItem is BookModel) {
            return (oldItem.booksTitle == newItem.booksTitle) &&
                    (oldItem.booksDescription == newItem.booksDescription)
        }

        return false
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItemsList[oldItemPosition]
        val newItem = newItemsList[newItemPosition]

        if (oldItem is BookModel && newItem is BookModel) {
            if (oldItem.isLiked != newItem.isLiked) {
                return newItem.isLiked
            }
        }

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}