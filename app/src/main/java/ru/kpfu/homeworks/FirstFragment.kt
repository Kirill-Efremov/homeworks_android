package ru.kpfu.homeworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.homeworks.databinding.FragmentFirstBinding


class FirstFragment : Fragment(R.layout.fragment_first) {
    private var _viewBinding: FragmentFirstBinding? = null
    private val viewBinding: FragmentFirstBinding
        get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentFirstBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.btnGoToThirdScreenButton.setOnClickListener {
            val inputText = viewBinding.etInputText.text.toString()
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_activity_container, SecondFragment.newInstance(inputText))
                .addToBackStack(null)
                .commit()

            parentFragmentManager
                .beginTransaction()
                .replace(R.id.main_activity_container, ThirdFragment.newInstance(inputText))
                .addToBackStack(null)
                .commit()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        const val TAG = "FirstFragment"
    }
}