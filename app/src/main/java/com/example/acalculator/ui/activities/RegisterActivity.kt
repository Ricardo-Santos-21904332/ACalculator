package com.example.acalculator.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModelProviders
import butterknife.ButterKnife
import butterknife.OnClick
import com.example.acalculator.R
import com.example.acalculator.domain.register.registadoComSucesso
import com.example.acalculator.ui.viewmodels.RegisterActivityViewModel
import kotlinx.android.synthetic.main.activity_register.*

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
        if (password == confirmarPassword) {
            viewModel.onClickRegister(nome, email, password)
            if (registadoComSucesso) {
                Toast.makeText(this, "Conta registada com sucesso!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }

    @OnClick(R.id.button_back_login)
    fun onClickBackButton() {
        finish()
        startActivity(Intent(this, LoginActivity::class.java))
    }

}
