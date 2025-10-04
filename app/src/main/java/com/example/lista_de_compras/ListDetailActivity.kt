package com.example.lista_de_compras

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.databinding.ActivityListDetailBinding

class ListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDetailBinding
    private lateinit var itemAdapter: ItemAdapter
    private var listId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listId = intent.getIntExtra("LISTA_ID", -1)
        if (listId == -1) {
            finish()
            return
        }

        setupToolbar()
        setupRecyclerView()
        setupFab()
        setupSearchListener()
    }

    override fun onResume() {
        super.onResume()
        loadItems()
    }

    private fun setupToolbar() {
        val list = DataManager.getListaById(listId)
        if (list == null) {
            Toast.makeText(this, "Lista não encontrada", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        binding.textViewListTitle.text = list.titulo
    }

    private fun setupRecyclerView() {
        itemAdapter = ItemAdapter(
            onItemClick = { item ->
                val intent = Intent(this, AddEditItemActivity::class.java).apply {
                    putExtra("LIST_ID", listId)
                    putExtra("ITEM_ID", item.id)
                }
                startActivity(intent)
            },
            onBoughtToggle = { item ->
                DataManager.toggleComprado(item.id)
                loadItems()
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

    private fun setupSearchListener() {
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                loadItems(s.toString())
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun loadItems(query: String? = null) {
        var allItems = DataManager.getItensForLista(listId)

        if (!query.isNullOrEmpty()) {
            allItems = allItems.filter {
                it.nome.contains(query, ignoreCase = true)
            }
        }

        val nonComprados = allItems.filter { !it.comprado }
        val comprados = allItems.filter { it.comprado }

        val sortedNonComprados = nonComprados
            .sortedWith(compareBy({ it.categoria }, { it.nome }))

        val finalItems = sortedNonComprados + comprados

        itemAdapter.submitList(finalItems)
    }
}