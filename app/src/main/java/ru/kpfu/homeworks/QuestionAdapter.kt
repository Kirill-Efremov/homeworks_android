package ru.kpfu.homeworks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.homeworks.databinding.ItemAnswerBinding

class QuestionAdapter(
    val items: List<AnswerData>,
    private val onItemChecked: ((Int) -> Unit)? = null,
    private val onRootClicked: ((Int) -> Unit)? = null,
) : RecyclerView.Adapter<QuestionHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionHolder {
       return QuestionHolder(viewBinding = ItemAnswerBinding.inflate(
           LayoutInflater.from(parent.context), parent, false),
           onItemChecked = onItemChecked,
           onRootClicked = onRootClicked)
    }

    override fun onBindViewHolder(holder: QuestionHolder, position: Int) {
       holder.bindItem(item = items[position], itemCount)
    }

    override fun getItemCount(): Int = items.count()
}