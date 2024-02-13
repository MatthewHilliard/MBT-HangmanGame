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
    private var numGuesses: Int = 0

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
            }
        }
    }

    private fun updateLetter(textView: TextView, index: Int, newChar: Char) {
        val currentText = StringBuilder(textView.text.toString())
        currentText.setCharAt(index*2, newChar)
        textView.text = currentText.toString()
    }

}