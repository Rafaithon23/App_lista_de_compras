package com.example.lista_de_compras

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lista_de_compras.databinding.ActivitySuasListasBinding

class SuasListasActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuasListasBinding
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySuasListasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupSearchListener()
        setupFabListener()

        // Se o seu projeto usa MainActivity, é possível que você precise iniciar o login aqui:
        // Se DataManager.currentUser == null, navegue para LoginActivity
    }

    // onResume atualiza a lista (do código do seu colega)
    override fun onResume() {
        super.onResume()
        loadListas()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Suas Listas"
    }

    // Configuração principal da lista
    private fun setupRecyclerView() {
        listAdapter = ListAdapter { lista ->
            val intent = Intent(this, ListDetailActivity::class.java).apply {
                putExtra("LISTA_ID", lista.id)
            }
            startActivity(intent)
        }

        binding.recyclerViewListas.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerViewListas.adapter = listAdapter
    }

    private fun loadListas() {
        val listasOrdenadas = DataManager.getUserLists()
        listAdapter.submitList(listasOrdenadas)
    }

    // Lógica de Busca (RF005)
    private fun setupSearchListener() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filterListas(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun filterListas(query: String) {
        val filteredListas = DataManager.getUserLists().filter {
            it.titulo.contains(query, ignoreCase = true)
        }
        listAdapter.submitList(filteredListas)
    }

    // Lógica do Botão FAB
    private fun setupFabListener() {
        binding.fabAddList.setOnClickListener {
            val intent = Intent(this, AddEditListActivity::class.java)
            startActivity(intent)
        }
    }

    // --- LÓGICA DE MENU E LOGOUT (DO SEU COLEGA) ---

    // Cria o Menu (RF001)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Assume que você tem um arquivo de menu chamado R.menu.main_menu
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Lida com o item do Menu (RF001)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            // Assume que o item de logout no seu menu se chama R.id.action_logout
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Função de Logout (RF001)
    private fun logout() {
        DataManager.clearUserData() // Remove os dados do usuário logado

        val intent = Intent(this, LoginActivity::class.java)
        // Essas flags garantem que o usuário não volte para a tela de listas com o botão "Voltar"
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}