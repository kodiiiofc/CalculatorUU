package com.example.calculator

import android.os.Bundle
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var toolbarMain: androidx.appcompat.widget.Toolbar

    private lateinit var inputET: EditText

    private lateinit var plusBTN: Button
    private lateinit var minusBTN: Button
    private lateinit var multiplyBTN: Button
    private lateinit var divideBTN: Button
    private lateinit var equalBTN: Button
    private lateinit var clearBTN: Button

    private lateinit var zeroBTN: Button
    private lateinit var oneBTN: Button
    private lateinit var twoBTN: Button
    private lateinit var threeBTN: Button
    private lateinit var fourBTN: Button
    private lateinit var fiveBTN: Button
    private lateinit var sixBTN: Button
    private lateinit var sevenBTN: Button
    private lateinit var eightBTN: Button
    private lateinit var nineBTN: Button

    private lateinit var resultTV: TextView

    private var result = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbarMain = findViewById(R.id.toolbar_actionbar)
        setSupportActionBar(toolbarMain)


        inputET = findViewById(R.id.et_input)

        plusBTN = findViewById(R.id.btn_plus)
        minusBTN = findViewById(R.id.btn_minus)
        multiplyBTN = findViewById(R.id.btn_multiply)
        divideBTN = findViewById(R.id.btn_divide)
        equalBTN = findViewById(R.id.btn_equal)
        clearBTN = findViewById(R.id.btn_clear)

        zeroBTN = findViewById(R.id.btn_zero)
        oneBTN = findViewById(R.id.btn_one)
        twoBTN = findViewById(R.id.btn_two)
        threeBTN = findViewById(R.id.btn_three)
        fourBTN = findViewById(R.id.btn_four)
        fiveBTN = findViewById(R.id.btn_five)
        sixBTN = findViewById(R.id.btn_six)
        sevenBTN = findViewById(R.id.btn_seven)
        eightBTN = findViewById(R.id.btn_eight)
        nineBTN = findViewById(R.id.btn_nine)

        resultTV = findViewById(R.id.tv_result)

        val operationsOnClickListener = View.OnClickListener {
            when (it.id) {
                R.id.btn_plus -> appendArithmeticOperation("+")
                R.id.btn_minus -> appendArithmeticOperation("-")
                R.id.btn_multiply -> appendArithmeticOperation(getString(R.string.btn_multiply))
                R.id.btn_divide -> appendArithmeticOperation(getString(R.string.btn_divide))
                R.id.btn_equal -> resultTV.text = parseEquation(inputET.text.toString()).toString()
                R.id.btn_clear -> {
                    inputET.text.clear()
                    resultTV.text = ""
                }
            }
        }

        val numbersOnClicksListener = View.OnClickListener {
            when (it.id) {
                R.id.btn_zero -> inputET.text.append("0")
                R.id.btn_one -> inputET.text.append("1")
                R.id.btn_two -> inputET.text.append("2")
                R.id.btn_three -> inputET.text.append("3")
                R.id.btn_four -> inputET.text.append("4")
                R.id.btn_five -> inputET.text.append("5")
                R.id.btn_six -> inputET.text.append("6")
                R.id.btn_seven -> inputET.text.append("7")
                R.id.btn_eight -> inputET.text.append("8")
                R.id.btn_nine -> inputET.text.append("9")
            }
        }

        plusBTN.setOnClickListener(operationsOnClickListener)
        minusBTN.setOnClickListener(operationsOnClickListener)
        multiplyBTN.setOnClickListener(operationsOnClickListener)
        divideBTN.setOnClickListener(operationsOnClickListener)
        equalBTN.setOnClickListener(operationsOnClickListener)
        clearBTN.setOnClickListener(operationsOnClickListener)

        zeroBTN.setOnClickListener(numbersOnClicksListener)
        oneBTN.setOnClickListener(numbersOnClicksListener)
        twoBTN.setOnClickListener(numbersOnClicksListener)
        threeBTN.setOnClickListener(numbersOnClicksListener)
        fourBTN.setOnClickListener(numbersOnClicksListener)
        fiveBTN.setOnClickListener(numbersOnClicksListener)
        sixBTN.setOnClickListener(numbersOnClicksListener)
        sevenBTN.setOnClickListener(numbersOnClicksListener)
        eightBTN.setOnClickListener(numbersOnClicksListener)
        nineBTN.setOnClickListener(numbersOnClicksListener)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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

    fun parseEquation(_expression: String): Int {

        val mpSymbol = getString(R.string.btn_multiply)
        val divSymbol = getString(R.string.btn_divide)
        var expression = _expression

        if (expression.first() == '-') {
            expression = "0" + expression
        } else if (expression.first() == mpSymbol[0] || expression.first() == divSymbol[0] || expression.first() == '+') {
            expression = expression.substring(1)
        }

        if (expression.last().toString().matches("""[+$mpSymbol$divSymbol\-]""".toRegex())) {
            expression = expression.substring(0..expression.length - 2)
        }

        val numbers = expression.split("\\D".toRegex()).map { it.toInt() }.toMutableList()
        val operations =
            expression.split("\\d".toRegex()).filter { it.isNotBlank() }.toMutableList()

        println(numbers)
        println(operations)

        while (operations.contains(mpSymbol) || operations.contains(divSymbol)) {

            val multiplyIndex = operations.indexOf(mpSymbol)
            val divideIndex = operations.indexOf(divSymbol)

            if (multiplyIndex in 1..<divideIndex || divideIndex < 0) {
                val temp = numbers[multiplyIndex] * numbers[multiplyIndex + 1]
                numbers.removeAt(multiplyIndex + 1)
                numbers[multiplyIndex] = temp
                operations.removeAt(multiplyIndex)
            } else {
                val temp = numbers[divideIndex] / numbers[divideIndex + 1]
                numbers.removeAt(divideIndex + 1)
                numbers[divideIndex] = temp
                operations.removeAt(divideIndex)
            }

        }

        var result = numbers[0]

        for (operation in operations.withIndex()) {
            if (operation.value == "+") {
                result += numbers[operation.index + 1]
            } else result -= numbers[operation.index + 1]
        }

        return result
    }

    fun appendArithmeticOperation(operation: String) {
        if (inputET.text.contains("\\d$".toRegex()) || inputET.text.isEmpty()) {
            inputET.text.append(operation)
        } else {
            val text = inputET.text.substring(0..<inputET.text.lastIndex) + operation
            inputET.setText(text)
        }

    }

}