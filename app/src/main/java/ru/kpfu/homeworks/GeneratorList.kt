package ru.kpfu.homeworks

object GeneratorList {
        private val newsList = mutableListOf<Model>()



    fun getNewsList(countQuestions: Int): List<Model> {
        return (0 until 50)
            .map {
                val imageId = when (it % 6) {
                    0 -> R.drawable.item1
                    1 -> R.drawable.item2
                    2 -> R.drawable.item3
                    3 -> R.drawable.item4
                    4 -> R.drawable.item5
                    else -> R.drawable.item6
                }

                Model(imageId)
            }
            .take(countQuestions)
    }






}