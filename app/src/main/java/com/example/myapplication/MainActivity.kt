package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.AbsSeekBar
import android.widget.Button
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
private const val TAG = "MainActivity"
private const val INIT_TIP_PERCENT = 15
class MainActivity : AppCompatActivity() {
    private lateinit var baseLabel: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var tipAmountView: TextView
    private lateinit var percentLabel: TextView
    private lateinit var totalAmountView: TextView
    private lateinit var checkingButton: Button
    private lateinit var textshow: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        baseLabel = findViewById(R.id.tipAmountView)
        seekBar  = findViewById(R.id.seekBar)
        tipAmountView = findViewById(R.id.editTextNumberDecimal3)
        percentLabel = findViewById(R.id.percentLabel)
        totalAmountView = findViewById(R.id.totalAmountView)
        checkingButton = findViewById(R.id.buttonCheck)
        textshow = findViewById(R.id.popUpTextView)

        seekBar.progress = INIT_TIP_PERCENT
        percentLabel.text = "$INIT_TIP_PERCENT%"

        seekBar.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Log.i(TAG, "onProgressChanged $progress")
                percentLabel.text = "$progress%"
                Log.i("TAG", "${percentLabel.text}")
                computeTipAndTotal()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })
        baseLabel.addTextChangedListener(object: TextWatcher{
           override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.i(TAG, "afterTextChanged $s")
                computeTipAndTotal()
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                computeTipAndTotal()
            }

            override fun afterTextChanged(s: Editable?) {}

        })
        checkingButton.setOnClickListener {
            val tipPercent = seekBar.progress
            if (tipPercent > 18) {
                // Tip percent greater than 18%
                Log.i(TAG, "Thoughtful choice!")
                textshow.text = "Thoughtful choice"
            } else if (tipPercent >= 15) {
                // Tip percent greater than 15% but less than or equal to 18%
                Log.i(TAG, "Nice of you!")
                textshow.text = "Nice of You!"
            } else {
                // Tip percent less than or equal to 15%
                Log.i(TAG, "We can do be better!")
                textshow.text = "We can do better nextTime!"
            }
        }

    }

    private fun computeTipAndTotal() {
        if (baseLabel.text.isEmpty()) {
            tipAmountView.text = ""
            totalAmountView.text = ""
            return
        } // if
        val baseAmount = baseLabel.text.toString().toDouble()
        val tipPercent = seekBar.progress
        Log.i(TAG, "tipPercentageVal $tipPercent")
        Log.i(TAG, "checkingBaseAmount $baseAmount")
        val tipAmount = baseAmount * tipPercent / 100
        val totalAmount = tipAmount + baseAmount
        // updating the UI
        tipAmountView.text = tipAmount.toString()
        totalAmountView.text = totalAmount.toString()
    } // computeTip
}