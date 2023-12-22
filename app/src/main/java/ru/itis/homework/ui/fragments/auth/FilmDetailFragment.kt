package ru.itis.homework.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kpfu.itis.android_inception_23.utils.ParamsKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.itis.homework.data.db.entity.RatingFilmEntity
import ru.itis.homework.di.ServiceLocator
import ru.itis.homework.utils.FilmMapper
import ru.itis.homework.utils.UserSession
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentFilmDetailBinding

class FilmDetailFragment : Fragment() {
    private var _viewBinding: FragmentFilmDetailBinding? = null
    private val viewBinding: FragmentFilmDetailBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentFilmDetailBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.main_bottom_navigation)?.visibility =
            View.VISIBLE
        initViews()
    }


    private fun initViews() {
        val filmId = arguments?.getInt(ParamsKey.FILM_ID) ?: -1
        val userId = UserSession.getUserId()
        lifecycleScope.launch(Dispatchers.IO) {
            val film = FilmMapper.filmModelFromFilmEntity(
                ServiceLocator.getDbInstance().filmDao.getFilmInfoById(filmId)
            )
            val ratingFromDbByUser =
                ServiceLocator.getDbInstance().ratingFilmDao.getFilmRatingFromUser(filmId, userId)
            withContext(Dispatchers.Main) {
                with(viewBinding) {
                    tittleTv.text = film.title
                    descTv.text = film.description
                    yearTv.text = film.year.toString()
                    if (ratingFromDbByUser == null) {
                        changeRatingRb.rating = 0.0F
                    } else {
                        changeRatingRb.rating = ratingFromDbByUser.toFloat()
                    }
                    actionBtn.setOnClickListener {
                        val newRating = RatingFilmEntity(
                            id = 0,
                            userId = userId,
                            filmId = filmId,
                            rating = changeRatingRb.rating.toDouble()
                        )
                        lifecycleScope.launch(Dispatchers.IO) {
                            ServiceLocator.getDbInstance().ratingFilmDao.addRatingFilm(
                                newRating
                            )
                        }
                        parentFragmentManager.beginTransaction().replace(
                            R.id.main_activity_container, MainFragment()
                        ).commit()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null

    }

    companion object {
        fun newInstance(id: Int) = FilmDetailFragment().apply {
            arguments = bundleOf(ParamsKey.FILM_ID to id)
        }
    }
}