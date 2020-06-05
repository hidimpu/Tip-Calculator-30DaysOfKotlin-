package com.raywenderlich.android.tipcalculator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView

const val HUNDRED_PERCENT = 100.00
const val TIP_INCREMENT_PERCENT = 5
const val PEOPLE_INCREMENT_VALUE = 1
const val MIN_TIP = 0
const val MIN_PEOPLE = 2
const val MAX_TIP = 95
const val MAX_PEOPLE = 20

class MainActivity : AppCompatActivity(), View.OnClickListener, TextWatcher {

    // Top Section
    private lateinit var expensePerPersonTextView: TextView
    private lateinit var billEditText: EditText

    // Tip Section
    private lateinit var addTipButton: ImageButton
    private lateinit var tipTextView: TextView
    private lateinit var subtractTipButton: ImageButton

    // Number of People Section
    private lateinit var addPeopleButton: ImageButton
    private lateinit var numberOfPeopleTextView: TextView
    private lateinit var subtractPeopleButton: ImageButton

    private var numberOfPeople = 4 // set default number of people
    private var tipPercent = 20 // set default tip percent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
    }

    private fun initViews() {
        expensePerPersonTextView = findViewById(R.id.expensePerPersonTextView)
        billEditText = findViewById(R.id.billEditText)

        addTipButton = findViewById(R.id.addTipButton)
        tipTextView = findViewById(R.id.tipTextView)
        subtractTipButton = findViewById(R.id.subtractTipButton)

        addPeopleButton = findViewById(R.id.addPeopleButton)
        numberOfPeopleTextView = findViewById(R.id.numberOfPeopleTextView)
        subtractPeopleButton = findViewById(R.id.subtractPeopleButton)

        addTipButton.setOnClickListener(this)
        subtractTipButton.setOnClickListener(this)

        addPeopleButton.setOnClickListener(this)
        subtractPeopleButton.setOnClickListener(this)

        billEditText.addTextChangedListener(this)
    }


    private fun calculateExpense() {

        val totalBill = billEditText.text.toString().toDouble()

        val totalExpense = ((HUNDRED_PERCENT + tipPercent) / HUNDRED_PERCENT) * totalBill
        val individualExpense = totalExpense / numberOfPeople

        expensePerPersonTextView.text = String.format("$%.2f", individualExpense)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addTipButton -> incrementTip()
            R.id.subtractTipButton -> decrementTip()
            R.id.addPeopleButton -> incrementPeople()
            R.id.subtractPeopleButton -> decrementPeople()
        }
    }

    private fun incrementTip() {
        if (tipPercent != MAX_TIP) {
            tipPercent += TIP_INCREMENT_PERCENT
            tipTextView.text = String.format("%d%%", tipPercent)
        }
        calculateExpense()
    }

    private fun decrementTip() {
        if (tipPercent != MIN_TIP) {
            tipPercent -= TIP_INCREMENT_PERCENT
            tipTextView.text = String.format("%d%%", tipPercent)
        }
        calculateExpense()
    }

    private fun incrementPeople() {
        if (numberOfPeople != MAX_PEOPLE) {
            numberOfPeople += PEOPLE_INCREMENT_VALUE
            numberOfPeopleTextView.text = numberOfPeople.toString()
        }
        calculateExpense()
    }

    private fun decrementPeople() {
        if (numberOfPeople != MIN_PEOPLE) {
            numberOfPeople -= PEOPLE_INCREMENT_VALUE
            numberOfPeopleTextView.text = numberOfPeople.toString()
        }
        calculateExpense()
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        if (!billEditText.text.isEmpty()) {
            calculateExpense()
        }
    }

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
}
