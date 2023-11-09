package ru.kpfu.homewroks.ui.holder

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.ItemBookBinding
import ru.kpfu.homewroks.model.BookModel
import ru.kpfu.homewroks.model.ItemModel

class BookItem (
    private val viewBinding: ItemBookBinding,
    private val onNewsClicked: ((BookModel) -> Unit),
    private val onLikeClicked: ((Int, BookModel) -> Unit),
) : RecyclerView.ViewHolder(viewBinding.root) {
    private var item: BookModel? = null

    init {
        viewBinding.root.setOnClickListener {
            this.item?.let(onNewsClicked)
        }
        viewBinding.ivLike.setOnClickListener {
            this.item?.let {
                val data = it.copy(isLiked = !it.isLiked)
                onLikeClicked(adapterPosition,data)
            }
        }
    }

    fun bindItem(item: BookModel) {
        this.item = item
        with(viewBinding){
            tvTitle.text = item.booksTitle
            tvAuth.text= item.booksAuth
            ivBook.setImageResource(item.bookImage)
            changeLikeBtnStatus(item.isLiked)
        }
    }
    fun changeLikeBtnStatus(isChecked: Boolean) {
        val likeDrawable = if (isChecked) R.drawable.baseline_heart_red else R.drawable.baseline_heart_black
        viewBinding.ivLike.setImageResource(likeDrawable)
    }
}