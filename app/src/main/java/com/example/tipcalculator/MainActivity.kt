package com.example.tipcalculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tipcalculator.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

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
            val totalTableTemp = binding.tieTotal.text
            val numPeopleTemp = binding.tieNumPeople.text
            val percentageTemp = binding.tiePercentage.text
            if (totalTableTemp?.isEmpty() == true || numPeopleTemp?.isEmpty() == true || percentageTemp?.isEmpty() == true) {
                Snackbar.make(binding.tieTotal, "Preencha todos os campos", Snackbar.LENGTH_LONG)
                    .show()
            } else {
                val totalTable: Float = totalTableTemp.toString().toFloat()
                val numPeople: Int = numPeopleTemp.toString().toInt()
                val percentage: Int = percentageTemp.toString().toInt()

                val tipTotal = totalTable * percentage / 100
                val totalWithTip = totalTable + tipTotal
                val totalPerPerson = totalWithTip / numPeople

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

    private fun reset() {
        binding.tieTotal.setText("")
        binding.tieNumPeople.setText("")
        binding.tiePercentage.setText("")

        binding.tieTotal.requestFocus()
    }
}