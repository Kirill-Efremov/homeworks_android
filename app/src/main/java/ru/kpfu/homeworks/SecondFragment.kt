package ru.kpfu.homeworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.homeworks.databinding.FragmentSecondBinding

class SecondFragment : Fragment(R.layout.fragment_second) {
    private var _viewBinding: FragmentSecondBinding? = null
    private val viewBinding: FragmentSecondBinding
        get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentSecondBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(viewBinding) {
            val inputText = arguments?.getString(ParamsKey.INPUT_TEXT)
            if (!inputText.isNullOrBlank()) {
                textView2.text = inputText
            }
            btnGoToFirstScreenButton.setOnClickListener {
                parentFragmentManager.popBackStack()
            }
            btnGoToThirdScreenButton.setOnClickListener {
                parentFragmentManager.popBackStack()

                parentFragmentManager.beginTransaction()
                    .replace(R.id.main_activity_container, ThirdFragment.newInstance(inputText))
                    .addToBackStack(null).commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }

    companion object {

        fun newInstance(projectName: String?): SecondFragment {
            val args = Bundle()
            args.putString(ParamsKey.INPUT_TEXT, projectName)
            val fragment = SecondFragment()
            fragment.arguments = args
            return fragment
        }
    }

}