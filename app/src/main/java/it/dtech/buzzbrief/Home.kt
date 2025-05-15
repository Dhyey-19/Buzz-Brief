package it.dtech.buzzbrief

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Home : AppCompatActivity() {

    private lateinit var newsRecyclerView: RecyclerView
    private lateinit var categorySpinner: Spinner
    private lateinit var newsAdapter: NewsAdapter
    private val newsList: MutableList<NewsItem> = mutableListOf()

    // Sample data for the RecyclerView
    private val categories = arrayOf("Select Category", "Technology", "Sports", "Entertainment")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize views
        newsRecyclerView = findViewById(R.id.newsRecyclerView)
        categorySpinner = findViewById(R.id.categorySpinner)

        // Setup RecyclerView
        newsRecyclerView.layoutManager = LinearLayoutManager(this)
        newsAdapter = NewsAdapter(newsList)
        newsRecyclerView.adapter = newsAdapter

        // Setup Spinner for category filtering
        setupCategorySpinner()

        // Load initial news data
        loadNewsData()
    }

    private fun setupCategorySpinner() {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = adapter

        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                // Filter news based on selected category
                filterNewsByCategory(categories[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // No action needed
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadNewsData() {
        newsList.clear()
        newsList.add(NewsItem("Google to develop AI that takes over computers", "The goal is to create an AI agent that can interact directly with a user's computer and browser, taking online browsing to a new level of automation.", "Date : 01-10-2024", R.drawable.tech_news_1, "Technology", "https://indianexpress.com/article/technology/artificial-intelligence/google-to-develop-ai-that-takes-over-computers-9640926/"))
        newsList.add(NewsItem("Naveen Express VS High Flyer Pawan", "Dabang Delhi K.C. Fightback Against Telugu Titans and Pawan Sehrawat to Register Thrilling Win", "Date : 02-10-2024", R.drawable.sports_news_1, "Sports", "https://www.prokabaddi.com/news/match-18-report-pkl-season-11-telugu-titans-vs-dabang-delhi-kc"))
        newsList.add(NewsItem("Singham Again title track out now", "Ajay Devgn aka Bajirao Singham roars back", "Date : 03-10-2024", R.drawable.entertainment_news_1, "Entertainment", "https://www.peepingmoon.com/entertainment-news/news/78817/Singham-Again-title-track-out-now-Ajay-Devgn-aka-Bajirao-Singham-roars-back.html"))
        newsList.add(NewsItem("Apple Unveils Next-Generation iPhone", "The latest iPhone promises revolutionary battery life and a powerful camera system.", "Date : 04-10-2024", R.drawable.tech_news_2, "Technology", "https://www.newindianexpress.com/business/2024/Oct/24/apple-unveils-new-ai-features-coming-to-your-iphones-in-ios-182-beta-including-chatgpt-integration"))
        newsList.add(NewsItem("India Wins Cricket World Cup 2024", "India clinches the ICC Cricket World Cup with a stunning performance in the finals.", "Date : 07-10-2024", R.drawable.sports_news_2, "Sports", "https://www.aljazeera.com/sports/2024/6/30/how-incredible-india-won-the-icc-t20-world-cup-2024-to-end-13-year-wait"))
        newsList.add(NewsItem("Avatar 3 Breaks Box Office Records", "James Cameron's latest film sets new records in its opening weekend.", "Date : 10-10-2024", R.drawable.entertainment_news_2, "Entertainment", "https://www.theguardian.com/film/2023/jan/23/avatar-the-way-of-water-records-worldwide-box-office-james-cameron"))
        newsList.add(NewsItem("Tesla Launches Fully Autonomous Driving Beta", "Tesla's new FSD Beta takes a major leap forward in autonomous vehicle technology.", "Date : 05-10-2024", R.drawable.tech_news_3, "Technology", "https://www.theverge.com/2024/9/5/24236628/tesla-fsd-full-self-driving-cybertruck-europe-eu-china-launch-regulators"))
        newsList.add(NewsItem("Ronaldo Scores Hat-Trick in UEFA Champions League", "Cristiano Ronaldo dazzles the crowd with a hat-trick, leading his team to victory.", "Date : 08-10-2024", R.drawable.sports_news_3, "Sports", "https://www.uefa.com/uefachampionsleague/news/0257-0e99f0d0d91b-eb0f4ba7a8f7-1000--champions-league-hat-tricks-who-has-the-most-when-was-th/"))
        newsList.add(NewsItem("NASA Plans Mars Colony by 2035", "NASA outlines an ambitious roadmap to establish a human colony on Mars within the next decade.", "Date : 06-10-2024", R.drawable.tech_news_4, "Technology", "https://indianexpress.com/article/technology/science/nasa-artemis-mission-send-humans-to-mars-2035-9615401/"))
        newsList.add(NewsItem("Olympics 2024: USA Tops Medal Table", "The United States leads the medal count with golds in swimming, athletics, and gymnastics.", "Date : 09-10-2024", R.drawable.sports_news_4, "Sports", "https://olympics.com/en/paris-2024/medals"))
        newsList.add(NewsItem("Netflix Releases Trailer for New Sci-Fi Series", "The highly anticipated series 'Galactic Odyssey' is set to debut next month.", "Date : 12-10-2024", R.drawable.entertainment_news_4, "Entertainment", "https://economictimes.indiatimes.com/news/international/us/netflix-premiers-korean-sci-fi-show-parasyte-the-grey-check-release-date-time-watch-trailer/articleshow/109045233.cms?from=mdr"))
        newsAdapter.notifyDataSetChanged()
    }

    private fun filterNewsByCategory(category: String) {
        if (category == "Select Category") {
            newsAdapter.updateNews(newsList)
        } else {
            // Filter news based on selected category
            val filteredNewsList = newsList.filter { it.category == category }
            newsAdapter.updateNews(filteredNewsList)
        }
    }

}
