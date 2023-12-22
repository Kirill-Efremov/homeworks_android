package ru.itis.homework.ui.fragments.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.itis.homework.data.db.entity.FilmEntity
import ru.itis.homework.di.ServiceLocator
import ru.itis.homework.model.FilmModel
import ru.itis.homework.utils.FilmMapper
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentAddFilmBinding

class AddFilmFragment : Fragment(R.layout.fragment_add_film) {
    private val viewBinding by viewBinding(FragmentAddFilmBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(viewBinding) {
            actionBtn.setOnClickListener {
                val title = titleEt.text.toString()
                val desc = descEt.text.toString()
                val year = yearEt.text.toString()
                if (title.isEmpty() || desc.isEmpty() || year.isEmpty()) {
                    makeToast("Все поля должны быть заполнены")
                } else {
                    var checkFilm: FilmEntity?
                    lifecycleScope.launch(Dispatchers.IO) {
                        checkFilm =
                            ServiceLocator.getDbInstance().filmDao.getFilmByYearAndTitle(
                                year.toInt(),
                                title
                            )
                        when {
                            (year.toInt() <= 1000 || year.toInt() >= 2023) -> makeToast("Год должен быть от 1000 до 2023")
                            checkFilm != null -> makeToast("Фильм уже существует")
                            else -> {
                                makeToast("Фильм добавлен успешно")
                                val newFilm = FilmModel(id = 0, title = title, description = desc, year = year.toInt())
                                ServiceLocator.getDbInstance().filmDao.addFilm(FilmMapper.filmEntityFromFilmModel(newFilm)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    private fun makeToast(text: String) {
        activity?.runOnUiThread {
            Toast.makeText(
                context, text, Toast.LENGTH_SHORT
            ).show()
        }
    }
}