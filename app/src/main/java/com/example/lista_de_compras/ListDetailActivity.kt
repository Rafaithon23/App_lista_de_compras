package com.example.lista_de_compras

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.databinding.ActivityListDetailBinding
import com.example.lista_de_compras.models.ItemLista
import java.util.Locale

class ListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDetailBinding
    private lateinit var itemAdapter: ItemAdapter
    private var listId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listId = intent.getIntExtra("LIST_ID", -1)
        if (listId == -1) {
            finish()
            return
        }

        setupToolbar()
        setupRecyclerView()
        setupFab()
        setupSearchListener() // Implementa a busca de itens (RF005)
    }

    override fun onResume() {
        super.onResume()
        loadItems()
    }

    private fun setupToolbar() {
        val list = DataManager.getListaById(listId)
        // Assume que o layout usa um TextView para o título
        binding.textViewListTitle.text = list?.titulo ?: "Detalhes da Lista"
        // Se houver uma Toolbar, descomentar e usar setSupportActionBar
    }

    private fun setupRecyclerView() {
        itemAdapter = ItemAdapter(
            onItemClick = { item ->
                // Navega para a edição
                val intent = Intent(this, AddEditItemActivity::class.java).apply {
                    putExtra("LIST_ID", listId)
                    putExtra("ITEM_ID", item.id)
                }
                startActivity(intent)
            },
            onBoughtToggle = { item ->
                DataManager.toggleComprado(item.id)
                loadItems() // Recarrega para mover o item na lista
            }
        )
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItems.adapter = itemAdapter
    }

    private fun setupFab() {
        binding.fabAddItem.setOnClickListener {
            val intent = Intent(this, AddEditItemActivity::class.java).apply {
                putExtra("LIST_ID", listId)
            }
            startActivity(intent)
        }
    }

    // LÓGICA DE BUSCA DE ITENS (RF005)
    private fun setupSearchListener() {
        // Assume o ID editTextSearch do layout XML
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loadItems(s.toString()) // Chama o loadItems com o termo de busca
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    // Altera loadItems para aceitar um parâmetro opcional de busca
    private fun loadItems(query: String? = null) {
        var allItems = DataManager.getItensForLista(listId)

        // 1. Filtra se houver termo de busca (RF005)
        if (!query.isNullOrEmpty()) {
            allItems = allItems.filter {
                it.nome.contains(query, ignoreCase = true)
            }
        }

        // 2. Lógica de Ordenação e Agrupamento (RF004)
        val nonComprados = allItems.filter { !it.comprado }
        val comprados = allItems.filter { it.comprado }

        // Ordena os não comprados por Categoria e depois por Nome
        val sortedNonComprados = nonComprados
            .sortedWith(compareBy({ it.categoria }, { it.nome }))

        // Junta as listas: Não Comprados (ordenados) + Comprados (no final)
        val finalItems = sortedNonComprados + comprados

        itemAdapter.submitList(finalItems)
    }
}