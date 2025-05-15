package it.dtech.buzzbrief

data class NewsItem(
    val title: String,
    val subtitle: String,
    val date: String,
    val imageResId: Int,
    val category: String,
    val link: String
)
