package com.example.calculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var toolbarMain: androidx.appcompat.widget.Toolbar

    private lateinit var firstOperandET: EditText
    private lateinit var secondOperandET: EditText

    private lateinit var operatorPlusBTN: Button
    private lateinit var operatorMinusBTN: Button
    private lateinit var operatorMultiplyBTN: Button
    private lateinit var operatorDivideBTN: Button

    private lateinit var resultTV: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = "Калькулятор"
        toolbarMain.subtitle = "версия 2"
        toolbarMain.setLogo(R.drawable.ic_calculate)


        firstOperandET = findViewById(R.id.firstOperandET)
        secondOperandET = findViewById(R.id.secondOperandET)

        operatorPlusBTN = findViewById(R.id.operatorPlusBTN)
        operatorMinusBTN = findViewById(R.id.operatorMinusBTN)
        operatorMultiplyBTN = findViewById(R.id.operatorMultiplyBTN)
        operatorDivideBTN = findViewById(R.id.operatorDivideBTN)

        resultTV = findViewById(R.id.resultTV)

        operatorPlusBTN.setOnClickListener(this)
        operatorMinusBTN.setOnClickListener(this)
        operatorMultiplyBTN.setOnClickListener(this)
        operatorDivideBTN.setOnClickListener(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.resetMenuMain -> {
                firstOperandET.text.clear()
                secondOperandET.text.clear()
                resultTV.text = "Результат"
                Toast.makeText(
                    applicationContext,
                    "Данные очищены",
                    Toast.LENGTH_LONG
                ).show()
            }
            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext,
                    "Работа завершена",
                    Toast.LENGTH_LONG
                ).show()
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(view: View) {
        if (firstOperandET.text.isEmpty() || secondOperandET.text.isEmpty()) {
            resultTV.text = "Введите значения операндов"
            return
        }

        var first = firstOperandET.text.toString().toDouble()
        var second = secondOperandET.text.toString().toDouble()

        var result = 0.0

        try {
            result = when (view.id) {
                R.id.operatorPlusBTN -> Operation(first, second).summary()
                R.id.operatorMinusBTN -> Operation(first, second).difference()
                R.id.operatorMultiplyBTN -> Operation(first, second).multiply()
                R.id.operatorDivideBTN -> Operation(first, second).division()
                else -> 0.0
            }
        } catch (e: ArithmeticException) {
            resultTV.text = "На ноль делить нельзя"
        }

        resultTV.text = result.toString()

    }
}