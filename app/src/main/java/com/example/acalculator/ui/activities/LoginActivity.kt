package com.example.acalculator.ui.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.acalculator.R
import com.example.acalculator.domain.auth.sucesso
import com.example.acalculator.domain.auth.token
import com.example.acalculator.ui.viewmodels.LoginActivityViewModel
import kotlinx.android.synthetic.main.activity_login.*

var nomeUserDrawer = ""
var emailUserDrawer = ""

class LoginActivity : AppCompatActivity() {
    private lateinit var viewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)
        ButterKnife.bind(this)
        logo.setImageResource(R.drawable.logo)
        val pref: SharedPreferences = getSharedPreferences("save", 0)
        if (pref.getBoolean("login?", false)) {
            token = pref.getString("token", "token")!!
            nomeUserDrawer = pref.getString("email", "email")!!
            emailUserDrawer = nomeUserDrawer
            Toast.makeText(this, "Login com sucesso", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    @OnClick(R.id.button_register)
    fun onClickRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    @OnClick(R.id.button_login)
    fun onClickLogin() {
        val pref: SharedPreferences = getSharedPreferences("save", 0)
        val email = user_email_login.text.toString()
        val password = user_password_login.text.toString()
        viewModel.onClickLogin(email, password)
        if (sucesso) {
            nomeUserDrawer = email
            emailUserDrawer = email
            val editor = pref.edit()
            editor.putBoolean("login?", true)
            editor.putString("email", email)
            editor.apply()
            Toast.makeText(this, "Login com sucesso", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
