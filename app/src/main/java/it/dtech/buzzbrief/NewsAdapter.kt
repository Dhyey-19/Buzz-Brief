package it.dtech.buzzbrief

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream

class NewsAdapter(private var newsList: List<NewsItem>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.newsTitle)
        val subtitle: TextView = itemView.findViewById(R.id.newsSubtitle)
        val date: TextView = itemView.findViewById(R.id.newsDate)
        val image: ImageView = itemView.findViewById(R.id.newsImageView)
        val readMore: TextView = itemView.findViewById(R.id.readMoreTextView) // Reference to the Read More TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.title.text = newsItem.title
        holder.subtitle.text = newsItem.subtitle
        holder.date.text = newsItem.date
        holder.image.setImageResource(newsItem.imageResId)

        // Set up the Read More click listener
        holder.readMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(newsItem.link))
            holder.itemView.context.startActivity(intent)
        }

        // Set up long click listener for sharing on news card
        holder.itemView.setOnLongClickListener {
            shareNews(holder, newsItem)
            true
        }
    }

    private fun shareNews(holder: NewsViewHolder, newsItem: NewsItem) {
        // Convert the image resource to a URI
        val bitmap = BitmapFactory.decodeResource(holder.itemView.context.resources, newsItem.imageResId)
        val imageUri = saveImageToCache(holder.itemView.context, bitmap) // Save and get URI for the image

        // Create share intent
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "image/*"
            putExtra(Intent.EXTRA_STREAM, imageUri) // Share image
            putExtra(Intent.EXTRA_TEXT, "${newsItem.title}\n\n${newsItem.subtitle}") // Share title and subtitle
        }

        // Start the share intent
        holder.itemView.context.startActivity(Intent.createChooser(shareIntent, "Share news via"))
    }

    // Helper function to save the image to cache and retrieve URI
    private fun saveImageToCache(context: Context, bitmap: Bitmap): Uri {
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs()
        val file = File(cachePath, "image.png")
        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
        }
        return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    }

    override fun getItemCount(): Int = newsList.size

    fun updateNews(newNewsList: List<NewsItem>) {
        newsList = newNewsList
        notifyDataSetChanged()
    }
}
