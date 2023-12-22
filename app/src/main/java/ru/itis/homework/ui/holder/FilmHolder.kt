package ru.itis.homework.ui.holder

import android.annotation.SuppressLint
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework.di.ServiceLocator
import ru.itis.homework.model.FilmModel
import ru.kpfu.homeworks.databinding.ItemFilmBinding

@SuppressLint("ClickableViewAccessibility")
class FilmHolder(
    private val viewBinding: ItemFilmBinding,
    private val onDeleteClicked: ( FilmModel) -> Unit,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val onLikeClicked: (Int, FilmModel) -> Unit,
) : RecyclerView.ViewHolder(viewBinding.root) {
    private var item: FilmModel? = null

    init {
        viewBinding.deleteBtn.setOnClickListener {
            this.item?.let { onDeleteClicked( it) }
        }
        viewBinding.favBtn.setOnClickListener {
            this.item?.let {
                onLikeClicked( adapterPosition, it)
            }
        }
        viewBinding.rating.setOnTouchListener { _, _ -> true }
    }

    fun bindItem(item: FilmModel) {
        this.item = item
        lifecycleScope.launch(Dispatchers.IO) {
            val ratingFromDb = ServiceLocator.getDbInstance().ratingFilmDao.getFilmRating(item.id)
            withContext(Dispatchers.Main) {
                with(viewBinding) {
                    titleTv.text = item.title
                    if (ratingFromDb == null) {
                        rating.rating = 0.0F
                    } else {
                        rating.rating = ratingFromDb.toFloat()
                    }
                }
            }
        }
    }
}