package ru.kpfu.homeworks

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.DisplayMetrics
import android.view.LayoutInflater

import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class Adapter(
    private val newsList: List<Model>,
    private val inputNumber: Int,
    private val context: Context
) : RecyclerView.Adapter<Holder>() {


    private val displayMetrics: DisplayMetrics = context.resources.displayMetrics

    override fun getItemViewType(position: Int): Int {
        return if (isFullWidth(position)) {
            VIEW_TYPE_FULL_WIDTH
        } else {
            VIEW_TYPE_DEFAULT
        }
    }

    private fun isFullWidth(position: Int): Boolean {
        val remainder = position % 6
        return when (inputNumber % 6) {
            1 -> remainder == 0 || remainder == 5
            2 -> remainder == 5 || position == getMaxPositionWithRemainderOne(
                inputNumber,
                1
            ) || remainder == 0

            3 -> remainder == 0 || remainder == 5
            4 -> position == getMaxPositionWithRemainderOne(
                inputNumber,
                3
            ) || remainder == 0 || remainder == 5

            5 -> remainder == 0 || remainder == 5
            0 -> remainder == 0 || remainder == 5
            else -> false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val isFullSpan = viewType == VIEW_TYPE_FULL_WIDTH
        val layoutId = R.layout.item
        val layoutView =
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        (layoutView.layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan =
            isFullSpan
        return Holder(layoutView)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val news = newsList[position]
        holder.onBindNewsItem(news, getItemViewType(position) == VIEW_TYPE_FULL_WIDTH, inputNumber)
        updateViewDimensions(holder, getItemViewType(position) == VIEW_TYPE_FULL_WIDTH)
    }

    private fun updateViewDimensions(holder: Holder, isFullWidth: Boolean) {
        val availableWidth = displayMetrics.widthPixels
        val numColumns = 2
        val viewWidth = availableWidth / numColumns
        val layoutParams = holder.itemView.layoutParams

        val drawableState = holder.viewBinding.image.drawable.constantState
        if (isSpecialDrawable(drawableState, R.drawable.item3) || isSpecialDrawable(
                drawableState,
                R.drawable.item4
            )
        ) {
            setViewDimensions(layoutParams, viewWidth, viewWidth * 1.5)
        } else if (isSpecialDrawable(drawableState, R.drawable.item2) || isSpecialDrawable(
                drawableState,
                R.drawable.item5
            )
        ) {
            setViewDimensions(layoutParams, viewWidth, viewWidth.toDouble())
        } else if (holder.adapterPosition == 0 || holder.adapterPosition == itemCount - 1) {

            setViewDimensions(layoutParams, viewWidth, viewWidth * 1.2)
        } else {

            setViewDimensions(layoutParams, viewWidth, viewWidth.toDouble())
        }


        holder.itemView.layoutParams = layoutParams
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun isSpecialDrawable(
        drawableState: Drawable.ConstantState?,
        resourceId: Int
    ): Boolean {
        return drawableState?.toString() == context.resources.getDrawable(
            resourceId,
            null
        ).constantState?.toString()
    }

    private fun setViewDimensions(
        layoutParams: ViewGroup.LayoutParams,
        width: Int,
        height: Double
    ) {
        layoutParams.width = width
        layoutParams.height = height.toInt()
    }

    private fun getMaxPositionWithRemainderOne(inputNumber: Int, value: Int): Int {
        var maxPosition = 0
        for (i in 0 until inputNumber) {
            val remainder = i % 6
            if (remainder == value && i > maxPosition) {
                maxPosition = i
            }
        }
        return maxPosition
    }

    companion object {
        private const val VIEW_TYPE_FULL_WIDTH = 0
        private const val VIEW_TYPE_DEFAULT = 1
    }
}
