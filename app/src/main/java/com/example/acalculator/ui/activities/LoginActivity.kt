package com.example.acalculator.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.acalculator.ui.viewmodels.LoginActivityViewModel
import com.example.acalculator.R
import com.example.acalculator.data.remote.requests.Login
import com.example.acalculator.data.remote.services.AuthService
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.codec.digest.DigestUtils
import retrofit2.Retrofit

var nomeUserDrawer = ""
var emailUserDrawer = ""

class LoginActivity(private val retrofit: Retrofit) : AppCompatActivity() {
    private lateinit var viewModel: LoginActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        viewModel = ViewModelProviders.of(this).get(LoginActivityViewModel::class.java)
        ButterKnife.bind(this)
        logo.setImageResource(R.drawable.logo)
    }

    @OnClick(R.id.button_register)
    fun onClickRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    @OnClick(R.id.button_login)
    fun onClickLogin() {
        var nome: String
        val email = user_email_login.text.toString()
        val password = user_password_login.text.toString()
        val hash: String = DigestUtils.sha256Hex(password)
        if (authenticateUser(email, password)) {
            emailUserDrawer = email
            Toast.makeText(this, "Login com sucesso", Toast.LENGTH_SHORT)
                .show()
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            Toast.makeText(this, "Nome de utilizador ou password incorrectos", Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun authenticateUser(email: String, password: String): Boolean {
        var loginSucesso = false
        val service = retrofit.create(AuthService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.login(Login(email, password))
            if (response.isSuccessful) {
                println(response.body())
                loginSucesso = true
            }else{
                loginSucesso = false
            }
        }
        return loginSucesso
    }
}
