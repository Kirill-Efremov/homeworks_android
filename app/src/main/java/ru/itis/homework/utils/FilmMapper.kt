package ru.itis.homework.utils

import ru.itis.homework.data.db.entity.FilmEntity
import ru.itis.homework.model.FilmModel

object FilmMapper {
    fun filmEntityFromFilmModel(filmModel: FilmModel) = FilmEntity(
        id = filmModel.id,
        title = filmModel.title,
        description = filmModel.description,
        year = filmModel.year
    )

    fun filmModelFromFilmEntity(filmEntity: FilmEntity) =  FilmModel(
        id = filmEntity.id,
        title = filmEntity.title,
        description = filmEntity.description,
        year = filmEntity.year
    )
}