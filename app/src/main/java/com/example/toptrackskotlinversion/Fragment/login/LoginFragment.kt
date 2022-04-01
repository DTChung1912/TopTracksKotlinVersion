package com.example.toptrackskotlinversion.Fragment.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.toptrackskotlinversion.R
import com.example.toptrackskotlinversion.View.MainActivity

class LoginFragment : Fragment(), LoginIterator.LoginView {
    private lateinit var presenter: LoginFragmentPresenter
    private var btnLogin: Button? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        presenter = LoginFragmentPresenter()
        presenter.attachView(this)
        btnLogin = view?.findViewById(R.id.btnLogin) as Button

        btnLogin!!.setOnClickListener {
            presenter.fetchLogin()
        }

        return view
    }

    override fun onFetchSuccess() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onFailed(msg: String?) {
    }

    override fun onError(msg: String?) {
    }

}