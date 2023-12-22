package ru.itis.homework.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework.data.db.entity.LikedFilmEntity
import ru.itis.homework.di.ServiceLocator
import ru.itis.homework.model.FavoriteFilmModel
import ru.itis.homework.model.FilmModel
import ru.itis.homework.model.ItemModel
import ru.itis.homework.ui.adapter.FilmAdapter
import ru.itis.homework.utils.FilmMapper
import ru.itis.homework.utils.UserSession
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentMainBinding


class MainFragment : Fragment(R.layout.fragment_main) {
    private var _viewBinding: FragmentMainBinding? = null
    private val viewBinding: FragmentMainBinding get() = _viewBinding!!
    private var filmAdapter: FilmAdapter? = null
    private val filmsListAdapter = mutableListOf<ItemModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentMainBinding.inflate(inflater)
        return viewBinding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.main_bottom_navigation)?.visibility =
            View.VISIBLE
        initRecyclerView()
        checkEmpty()
    }

    private fun initRecyclerView() {

        lifecycleScope.launch(Dispatchers.IO) {
            setFilmListAdapter()
            withContext(Dispatchers.Main) {
                with(viewBinding) {
                    filmAdapter = FilmAdapter(
                        onDeleteClicked = ::onDeleteClicked,
                        onFilmClicked = ::onFilmClicked,
                        lifecycleScope = lifecycleScope,
                        onLikeClicked = ::onLikeClicked,
                        context = requireContext(),
                    )
                    if(filmsListAdapter.isEmpty()){
                        viewBinding.noFilmsTv.visibility = View.VISIBLE
                    }
                    val filmLayoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    allFilmsRv.layoutManager = filmLayoutManager
                    allFilmsRv.adapter = filmAdapter
                    filmAdapter?.setItems(filmsListAdapter)
                }
            }
        }
    }

    private fun onDeleteClicked(filmModel: FilmModel) {
        lifecycleScope.launch(Dispatchers.IO) {
            ServiceLocator.getDbInstance().filmDao.deleteFilm(filmModel.id)
        }
        setFilmListAdapter()
        filmAdapter?.setItems(filmsListAdapter)
        filmAdapter?.deleteItem(filmModel)
        checkEmpty()


    }

    private fun onFilmClicked(filmModel: FilmModel) {
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.main_activity_container, FilmDetailFragment.newInstance(filmModel.id)
            ).addToBackStack(null).commit()
    }

    private fun onLikeClicked( position : Int , filmModel: FilmModel) {
        println(position)
        lifecycleScope.launch(Dispatchers.IO) {
            val likedFilmEntity =
                LikedFilmEntity(id = 0, userId = UserSession.getUserId(), filmId = filmModel.id)
            val checkFilm =
                ServiceLocator.getDbInstance().likedFilmDao.getLikedFilmsByFilmUser(likedFilmEntity.filmId, likedFilmEntity.userId)
                    ?: -1
            if (checkFilm == -1) { println("Add to like")
                ServiceLocator.getDbInstance().likedFilmDao.addLikedFilm(likedFilmEntity)
                if (filmsListAdapter.none { it is FavoriteFilmModel }) {
                    println("go to add 0 FavModel")
                    filmsListAdapter.add(0, FavoriteFilmModel)

                    withContext(Dispatchers.Main) {
                        setFilmListAdapter()
                        filmAdapter?.setItems(filmsListAdapter)
                    }
                }
            } else {
                println("go go 1 ")
                ServiceLocator.getDbInstance().likedFilmDao.deleteLikedFilm(
                    likedFilmEntity.userId, likedFilmEntity.filmId)
                println(ServiceLocator.getDbInstance().likedFilmDao.getLikedFilmsByUser(UserSession.getUserId()).toString())
                    if (ServiceLocator.getDbInstance().likedFilmDao.getLikedFilmsByUser(UserSession.getUserId())
                            ?.isEmpty() == true && !filmsListAdapter.none { it is FavoriteFilmModel }
                    ) {
                    println("go go 2 ")

                        withContext(Dispatchers.Main) {
                            println("go go трарарара  ")
                            setFilmListAdapter()
                            filmAdapter?.setItems(filmsListAdapter)
                        }
                }
            }
        }
        setFilmListAdapter()
        filmAdapter?.setItems(filmsListAdapter)
        filmAdapter?.likeItemFromLikedFilms(position = position)


    }

    private fun setFilmListAdapter() {
        lifecycleScope.launch(Dispatchers.IO) {
            val userId = UserSession.getUserId()
            val listFavFilmId =
                ServiceLocator.getDbInstance().likedFilmDao.getLikedFilmsByUser(userId)
            filmsListAdapter.clear()
            if (listFavFilmId != null) {
                if (listFavFilmId.isNotEmpty()) {
                    println(listFavFilmId.size.toString() + "adasda")
                    filmsListAdapter.add(FavoriteFilmModel)
                }
            }
            val filmsListFromDb = ServiceLocator.getDbInstance().filmDao.getAllFilms()
            if (filmsListFromDb != null) {
                if (filmsListFromDb.isNotEmpty()) {
                    filmsListAdapter.addAll(filmsListFromDb.map {
                        FilmMapper.filmModelFromFilmEntity(
                            it
                        )
                    }.toMutableList())
                }
            }
        }
    }
    private fun checkEmpty(){
        if (filmsListAdapter.isEmpty()) {
            viewBinding.noFilmsTv.visibility = View.VISIBLE}
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
        filmAdapter = null
    }

}