package com.example.tipcalculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tipcalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnReset.setOnClickListener {
            reset()
        }

        binding.btnCalculate.setOnClickListener {
            val totalTable = binding.tieTotal.text.toString().toDoubleOrNull()
            val numPeople = binding.tieNumPeople.text.toString().toIntOrNull()
            val percentage = binding.tiePercentage.text.toString().toIntOrNull()

            if (totalTable == null || numPeople == null || percentage == null) {
                val messageEmptyField = getString(R.string.message_empty_field)
                Snackbar.make(binding.tieTotal, messageEmptyField, Snackbar.LENGTH_LONG)
                    .show()
            } else if (!isValidValue(totalTable) || !isValidValue(numPeople.toDouble()) || !isValidPercentage(
                    percentage
                )
            ) {
                val messageZeroField = getString(R.string.message_zero_field)
                Snackbar.make(binding.tieTotal, messageZeroField, Snackbar.LENGTH_LONG)
                    .show()
            } else {
                val (tipTotal, totalWithTip, totalPerPerson) = calculateValues(
                    totalTable,
                    numPeople,
                    percentage
                )

                val intent = Intent(this, SummaryActivity::class.java)
                intent.apply {
                    putExtra("totalTable", totalTable)
                    putExtra("numPeople", numPeople)
                    putExtra("percentage", percentage)
                    putExtra("totalWithTip", totalWithTip)
                    putExtra("totalAmount", totalPerPerson)
                    putExtra("tip", tipTotal)
                }
                reset()
                startActivity(intent)
                finish()
            }
        }
    }

    private fun calculateValues(
        totalTable: Double,
        numPeople: Int,
        percentage: Int
    ): Triple<Double, Double, Double> {
        val tipTotal = totalTable * percentage / 100
        val totalWithTip = totalTable + tipTotal
        val totalPerPerson = totalWithTip / numPeople
        return Triple(tipTotal, totalWithTip, totalPerPerson)
    }

    private fun isValidPercentage(percentage: Int): Boolean {
        return percentage in 0..100
    }

    private fun isValidValue(value: Double): Boolean {
        return value > 0
    }

    private fun TextInputEditText.clearText() {
        this.setText("")
    }

    private fun reset() {
        binding.tieTotal.clearText()
        binding.tieNumPeople.clearText()
        binding.tiePercentage.clearText()

        binding.tieTotal.requestFocus()
    }
}