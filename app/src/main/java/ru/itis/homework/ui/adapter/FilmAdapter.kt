package ru.itis.homework.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import ru.itis.homework.model.FavoriteFilmModel
import ru.itis.homework.model.FilmModel
import ru.itis.homework.model.ItemModel
import ru.itis.homework.ui.holder.FilmHolder
import ru.itis.homework.ui.holder.LikedFilmHolder
import ru.kpfu.homeworks.databinding.ItemFavouriteFilmsBinding
import ru.kpfu.homeworks.databinding.ItemFilmBinding

class FilmAdapter(
    private val onDeleteClicked: (( FilmModel) -> Unit),
    private val onFilmClicked: ((FilmModel) -> Unit),
    private val lifecycleScope: LifecycleCoroutineScope,
    private val onLikeClicked: (( Int, FilmModel) -> Unit),
    private val context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var listAdapter = mutableListOf<ItemModel>()
    private var favouriteFilmHolder: LikedFilmHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        FILM_TYPE -> {
            FilmHolder(
                viewBinding = ItemFilmBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onDeleteClicked = onDeleteClicked,
                lifecycleScope = lifecycleScope,
                onLikeClicked = onLikeClicked,
            )
        }

        LIKE_TYPE -> {
            LikedFilmHolder(
                viewBinding = ItemFavouriteFilmsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onDeleteClicked = onDeleteClicked,
                onFilmClicked = onFilmClicked,
                onLikeClicked = onLikeClicked,
                lifecycleScope = lifecycleScope,
                context = context,
            )
        }
        else -> throw RuntimeException("No such view holder")
    }

    override fun getItemCount(): Int = listAdapter.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FilmHolder -> {
                holder.bindItem(listAdapter[position] as FilmModel)
                holder.itemView.setOnClickListener {
                    onFilmClicked.invoke(listAdapter[position] as FilmModel)
                }
            }
            is LikedFilmHolder -> {
                holder.bindItem()
                favouriteFilmHolder = holder

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (listAdapter[position]) {
            is FilmModel -> FILM_TYPE
            is FavoriteFilmModel -> LIKE_TYPE
            else -> throw RuntimeException("No such view type")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(list: List<ItemModel>) {
        listAdapter.clear()
        listAdapter.addAll(list)
        notifyDataSetChanged()
    }

    fun deleteItem( filmModel: FilmModel) {
        deleteItemById(filmModel.id)
        favouriteFilmHolder?.deleteFilmById(filmModel.id)
        notifyItemChanged(listAdapter.indexOfFirst { it is FavoriteFilmModel })
    }

    private fun deleteItemById(id: Int) {
        val position = listAdapter.indexOfFirst { it is FilmModel && it.id == id }
        if (position != -1) {
            listAdapter.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun likeItemFromLikedFilms(position: Int) {
        notifyItemChanged(0)
        favouriteFilmHolder?.onLikeCliked(position )
    }



    companion object {
        private const val FILM_TYPE = 0
        private const val LIKE_TYPE = 1
    }
}

