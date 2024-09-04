package com.example.tipcalculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tipcalculator.databinding.ActivitySummaryBinding

class SummaryActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySummaryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val totalTable = intent.getFloatExtra("totalTable", 0.0f)
        val numPeople = intent.getIntExtra("numPeople", 0)
        val percentage = intent.getIntExtra("percentage", 0)
        val totalWithTip = intent.getFloatExtra("totalWithTip", 0.0f)
        val totalAmount = intent.getFloatExtra("totalAmount", 0.0f)
        val tip = intent.getFloatExtra("tip", 0.0f)

        binding.tvTotalTable.text = "R$ ${"%.2f".format(totalTable)}"
        binding.tvNumPeople.text = numPeople.toString()
        binding.tvPercentage.text = "$percentage %"
        binding.tvTotalWithTip.text = "R$ ${"%.2f".format(totalWithTip)}"
        binding.tvTotalAmount.text = "R$ ${"%.2f".format(totalAmount)}"
        binding.tvTotalTip.text = "R$ ${"%.2f".format(tip)}"

        binding.btnRecalculate.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}