package com.example.lista_de_compras

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lista_de_compras.databinding.ActivityCadastroBinding

class CadastroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializando o ViewBinding para a tela de cadastro
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurando o clique do botão "Criar"
        binding.buttonCreate.setOnClickListener {
            validateCadastro()
        }
    }

    private fun validateCadastro() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val confirmPassword = binding.editTextConfirmPassword.text.toString()

        // Validação: Verificando se os campos estão vazios
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
            return
        }

        // Validação: Verificando o formato do e-mail
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Por favor, insira um e-mail válido.", Toast.LENGTH_SHORT).show()
            return
        }

        // Validação: Verificando se as senhas coincidem
        if (password != confirmPassword) {
            Toast.makeText(this, "As senhas não coincidem.", Toast.LENGTH_SHORT).show()
            return
        }

        // Se todas as validações passarem
        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

        // Aqui você pode adicionar a lógica para armazenar os dados do usuário,
        // mas o projeto não exige persistência em disco.
        // O próximo passo seria navegar de volta para a tela de login.
        finish()
    }
}