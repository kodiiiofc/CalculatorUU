package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var firstOperandET: EditText
    private lateinit var secondOperandET: EditText

    private lateinit var operatorPlusBTN: Button
    private lateinit var operatorMinusBTN: Button
    private lateinit var operatorMultiplyBTN: Button
    private lateinit var operatorDivideBTN: Button

    private lateinit var resultTV: TextView

    private lateinit var resetBTN: Button
    private lateinit var exitBTN: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firstOperandET = findViewById(R.id.firstOperandET)
        secondOperandET = findViewById(R.id.secondOperandET)

        operatorPlusBTN = findViewById(R.id.operatorPlusBTN)
        operatorMinusBTN = findViewById(R.id.operatorMinusBTN)
        operatorMultiplyBTN = findViewById(R.id.operatorMultiplyBTN)
        operatorDivideBTN = findViewById(R.id.operatorDivideBTN)

        exitBTN = findViewById(R.id.exitBTN)
        resetBTN = findViewById(R.id.resetBTN)

        resultTV = findViewById(R.id.resultTV)

        operatorPlusBTN.setOnClickListener(this)
        operatorMinusBTN.setOnClickListener(this)
        operatorMultiplyBTN.setOnClickListener(this)
        operatorDivideBTN.setOnClickListener(this)
        resetBTN.setOnClickListener(this)
        exitBTN.setOnClickListener(this)


    }

    override fun onClick(view: View) {
        var check = true


        var result = 0.0

        if (firstOperandET.text.isEmpty() || secondOperandET.text.isEmpty()) {

            resultTV.text = "Введите значения операндов"
            return

        }

        var first = firstOperandET.text.toString().toDouble()
        var second = secondOperandET.text.toString().toDouble()


        try {
            result = when (view.id) {
                R.id.operatorPlusBTN -> Operation(first, second).summary()
                R.id.operatorMinusBTN -> Operation(first, second).difference()
                R.id.operatorMultiplyBTN -> Operation(first, second).multiply()
                R.id.operatorDivideBTN -> Operation(first, second).division()
                R.id.resetBTN -> {
                    firstOperandET.text.clear()
                    secondOperandET.text.clear()
                    check = false
                    0.0
                }
                R.id.exitBTN -> {
                    finish()
                    0.0
                }
                else -> 0.0
            }
        } catch (e: ArithmeticException) {
            resultTV.text = "На ноль делить нельзя"
        }

        if (!check) resultTV.text = "Результат" else resultTV.text = result.toString()

    }
}