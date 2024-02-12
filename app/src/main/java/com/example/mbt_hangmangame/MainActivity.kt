package com.example.mbt_hangmangame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var hangmanProgress: ImageView
    private lateinit var guessingWord: TextView
    private lateinit var newGameButton: Button

    private var currWord: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hangmanProgress = findViewById(R.id.hangmanPicture)
        guessingWord = findViewById(R.id.guessingWord)
        currWord = resources.getStringArray(R.array.wordBank).random()
        newGameButton = findViewById(R.id.newGameButton)

        newGame()

        newGameButton.setOnClickListener(){
            newGame()
        }
    }
    private fun newGame() {
        hangmanProgress.setImageResource(R.drawable.state0)
        currWord = resources.getStringArray(R.array.wordBank).random()
        guessingWord.text = currWord
    }
}