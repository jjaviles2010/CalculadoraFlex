package com.jlapp.calculadoraflex.watchers

import android.telephony.PhoneNumberUtils.formatNumber
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.lang.ref.WeakReference

class DecimalTextWatcher(editText: EditText, val totalDecimalNumber: Int = 2) :
    TextWatcher {
    private val editTextWeakReference: WeakReference<EditText> =
        WeakReference(editText)

    init {
        formatNumber(editTextWeakReference.get()!!.text.toString())
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    override fun afterTextChanged(editable: Editable) {
        formatNumber(editable.toString())
    }

    private fun getTotalDecimalNumber(): String {
        val decimalNumber = StringBuilder()
        for (i in 1..totalDecimalNumber) {
            decimalNumber.append("0")
        }
        return decimalNumber.toString()
    }

}