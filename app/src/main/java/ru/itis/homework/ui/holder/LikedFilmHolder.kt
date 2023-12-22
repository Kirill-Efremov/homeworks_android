package ru.itis.homework.ui.holder

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework.data.db.entity.FilmEntity
import ru.itis.homework.di.ServiceLocator
import ru.itis.homework.model.FilmModel
import ru.itis.homework.ui.adapter.FilmAdapter
import ru.itis.homework.utils.FilmMapper
import ru.itis.homework.utils.UserSession
import ru.kpfu.homeworks.databinding.ItemFavouriteFilmsBinding


class LikedFilmHolder(
    private val viewBinding: ItemFavouriteFilmsBinding,
    private val context: Context,
    private val lifecycleScope: LifecycleCoroutineScope,
    private val onDeleteClicked: (( FilmModel) -> Unit),
    private val onFilmClicked: ((FilmModel) -> Unit),
    private val onLikeClicked: ((Int,  FilmModel) -> Unit),
) : RecyclerView.ViewHolder(viewBinding.root) {
    private var listFilms = mutableListOf<FilmModel>()
    private var filmsAdapter: FilmAdapter ? = null
    fun bindItem() {
        lifecycleScope.launch(Dispatchers.IO) {
            val userId = UserSession.getUserId()
            val listFilmIds = ServiceLocator.getDbInstance().likedFilmDao.getLikedFilmsByUser(userId)
            var listFilmsFromDb =  mutableListOf<FilmEntity>()
            if (listFilmIds != null) {
                 listFilmsFromDb =
                     ServiceLocator.getDbInstance().filmDao.getFilmsByIds(
                         listFilmIds
                     ).toMutableList()
            }
            listFilms = listFilmsFromDb.map { FilmMapper.filmModelFromFilmEntity(it) }.toMutableList()
            withContext(Dispatchers.Main) {
                with(viewBinding) {
                    filmsAdapter = FilmAdapter(
                        onDeleteClicked = onDeleteClicked,
                        onFilmClicked = onFilmClicked,
                        lifecycleScope = lifecycleScope,
                        onLikeClicked = onLikeClicked,
                        context = context
                    )
                    filmsAdapter?.setItems(listFilms)
                    val layoutManager: RecyclerView.LayoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    favoriteFilmsRv.layoutManager = layoutManager
                    favoriteFilmsRv.adapter = filmsAdapter
                }
            }
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun deleteFilmById(filmId: Int) {
        val position = listFilms.indexOfFirst { it.id == filmId }
        if (position != -1) {
            listFilms.removeAt(position)
            filmsAdapter?.notifyItemRemoved(position)
            filmsAdapter?.notifyDataSetChanged()
        }

    }

    fun onLikeCliked(position: Int) {
        filmsAdapter?.notifyItemChanged(position)
    }


}