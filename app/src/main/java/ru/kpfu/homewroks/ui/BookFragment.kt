package ru.kpfu.homewroks.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentBooksBinding
import ru.kpfu.homewroks.adapter.BookAdapter
import ru.kpfu.homewroks.model.BookModel
import ru.kpfu.homewroks.model.ButtonModel
import ru.kpfu.homewroks.model.DateModel
import ru.kpfu.homewroks.model.ItemModel
import ru.kpfu.homewroks.ui.holder.BookItem
import ru.kpfu.homewroks.utils.BookRepository
import ru.kpfu.homewroks.utils.ParamsKey

class BookFragment : Fragment(R.layout.fragment_books) {
    private val viewBinding: FragmentBooksBinding by viewBinding(FragmentBooksBinding::bind)
    private var bookAdapter: BookAdapter? = null
    private val fragmentContainerId: Int = R.id.main_activity_container


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycleView()

    }

    private fun initRecycleView() {
        bookAdapter = BookAdapter(
            onNewsClicked = ::onNewsClicked,
            onLikeClicked = ::onLikeClicked,
            fragmentManager = parentFragmentManager,
        )
        with(viewBinding) {
            val input = arguments?.getString(ParamsKey.BOOKS_COUNT)
            if (input != null) {
                val booksCount = input.toInt()
                if (booksCount == 0) {
                    tvInfo.visibility = View.VISIBLE
                } else {
                    tvInfo.visibility = View.GONE
                }
                val list = BookRepository.getBooks(booksCount)

                if (booksCount <= 12) {
                    val layoutManager =
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    rvBook.layoutManager = layoutManager
                } else {
                    val layoutManager = GridLayoutManager(requireContext(), 2)
                    rvBook.layoutManager = layoutManager
                    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int) = if (list[position] is BookModel) 1
                        else 2
                    }
                }
                rvBook.adapter = bookAdapter
                bookAdapter?.setItems(list)


            }
        }
    }

    private fun onLikeClicked(position: Int, book: BookModel) {
        BookRepository.markFavourite(book)
        bookAdapter?.updateItem(position, book)
    }

    private fun onNewsClicked(book: BookModel) {
        parentFragmentManager.beginTransaction().replace(
            fragmentContainerId, BookDetailFragment.newInstance(
                book.booksTitle, book.booksDescription, book.bookImage
            )
        ).addToBackStack(null).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        bookAdapter = null
    }

    companion object {
        fun newInstance(count: String) = BookFragment().apply {
            arguments = bundleOf(ParamsKey.BOOKS_COUNT to count)
        }
    }
}