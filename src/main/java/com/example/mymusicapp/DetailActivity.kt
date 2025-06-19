package com.example.mymusicapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DetailActivity : AppCompatActivity() {

    // Variables to hold the incoming song data
    private lateinit var titles: ArrayList<String>
    private lateinit var artists: ArrayList<String>
    private lateinit var ratings: ArrayList<Int>
    private lateinit var comments: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // Get references to UI elements
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val averageText = findViewById<TextView>(R.id.txtAverage)
        val calcButton = findViewById<Button>(R.id.btnCalcAverage)
        val backButton = findViewById<Button>(R.id.btnBack)

        // Get the data passed from MainActivity
        titles = intent.getStringArrayListExtra("titles") ?: arrayListOf()
        artists = intent.getStringArrayListExtra("artists") ?: arrayListOf()
        ratings = intent.getIntegerArrayListExtra("ratings") ?: arrayListOf()
        comments = intent.getStringArrayListExtra("comments") ?: arrayListOf()

        // Setup RecyclerView for displaying song list
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SongAdapter(titles, artists, ratings, comments)

        // When "Calculate Average" is clicked
        calcButton.setOnClickListener {
            var total = 0

            // Use a loop to calculate the sum of ratings
            for (i in ratings.indices) {
                total += ratings[i]
            }

            // Calculate average if ratings are available
            if (ratings.isNotEmpty()) {
                val average = total.toDouble() / ratings.size
                averageText.text = "⭐ Average Rating: %.2f".format(average)
            } else {
                averageText.text = "No ratings available"
            }
        }

        // When "Back to Main" is clicked
        backButton.setOnClickListener {
            finish()  // Closes this screen and returns to MainActivity
        }
    }

    // Inner Adapter class
    inner class SongAdapter(
        private val titles: ArrayList<String>,
        private val artists: ArrayList<String>,
        private val ratings: ArrayList<Int>,
        private val comments: ArrayList<String>
    ) : RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

        inner class SongViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val tvTitle: TextView = view.findViewById(R.id.tvTitle)
            val tvArtist: TextView = view.findViewById(R.id.tvArtist)
            val tvRating: TextView = view.findViewById(R.id.tvRating)
            val tvComment: TextView = view.findViewById(R.id.tvComment)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.song_item, parent, false)
            return SongViewHolder(view)
        }

        override fun getItemCount(): Int = titles.size

        override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
            holder.tvTitle.text = titles[position]
            holder.tvArtist.text = artists[position]
            holder.tvRating.text = "Rating: ${ratings[position]}"
            holder.tvComment.text = comments[position]
        }
    }
}
