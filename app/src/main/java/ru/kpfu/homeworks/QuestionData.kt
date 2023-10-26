package ru.kpfu.homeworks

import java.io.Serializable

data class QuestionData(
    val question: String,

    val answers: MutableList<AnswerData>
): Serializable