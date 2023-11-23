package ru.kpfu.homeworks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kpfu.homeworks.MainActivity
import ru.kpfu.homeworks.databinding.FragmentCoroutineBinding

class SettingsCoroutineFragment : Fragment() {
    private var _viewBinding: FragmentCoroutineBinding? = null
    private val viewBinding: FragmentCoroutineBinding get() = _viewBinding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentCoroutineBinding.inflate(inflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()}

    private fun initViews() {
        with(viewBinding) {
            btnExecute.setOnClickListener {
                val async = cbAsync.isChecked
                val stopOnBackground = cbBackground.isChecked
                (requireActivity() as MainActivity).startAllCoroutines(
                    seekBar.progress,
                    async,
                    stopOnBackground,
                )
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}