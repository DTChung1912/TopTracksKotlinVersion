package com.example.toptrackskotlinversion.Fragment.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import com.example.toptrackskotlinversion.R
import com.example.toptrackskotlinversion.View.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetRegisterFragment(private val onRegister: OnRegister) : BottomSheetDialogFragment(),
    RegisterIterator.RegisterView {

    private lateinit var edtFullName: EditText
    private lateinit var edtUserName: EditText
    private lateinit var edtPassword: EditText
    private lateinit var checkBox: CheckBox
    private lateinit var register: Button
    private lateinit var presenter: RegisterFragmentPresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet_register, container, false)

        edtFullName = view.findViewById(R.id.edtFullName)
        edtPassword = view.findViewById(R.id.edtPassword)
        edtUserName = view.findViewById(R.id.edtUserName)
        checkBox = view.findViewById(R.id.checkbox)
        register = view.findViewById(R.id.register)

        presenter = RegisterFragmentPresenter()
        presenter.attachView(this)

        register.setOnClickListener {
            val fullName = edtFullName.text.toString()
            if (edtFullName.text.toString().isBlank() ||
                edtPassword.text.toString().isBlank() ||
                edtUserName.text.toString().isBlank()
            ) {
                Toast.makeText(getContext(), "Please fill in information", Toast.LENGTH_SHORT)
                    .show()
            } else {
                if (checkBox.isChecked) {
                    onRegister.onRegistered(fullName)
                    presenter.fetchRegister()
                } else {
                    Toast.makeText(getContext(), "Please check 'I agree'", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        return view
    }

    override fun onFetchSuccess() {
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    override fun onFailed(msg: String) {
    }

    override fun onError(msg: String?) {
    }
}