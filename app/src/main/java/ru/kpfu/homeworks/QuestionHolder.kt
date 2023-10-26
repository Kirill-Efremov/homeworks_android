package ru.kpfu.homeworks

import androidx.recyclerview.widget.RecyclerView
import ru.kpfu.homeworks.databinding.ItemAnswerBinding

class QuestionHolder(private val viewBinding: ItemAnswerBinding,
                     private val onItemChecked: ((Int) -> Unit)? = null,
                     private val onRootClicked: ((Int) -> Unit)? = null,
    ) : RecyclerView.ViewHolder(viewBinding.root){
    init {
        viewBinding.cbAnswer.setOnClickListener {
            onItemChecked?.invoke(adapterPosition)
        }
        viewBinding.root.setOnClickListener {
            onRootClicked?.invoke(adapterPosition)
        }
    }
    fun bindItem(item: AnswerData, itemCount: Int) {
        with(viewBinding){
          tvAnswer.text = item.answer
            cbAnswer.isChecked = item.checked
            cbAnswer.isEnabled = !item.checked
        }
    }
}