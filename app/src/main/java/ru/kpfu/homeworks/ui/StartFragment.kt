package ru.kpfu.homeworks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import ru.kpfu.homeworks.databinding.FragmentStartBinding
import ru.kpfu.homeworks.utlis.NotificationBuilder

class StartFragment : Fragment() {
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
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        with(viewBinding) {
            btnSendMessage.setOnClickListener {
                val title = etTitleNotification.text.toString()
                val text = etTextNotification.text.toString()
                if (title.isEmpty() && text.isEmpty()) {
                    Snackbar.make(
                        root,
                        "Заголовок и текст уведомления не могут быть пустыми",
                        Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    NotificationBuilder.sendNotification(ctx = requireContext(), title, text)
                }
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }
}