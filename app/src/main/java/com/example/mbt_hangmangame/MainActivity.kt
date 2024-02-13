package com.example.mbt_hangmangame

import android.annotation.SuppressLint
import android.graphics.Color
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
    private val guessedLetters = mutableSetOf<Button>()

    companion object {
        const val GUESSING_WORD_KEY = "GUESSING_WORD_KEY"
    }

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
        numGuesses = 0
        underscoreWord()
        resetButtons()
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
        outState.putString("GUESSING_WORD_KEY", guessingWord.text.toString())
    }

    @SuppressLint("DiscouragedApi")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currWord = savedInstanceState.getString("currWord", "")
        numGuesses = savedInstanceState.getInt("numGuesses", 0)
        guessingWord.text = savedInstanceState.getString(GUESSING_WORD_KEY, "")
        val currentState = "state$numGuesses"
        hangmanProgress.setImageResource(resources.getIdentifier(currentState, "drawable", packageName))
    }

    private fun underscoreWord() {
        val stringBuilder = StringBuilder()
        for (char in currWord) {
            stringBuilder.append("_ ")
        }
        guessingWord.text = stringBuilder.toString()
    }

    private fun resetButtons() {
        guessedLetters.forEach { button ->
            button.isEnabled = true
            button.setBackgroundColor(Color.parseColor("#42474f"))
        }
        guessedLetters.clear()
    }

    fun letterClick(view: View) {
        if (view is Button) {
            val button = view as Button
            guessedLetters.add(button)
            button.setBackgroundColor(Color.parseColor("#c6cfc8"))
            button.isEnabled = false
            val guess = view.text.toString().uppercase().first()
            if (currWord.contains(guess, ignoreCase = true)) {
                currWord.forEachIndexed { index, char ->
                    if (char.equals(guess, ignoreCase = true)) {
                        updateLetter(index, guess)
                    }
                }
            } else {
                wrongLetter()
            }
        }
    }

    private fun updateLetter(index: Int, newChar: Char) {
        val currentText = StringBuilder(guessingWord.text.toString())
        currentText.setCharAt(index*2, newChar)
        guessingWord.text = currentText.toString()
    }

    private fun vowelHint() {
        wrongLetter()
        val vowels = setOf('A', 'E', 'I', 'O', 'U')
        for ((index, char) in currWord.withIndex()) {
            if (char in vowels) {
                updateLetter(index*2, char)
            }
        }
    }

}