package com.example.lista_de_compras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import com.example.lista_de_compras.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    // Declaração da variável de binding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializando o ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurando o clique do botão "Acessar"
        binding.buttonLogin.setOnClickListener {
            // Chamando a função de validação
            validateLogin()
        }

        // Configurando o clique do botão "Criar Conta" para navegação
        binding.buttonCreateAccount.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validateLogin() {
        // Acessando o texto dos campos usando o binding
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()

        // Verificando se os campos estão vazios
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Validação de formato de e-mail simples
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, insira um e-mail válido.", Toast.LENGTH_SHORT).show()
            return
        }

        // Simulação de login
        // Conforme o documento do projeto, você pode usar dados simulados ("mocados")
        if (email == "teste@email.com" && password == "123") {
            // Se o login for bem-sucedido, navega para a tela de listas
            val intent = Intent(this, SuasListasActivity::class.java)
            startActivity(intent)
            finish() // Impede que o usuário volte para a tela de login pelo botão "voltar"
        } else {
            // Se as credenciais estiverem erradas
            Toast.makeText(this, "Credenciais inválidas. Tente novamente.", Toast.LENGTH_SHORT).show()
        }
    }
}