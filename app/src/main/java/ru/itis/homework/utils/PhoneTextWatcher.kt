package ru.itis.homework.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class PhoneTextWatcher(private val editText: EditText) : TextWatcher {
    private var mSelfChange = false

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (mSelfChange) {
            return
        }
        val preparedStr = s.replace(Regex("(\\D*)"), "")
        var resultStr = ""
        for (i in preparedStr.indices) {
            resultStr = when (i) {
                0 -> resultStr.plus("+7 (")
                1 -> resultStr.plus(preparedStr[i])
                2 -> resultStr.plus(preparedStr[i])
                3 -> resultStr.plus(preparedStr[i])
                4 -> resultStr.plus(")-".plus(preparedStr[i]))
                5 -> resultStr.plus(preparedStr[i])
                6 -> resultStr.plus(preparedStr[i])
                7 -> resultStr.plus("-".plus(preparedStr[i]))
                8 -> resultStr.plus(preparedStr[i])
                9 -> resultStr.plus("-".plus(preparedStr[i]))
                10 -> resultStr.plus(preparedStr[i])
                else -> resultStr
            }
        }

        mSelfChange = true
        val oldSelectionPos = editText.selectionStart
        val isEdit = editText.selectionStart != editText.length()
        editText.setText(resultStr)
        if (isEdit) {
            editText.setSelection(if (oldSelectionPos > resultStr.length) resultStr.length else oldSelectionPos)
        } else {
            editText.setSelection(resultStr.length)
        }
        mSelfChange = false
    }

    override fun afterTextChanged(s: Editable?) {
    }
}
