package ru.kpfu.homewroks.utils

import ru.kpfu.homeworks.R
import ru.kpfu.homewroks.model.BookModel
import ru.kpfu.homewroks.model.ButtonModel
import ru.kpfu.homewroks.model.DateModel
import ru.kpfu.homewroks.model.ItemModel

object BookRepository {
    private val list = mutableListOf<ItemModel>()
    private var books = listOf<BookModel>()

    init {
        books = arrayListOf(
            BookModel(
                booksId = 1,
                booksTitle = "Book 1",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 2,
                booksTitle = "Book 2",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.baseline_check_circle_24
            ),
            BookModel(
                booksId = 3,
                booksTitle = "Book 3",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 4,
                booksTitle = "Book 4",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 5,
                booksTitle = "Book 5",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 6,
                booksTitle = "Book 6",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 7,
                booksTitle = "Book 7",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 8,
                booksTitle = "Book 8",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 9,
                booksTitle = "Book 9",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 10,
                booksTitle = "Book 10",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 11,
                booksTitle = "Book 11",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 12,
                booksTitle = "Book 12",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 13,
                booksTitle = "Book 13",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 14,
                booksTitle = "Book 14",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 15,
                booksTitle = "Book 15",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 16,
                booksTitle = "Book 16",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 17,
                booksTitle = "Book 17",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 18,
                booksTitle = "Book 18",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.baseline_check_circle_24
            ),
            BookModel(
                booksId = 19,
                booksTitle = "Book 19",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 20,
                booksTitle = "Book 20",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 21,
                booksTitle = "Book 21",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 22,
                booksTitle = "Book 22",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 23,
                booksTitle = "Book 7",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 24,
                booksTitle = "Book 8",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 25,
                booksTitle = "Book 9",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 26,
                booksTitle = "Book 10",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 27,
                booksTitle = "Book 11",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 28,
                booksTitle = "Book 12",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 29,
                booksTitle = "Book 13",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 30,
                booksTitle = "Book 14",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 31,
                booksTitle = "Book 15",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 32,
                booksTitle = "Book 16",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 33,
                booksTitle = "Book 1",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 34,
                booksTitle = "Book 2",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.baseline_check_circle_24
            ),
            BookModel(
                booksId = 35,
                booksTitle = "Book 3",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 36,
                booksTitle = "Book 4",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 37,
                booksTitle = "Book 5",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 38,
                booksTitle = "Book 6",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 39,
                booksTitle = "Book 7",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
            BookModel(
                booksId = 40,
                booksTitle = "Book 8",
                booksAuth = "Pushkin",
                booksDescription = "Здесь описание книги",
                isLiked = false,
                bookImage = R.drawable.book
            ),
        )
    }

    fun markFavourite(bookModel: BookModel) {
        val book =
            getAllBooks().find { (it as? BookModel)?.booksId == bookModel.booksId } as BookModel
        book.let {
            book.isLiked = !book.isLiked
        }
    }

    fun getAllBooks(): List<ItemModel> = list

    fun getBooks(booksCount: Int): MutableList<ItemModel> {
        if (list.isEmpty()) fillList(booksCount)
        return list

    }

    private fun fillList(booksCount: Int) {
        list.clear()
        list.add(ButtonModel())
        for (i in 1..booksCount) {
            val book = books.find { it.booksId == i }
            if (book != null) {
                list.add(book)
            }
        }
        addDateItems()
    }

    private fun addDateItems() {
        var i = 1
        while (i < list.size) {
            list.add(i, DateModel())
            i += 9
        }
    }


    fun addBooks(count: Int) {
        val id = list.size
        for (i in 1..count) {
            list.add(books[id + i])
        }

    }
    fun clearAll() {
        if (list.isNotEmpty()) {
            list.clear()
        }
    }

}
