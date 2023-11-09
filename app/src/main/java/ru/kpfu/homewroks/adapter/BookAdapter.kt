package ru.kpfu.homewroks.adapter


import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import ru.kpfu.homeworks.databinding.ItemBookBinding
import ru.kpfu.homeworks.databinding.ItemButtonBinding
import ru.kpfu.homeworks.databinding.ItemDateBinding
import ru.kpfu.homewroks.adapter.diffutils.BookNewsUtils
import ru.kpfu.homewroks.model.BookModel
import ru.kpfu.homewroks.model.ButtonModel
import ru.kpfu.homewroks.model.DateModel
import ru.kpfu.homewroks.model.ItemModel
import ru.kpfu.homewroks.ui.holder.BookItem
import ru.kpfu.homewroks.ui.holder.ButtonItem
import ru.kpfu.homewroks.ui.holder.DateItem
import java.util.Date
import java.util.Locale


class BookAdapter(
    private val onNewsClicked: ((BookModel) -> Unit),
    private val fragmentManager: FragmentManager,
    private val onLikeClicked: ((Int, BookModel) -> Unit),
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var newsList = mutableListOf<ItemModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            BOOK_TYPE -> {
                BookItem(
                    viewBinding = ItemBookBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    ),
                    onNewsClicked,
                    onLikeClicked
                )
            }

            DATE_TYPE -> {
                DateItem(
                    viewBinding = ItemDateBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    )
                )
            }

            BUTTON_TYPE -> {
                ButtonItem(
                    viewBinding = ItemButtonBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent, false
                    ), fragmentManager = fragmentManager, this
                )
            }


            else -> throw RuntimeException()
        }
    }


    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BookItem -> {
                holder.bindItem(newsList[position] as BookModel)
            }

            is ButtonItem -> {
                holder.bindItem()
            }

            is DateItem -> {
                newsList[position].let {
                    holder.bindItem(
                        SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.getDefault()
                        ).format(Date())
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            (payloads.first() as? Boolean)?.let {
                (holder as? BookItem)?.changeLikeBtnStatus(it)
            }
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemViewType(position: Int): Int {
        return when (newsList[position]) {
            is BookModel -> BOOK_TYPE
            is ButtonModel -> BUTTON_TYPE
            is DateModel -> DATE_TYPE
            else -> throw RuntimeException()
        }
    }

    fun setItems(list: List<ItemModel>) {
        val diff = BookNewsUtils(oldItemsList = newsList, newItemsList = list)
        val diffResult = DiffUtil.calculateDiff(diff)
        newsList.clear()
        newsList.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    fun updateItem(position: Int, item: BookModel) {
        this.newsList[position] = item
        notifyItemChanged(position, item.isLiked)

    }

    companion object {
        private const val BOOK_TYPE = 0
        private const val DATE_TYPE = 1
        private const val BUTTON_TYPE = 2

    }


}