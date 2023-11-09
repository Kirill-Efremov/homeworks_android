package ru.kpfu.homewroks.model

data class BookModel (
    val booksId: Int,
    val booksTitle: String,
    val booksAuth: String,
    val booksDescription: String,
    val bookImage: Int,
    var isLiked: Boolean = false,
): ItemModel()


