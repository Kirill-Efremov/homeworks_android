package ru.kpfu.homeworks

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.kpfu.homeworks.databinding.FragmentViewPagerBinding

class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {

    private val viewBinding: FragmentViewPagerBinding by viewBinding(FragmentViewPagerBinding::bind)

    private var vpAdapter: FragmentAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val questionCount = arguments?.getInt(ParamsKey.QUESTIONS_COUNT)
        if (questionCount != null) {
            vpAdapter = context?.let { QuestionGenerator.getQuestions(it, questionCount) }?.let {
                FragmentAdapter(manager = parentFragmentManager, lifecycle, it)
            }
            viewBinding.apply {
                vpFragment.adapter = vpAdapter
                vpFragment.run {
                    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                        override fun onPageScrolled(
                            position: Int,
                            positionOffset: Float,
                            positionOffsetPixels: Int
                        ) {
                            if (position == 0 && positionOffset == 0.0f) {
                                setCurrentItem(questionCount, false)
                            }else
                                if (position == questionCount + 1 && positionOffset == 0.0f) {
                                setCurrentItem(1, false)
                            }
                        }
                        override fun onPageSelected(position: Int) {
                            val posit = when(position) {
                                0 -> questionCount
                                questionCount + 1 -> 1
                                else -> position
                            }
                            "$posit/$questionCount".also { tvHeader.text = it }
                        }
                    })
                    setCurrentItem(1, false)
                }

            }
        }
    }
    companion object {
        fun newInstance(count: Int) = ViewPagerFragment().apply {
            arguments = bundleOf(ParamsKey.QUESTIONS_COUNT to count)
        }
    }
}