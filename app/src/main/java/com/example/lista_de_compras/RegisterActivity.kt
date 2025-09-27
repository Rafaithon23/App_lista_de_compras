package com.example.lista_de_compras

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.databinding.ActivityRegisterBinding
import com.example.lista_de_compras.models.User

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TODO: Ajuste os IDs dos seus EditTexts se forem diferentes de 'editTextNome', etc.
        savedInstanceState?.let {
            binding.editTextNome.setText(it.getString("nome", ""))
            binding.editTextEmail.setText(it.getString("email", ""))
            binding.editTextSenha.setText(it.getString("senha", ""))
            binding.editTextConfirmSenha.setText(it.getString("confirmSenha", ""))
        }

        binding.buttonCadastrar.setOnClickListener {
            val nome = binding.editTextNome.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()
            val senha = binding.editTextSenha.text.toString().trim()
            val confirmSenha = binding.editTextConfirmSenha.text.toString().trim()

            if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmSenha.isEmpty()) {
                Toast.makeText(this, getString(R.string.validation_empty), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                Toast.makeText(this, getString(R.string.validation_email), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (senha != confirmSenha) {
                Toast.makeText(this, getString(R.string.validation_password_match), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (DataManager.users.any { it.email == email }) {
                Toast.makeText(this, "Erro: Email já cadastrado.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val newUser  = User(nome, email, senha)
            DataManager.addUser (newUser )
            Toast.makeText(this, getString(R.string.success_register), Toast.LENGTH_LONG).show()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.buttonVoltar.setOnClickListener {
            finish()
        }
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("nome", binding.editTextNome.text.toString())
        outState.putString("email", binding.editTextEmail.text.toString())
        outState.putString("senha", binding.editTextSenha.text.toString())
        outState.putString("confirmSenha", binding.editTextConfirmSenha.text.toString())
    }
}