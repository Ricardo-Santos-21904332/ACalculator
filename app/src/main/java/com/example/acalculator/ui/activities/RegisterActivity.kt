package com.example.acalculator.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.acalculator.R
import com.example.acalculator.ui.viewmodels.RegisterActivityViewModel
import com.example.acalculator.entities.User
import kotlinx.android.synthetic.main.activity_register.*
import org.apache.commons.codec.digest.DigestUtils

class RegisterActivity : AppCompatActivity() {
    private lateinit var viewModel: RegisterActivityViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(RegisterActivityViewModel::class.java)
        setContentView(R.layout.activity_register)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.button_criar_conta)
    fun onClickRegister() {
        val nome = user_name.text.toString()
        val email = user_email.text.toString()
        val password = user_password.text.toString()
        val confirmarPassword = user_password_confirm.text.toString()
        if (nome != "" && email != "" && password != "" && confirmarPassword != "") {
            if (registoComSucesso(password, confirmarPassword)) {
                val hash: String = DigestUtils.sha256Hex(password)
                val user =
                    User(nome, email, hash)
                viewModel.insert(user)
                Toast.makeText(this, "Conta criada com sucesso", Toast.LENGTH_SHORT)
                    .show()
                finish()
                startActivity(Intent(this, LoginActivity::class.java))
            } else {
                Toast.makeText(this, "Passwords diferentes", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Parametros inválidos", Toast.LENGTH_SHORT).show()
        }
    }

    @OnClick(R.id.button_back_login)
    fun onClickBackButton() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

    fun registoComSucesso(password: String, confirmarPassword: String): Boolean {
        if (password == confirmarPassword) {
            return true
        }
        return false
    }
}
