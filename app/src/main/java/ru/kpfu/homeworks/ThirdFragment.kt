package ru.kpfu.homeworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.homeworks.databinding.FragmentThirdBinding

class ThirdFragment : Fragment(R.layout.fragment_third) {
    private var _viewBinding: FragmentThirdBinding? = null
    private val viewBinding: FragmentThirdBinding
        get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentThirdBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        val inputText = arguments?.getString(ParamsKey.INPUT_TEXT)
        if (!inputText.isNullOrBlank()) {
            viewBinding.textView3.text = inputText
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {
        fun newInstance(projectName: String?): ThirdFragment {
            val args = Bundle()
            args.putString(ParamsKey.INPUT_TEXT, projectName)
            val fragment = ThirdFragment()
            fragment.arguments = args
            return fragment
        }
    }
}