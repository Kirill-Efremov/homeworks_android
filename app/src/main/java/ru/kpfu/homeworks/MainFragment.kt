package ru.kpfu.homeworks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout

import android.widget.ImageView
import android.widget.LinearLayout

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.kpfu.homeworks.databinding.FragmentMainBinding
import by.kirich1409.viewbindingdelegate.viewBinding


class MainFragment : Fragment(R.layout.fragment_main) {
    private val viewBinding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private var newsAdapter: Adapter? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewBinding) {
            val recyclerView: RecyclerView = recycleView
            val inputNumber = arguments?.getInt(ARG_COUNT) ?: 0
            val newsList = GeneratorList.getNewsList(inputNumber)
            newsAdapter = Adapter(newsList, inputNumber, requireContext())
            recyclerView.adapter = newsAdapter
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }


    companion object {
        private const val ARG_COUNT = "count"
        fun newInstance(count: Int): MainFragment {
            val fragment = MainFragment()
            val args = Bundle()
            args.putInt(ARG_COUNT, count)
            fragment.arguments = args
            return fragment
        }
    }

}
