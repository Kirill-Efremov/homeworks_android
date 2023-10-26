package ru.kpfu.homeworks

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.kpfu.homeworks.databinding.FragmentStartBinding


class StartFragment : Fragment(R.layout.fragment_start) {
    private val fragmentContainerId: Int = R.id.main_activity_container
    private var _viewBinding: FragmentStartBinding? = null
    private val viewBinding: FragmentStartBinding get() = _viewBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _viewBinding = FragmentStartBinding.inflate(inflater)
        return viewBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViews()
    }
    override fun onDestroy() {
        super.onDestroy()
        _viewBinding = null
    }


    private fun initViews() {
        with(viewBinding){
            etPhone.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                }
                private var mSelfChange = false
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
                    val oldSelectionPos = etPhone.selectionStart
                    val isEdit = etPhone.selectionStart != etPhone.length()
                    etPhone.setText(resultStr)
                    if (isEdit) {
                        etPhone.setSelection(if (oldSelectionPos > resultStr.length) resultStr.length else oldSelectionPos)
                    } else {
                        etPhone.setSelection(resultStr.length)
                    }
                    mSelfChange = false
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            btnStart.setOnClickListener {
                val phoneNumber = etPhone.text.toString()


                val questionCount = etQuestionsNumber.text.toString()
                if ( validateQuestionCount(questionCount) &&  validatePhoneNumber(phoneNumber)) {
                    parentFragmentManager.beginTransaction()
                        .replace(fragmentContainerId,  ViewPagerFragment.newInstance(etQuestionsNumber.text.toString().toInt()))
                        .commit()
                    Toast.makeText(context, "All ok", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please enter correct information", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        val regex = Regex("^\\+7 \\(9\\d{2}\\)-\\d{3}-\\d{2}-\\d{2}\$")
        return regex.matches(phoneNumber)
    }
    private fun validateQuestionCount(questionCount: String): Boolean {
        return questionCount.toInt() > 0
    }
}





