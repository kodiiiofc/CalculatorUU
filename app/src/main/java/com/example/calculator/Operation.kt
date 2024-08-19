package com.example.calculator

class Operation(private val first: Double, private val second: Double) {

    fun summary() = first + second
    fun difference() = first - second
    fun multiply() = first * second
    fun division() = first / second

}