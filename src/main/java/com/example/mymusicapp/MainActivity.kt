package com.example.mymusicapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Store song details in parallel ArrayLists
    private val songTitles = ArrayList<String>()
    private val artistNames = ArrayList<String>()
    private val ratings = ArrayList<Int>()
    private val comments = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Link UI elements by matching the IDs in XML
        val titleInput = findViewById<EditText>(R.id.editSongTitle)
        val artistInput = findViewById<EditText>(R.id.editArtist)
        val ratingInput = findViewById<EditText>(R.id.editRating)
        val commentInput = findViewById<EditText>(R.id.editComment)

        val addButton = findViewById<Button>(R.id.btnAdd)
        val viewButton = findViewById<Button>(R.id.btnView)
        val exitButton = findViewById<Button>(R.id.btnExit)

        // Add song button handler
        addButton.setOnClickListener {
            val title = titleInput.text.toString().trim()
            val artist = artistInput.text.toString().trim()
            val ratingText = ratingInput.text.toString().trim()
            val comment = commentInput.text.toString().trim()

            // Validate inputs
            if (title.isEmpty() || artist.isEmpty() || ratingText.isEmpty() || comment.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val rating = ratingText.toIntOrNull()
            if (rating == null || rating !in 1..5) {
                Toast.makeText(this, "Enter a rating between 1 and 5", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Add data to lists
            songTitles.add(title)
            artistNames.add(artist)
            ratings.add(rating)
            comments.add(comment)

            // Clear input fields
            titleInput.text.clear()
            artistInput.text.clear()
            ratingInput.text.clear()
            commentInput.text.clear()

            Toast.makeText(this, "Song added!", Toast.LENGTH_SHORT).show()
        }

        // View playlist button handler
        viewButton.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putStringArrayListExtra("titles", songTitles)
            intent.putStringArrayListExtra("artists", artistNames)
            intent.putIntegerArrayListExtra("ratings", ratings)
            intent.putStringArrayListExtra("comments", comments)
            startActivity(intent)
        }

        // Exit app button handler
        exitButton.setOnClickListener {
            finish()
        }
    }
}
