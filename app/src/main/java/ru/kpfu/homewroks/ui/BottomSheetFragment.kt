package ru.kpfu.homewroks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentBotoonSheetBinding
import ru.kpfu.homewroks.adapter.BookAdapter
import ru.kpfu.homewroks.utils.BookRepository

class BottomSheetFragment(val adapter: BookAdapter) :
    BottomSheetDialogFragment() {
    private val viewBinding: FragmentBotoonSheetBinding by viewBinding(FragmentBotoonSheetBinding::bind)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_botoon_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()

    }

    private fun initViews() {
        with(viewBinding) {
            btnDialog.setOnClickListener {

                val bookCount = etBooksNumber?.text.toString().toInt()
                if (bookCount in 1..5) {
                    BookRepository.addBooks(bookCount)
                    adapter.setItems(BookRepository.getAllBooks())
                    dismiss()
                } else {
                    Toast.makeText(context, "Enter a number 0 - 5", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    companion object {
        const val BOTTOM_SHEET_FRAGMENT_TAG = "BOTTOM_SHEET_DIALOG_FRAGMENT_TAG"
    }
}