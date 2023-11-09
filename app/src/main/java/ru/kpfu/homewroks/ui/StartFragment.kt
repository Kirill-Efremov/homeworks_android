package ru.kpfu.homewroks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.kpfu.homeworks.R
import ru.kpfu.homeworks.databinding.FragmentStartBinding
import ru.kpfu.homewroks.utils.BookRepository

class StartFragment : Fragment(R.layout.fragment_start) {
    private var _viewBinding: FragmentStartBinding? = null
    private val viewBinding: FragmentStartBinding get() = _viewBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentStartBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {
        with(viewBinding) {
            val input = etBooksNumber.text.toString()
            btnStart.setOnClickListener {
                BookRepository.clearAll()
                if (validate(input)) {
                    val count = etBooksNumber.text.toString()
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.main_activity_container, BookFragment.newInstance(count))
                        .addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(context, "Enter a number 0 - 45", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun validate(c: String): Boolean {
        return (c.toIntOrNull() ?: 0) <= 45
    }


    override fun onDestroy() {
        _viewBinding = null
        super.onDestroy()
    }


}