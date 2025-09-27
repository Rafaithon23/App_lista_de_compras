package com.example.lista_de_compras

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.databinding.ActivityListDetailBinding
import com.example.lista_de_compras.models.ItemLista

class ListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListDetailBinding
    private lateinit var itemAdapter: ItemAdapter
    private var listId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        listId = intent.getIntExtra("LIST_ID", -1)
        if (listId == -1) {
            finish()
            return
        }

        val list = DataManager.getListaById(listId)
        binding.textViewListTitle.text = list?.titulo ?: "Lista"

        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        itemAdapter = ItemAdapter(
            onItemClick = { item ->
                // Open AddEditItemActivity for editing
                val intent = Intent(this, AddEditItemActivity::class.java)
                intent.putExtra("ITEM_ID", item.id)
                startActivity(intent)
            },
            onBoughtToggle = { item ->
                DataManager.toggleComprado(item.id)
                loadItems()
            }
        )
        binding.recyclerViewItems.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewItems.adapter = itemAdapter
        loadItems()
    }

    private fun setupFab() {
        binding.fabAddItem.setOnClickListener {
            val intent = Intent(this, AddEditItemActivity::class.java)
            intent.putExtra("LIST_ID", listId)
            startActivity(intent)
        }
    }

    private fun loadItems() {
        val items = DataManager.getItensForLista(listId)
        itemAdapter.submitList(items)
    }

    override fun onResume() {
        super.onResume()
        loadItems()
    }
}
