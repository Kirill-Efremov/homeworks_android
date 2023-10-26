package ru.kpfu.homeworks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.homeworks.databinding.FragmentQuestionBinding


class QuestionFragment : Fragment(R.layout.fragment_question) {
    private val viewBinding: FragmentQuestionBinding by viewBinding(FragmentQuestionBinding::bind)
    private var rvAdapter: QuestionAdapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val question: QuestionData = arguments?.getSerializable(ParamsKey.QUESTIONS_COUNT) as QuestionData
        viewBinding.apply {
            tvQuestion.text = question.question
            rvAdapter = QuestionAdapter(
                items = question.answers,
                onItemChecked = { position ->  }
            ) { position ->  }
            rvAnswers.adapter = rvAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        rvAdapter = null
    }
    companion object {
        fun newInstance(questions: QuestionData, currentQuestionNumber: Int) = QuestionFragment().apply {
                arguments = bundleOf(ParamsKey.QUESTIONS_COUNT to questions,
                    ParamsKey.POSITION_QUESTION to currentQuestionNumber
                )
            }
    }

}