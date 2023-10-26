package ru.kpfu.homeworks

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
    private val questions: List<QuestionData>,
) : FragmentStateAdapter(manager, lifecycle) {
    override fun getItemCount(): Int = questions.size
    override fun createFragment(position: Int): Fragment {
        return QuestionFragment.newInstance(questions[position], position)
    }
}