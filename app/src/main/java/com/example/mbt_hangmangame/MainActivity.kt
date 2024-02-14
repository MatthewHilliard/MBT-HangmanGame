package com.example.mbt_hangmangame

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var hangmanProgress: ImageView
    private lateinit var guessingWord: TextView
    private lateinit var newGameButton: Button

    private var currWord: String = ""
    private var numGuesses = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hangmanProgress = findViewById(R.id.hangmanPicture)
        guessingWord = findViewById(R.id.guessingWord)
        currWord = resources.getStringArray(R.array.wordBank).random()
        newGameButton = findViewById(R.id.newGameButton)

        newGameButton.setOnClickListener() {
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
    private fun youLost(view: View) {
        val keyboardGroup: LinearLayout = findViewById(R.id.keyboard)

            for (i in 0 until keyboardGroup.childCount) {
                val childView: View = keyboardGroup.getChildAt(i)

                if (childView is Button) {
                    childView.setBackgroundColor(Color.parseColor("#c6cfc8"))
                    childView.isEnabled = false
                }
            }

        val snackbar = Snackbar.make(view,
            "You lost :( Click to start a new game",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("New Game") {
            newGame()
        }
        snackbar.show()
        //should disable all buttons so no more guesses are able to be made
    }
//    private fun youWin(view: View) {
//        val snackbar = Snackbar.make(view,
//            "You Won! Click to start a new game :)",
//            Snackbar.LENGTH_LONG
//        )
//        snackbar.setAction("New Game") {
//            newGame()
//        }
//        snackbar.show()
//    }

    @SuppressLint("DiscouragedApi")
    private fun wrongLetter() {
        numGuesses += 1
        val curState = "state$numGuesses"
        val resourceId = resources.getIdentifier(curState, "drawable", packageName)
        hangmanProgress.setImageResource(resourceId)
        if(numGuesses > 6) {
            youLost(findViewById(android.R.id.content))
        }
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
        hangmanProgress.setImageResource(
            resources.getIdentifier(
                currentState,
                "drawable",
                packageName
            )
        )
        guessingWord.text = currWord
        //guessingWord.text = currWord
        underscoreWord(currWord, guessingWord)
    }

    private fun underscoreWord(word: String, textView: TextView) {
        val stringBuilder = StringBuilder()
        for (char in word) {
            stringBuilder.append("_ ")
        }
        textView.text = stringBuilder.toString()
    }

    fun letterClick(view: View) {
        if (view is Button) {
            val guess = view.text.toString().uppercase().first()
            if (currWord.contains(guess, ignoreCase = true)) {
                currWord.forEachIndexed { index, char ->
                    if (char.equals(guess, ignoreCase = true)) {
                        updateLetter(guessingWord, index, guess)
                    }
                }
            } else {
                wrongLetter()
            }
        }
    }

    private fun updateLetter(textView: TextView, index: Int, newChar: Char) {
        val currentText = StringBuilder(textView.text.toString())
        currentText.setCharAt(index * 2, newChar)
        textView.text = currentText.toString()
    }

}