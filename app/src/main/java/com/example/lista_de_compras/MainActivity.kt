package com.example.lista_de_compras

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.databinding.ActivitySuasListasBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySuasListasBinding
    private lateinit var listAdapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySuasListasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupRecyclerView()
        setupFab()
    }

    private fun setupRecyclerView() {
        listAdapter = ListAdapter { lista ->
            // Handle list click - open ListDetailActivity
            val intent = Intent(this, ListDetailActivity::class.java)
            intent.putExtra("LIST_ID", lista.id)
            startActivity(intent)
        }
        binding.recyclerViewListas.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewListas.adapter = listAdapter
        loadLists()
    }

    private fun setupFab() {
        binding.fabAddList.setOnClickListener {
            // Open AddEditListActivity for new list
            val intent = Intent(this, AddEditListActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadLists() {
        val lists = DataManager.getUserLists()
        listAdapter.submitList(lists)
    }

    override fun onResume() {
        super.onResume()
        loadLists() // Refresh list when returning from other activities
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        DataManager.clearUserData()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
