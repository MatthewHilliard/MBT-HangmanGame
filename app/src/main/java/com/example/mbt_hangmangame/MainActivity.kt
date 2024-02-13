package com.example.mbt_hangmangame

import android.annotation.SuppressLint
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
    private var numGuesses = 0
    private var curState = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hangmanProgress = findViewById(R.id.hangmanPicture)
        guessingWord = findViewById(R.id.guessingWord)
        currWord = resources.getStringArray(R.array.wordBank).random()
        newGameButton = findViewById(R.id.newGameButton)

        newGameButton.setOnClickListener(){
            newGame()
        }

        if (savedInstanceState != null) {
            // Restore the state of the game
            currWord = savedInstanceState.getString("currWord", "")
            numGuesses = savedInstanceState.getInt("numGuesses", 0)
        } else {
            // If no saved instance state, start a new game
            newGame()
        }
    }
    private fun newGame() {
        hangmanProgress.setImageResource(R.drawable.state0)
        currWord = resources.getStringArray(R.array.wordBank).random()
        guessingWord.text = currWord
        numGuesses = 0
    }

    @SuppressLint("DiscouragedApi")
    private fun wrongLetter() {
        numGuesses += 1
        curState = "state$numGuesses"
        val resourceId = resources.getIdentifier(curState, "drawable", packageName)
        hangmanProgress.setImageResource(resourceId)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currWord", currWord)
        outState.putInt("numGuesses", numGuesses)
    }

    @SuppressLint("DiscouragedApi")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currWord = savedInstanceState.getString("currWord", "")
        numGuesses = savedInstanceState.getInt("numGuesses", 0)
        val currentState = "state$numGuesses"
        hangmanProgress.setImageResource(resources.getIdentifier(currentState, "drawable", packageName))
        guessingWord.text = currWord
    }
}