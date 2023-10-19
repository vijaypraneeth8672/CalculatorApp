package com.example.mycalculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private var tvInput : TextView? = null
    var lastNumber = false
    var lastDot = false
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvInput = findViewById(R.id.tvInput)
    }
    fun onDigit(view : View){
        tvInput?.append((view as Button).text)
        lastNumber= true
        lastDot = false
    }
    fun onDecimalPoint(view: View){
        if(lastNumber && !lastDot){
            tvInput?.append(".")
            lastNumber = false
            lastDot = true
        }
    }
    fun onClear(view :View){
        tvInput?.text = ""
        lastNumber = false
        lastDot = false

    }
    fun onOperator(view: View){
        tvInput?.text?.let {
            if(lastNumber && !isOperatorPresent(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumber = false
                lastDot = false
            }
        }
    }
    fun onEqual(view: View){
        if(lastNumber){
            var tvValue = tvInput?.text.toString()
            var prefix =""
            try {
                if(tvValue.startsWith("-")){
                    prefix="-"
                    tvValue = tvValue.substring(1)
                }
                when{
                    tvValue.contains("/") -> {
                        val splitValue = tvValue.split("/")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix +one
                        }
                        val result = one.toDouble()/two.toDouble()
                        tvInput?.text = removeZero(result.toString())
                    }
                    tvValue.contains("x") -> {
                        val splitValue = tvValue.split("x")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix +one
                        }
                        val result = one.toDouble()*two.toDouble()
                        tvInput?.text = removeZero(result.toString())
                    }
                    tvValue.contains("+") -> {
                        val splitValue = tvValue.split("+")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix +one
                        }
                        val result = one.toDouble()+two.toDouble()
                        tvInput?.text = removeZero(result.toString())
                    }
                    tvValue.contains("-") -> {
                        val splitValue = tvValue.split("-")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if(prefix.isNotEmpty()){
                            one = prefix +one
                        }
                        val result = one.toDouble()-two.toDouble()
                        tvInput?.text = removeZero(result.toString())
                    }
                }

            }catch (e:ArithmeticException){
                e.printStackTrace()
            }
        }
    }
    private fun removeZero(result : String) : String {
        var value = result
        if(result.contains(".0"))
        {
            value = result.substring(0,result.length-2)
        }
        return value
    }
    private fun isOperatorPresent(value : String) : Boolean {
        return if(value.startsWith("-")){
            false
        }else{
            (value.contains("/") || value.contains("+") || value.contains("x") || value.contains("-"))
        }
    }
}
