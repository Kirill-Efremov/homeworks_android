package ru.kpfu.homewroks.ui

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentBookDetailBinding
import ru.kpfu.homewroks.utils.ParamsKey

class BookDetailFragment : Fragment(R.layout.fragment_book_detail) {
    private val viewBinding: FragmentBookDetailBinding by viewBinding(FragmentBookDetailBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            tvTitle.text = arguments?.getString(ParamsKey.BOOK_TITLE_KEY)
            tvDesc.text = arguments?.getString(ParamsKey.BOOK_DESC_KEY)
            arguments?.getInt(ParamsKey.BOOK_IMAGE_KEY)?.let { ivImage.setImageResource(it) }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(bookTitle: String, bookDesc: String, bookImage: Int) =
            BookDetailFragment().apply {
                arguments = bundleOf(
                    ParamsKey.BOOK_TITLE_KEY to bookTitle,
                    ParamsKey.BOOK_DESC_KEY to bookDesc,
                    ParamsKey.BOOK_IMAGE_KEY to bookImage
                )
            }
    }
}