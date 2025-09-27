package com.example.lista_de_compras

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.databinding.ActivityLoginBinding
import com.example.lista_de_compras.models.User

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageViewLogo.setImageResource(android.R.drawable.ic_menu_gallery)

        savedInstanceState?.let {
            binding.editTextEmail.setText(it.getString("email", ""))
            binding.editTextPassword.setText(it.getString("password", ""))
        }

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, getString(R.string.validation_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, getString(R.string.validation_email), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = DataManager.users.find { it.email == email && it.senha == password }
//            Usuário mockado no código, apenas para teste
            if (user != null || (email == "teste@teste.com" && password == "123456")) {
                if (user == null) {
                    DataManager.currentUser  = User("Usuário Teste", email, password)
                } else {
                    DataManager.currentUser  = user
                }
                Toast.makeText(this, getString(R.string.success_login), Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, getString(R.string.error_credentials), Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonCadastro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return email.contains("@") && email.contains(".")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("email", binding.editTextEmail.text.toString())
        outState.putString("password", binding.editTextPassword.text.toString())
    }
}