package ru.kpfu.homeworks

import android.content.Context

object QuestionGenerator {

    fun getQuestions(ctx: Context, questionCount: Int): MutableList<QuestionData> {
        val arrayQue = ctx.resources.getStringArray(R.array.questions)
        val arrayAns = ctx.resources.getStringArray(R.array.answers)
        val resultList = mutableListOf<QuestionData>()
        repeat(questionCount) {
            val answers = arrayAns.map { AnswerData(it) }.toMutableList()
            val newIndex = (0 until arrayQue.size).random()
            resultList.add(QuestionData(arrayQue[newIndex], answers))
        }
        resultList.add(0, resultList.last())
        resultList.add(resultList[1])
        return resultList
    }
    }
