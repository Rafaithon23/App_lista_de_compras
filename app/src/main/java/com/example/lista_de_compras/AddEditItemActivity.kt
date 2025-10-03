package com.example.lista_de_compras

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.databinding.ActivityAddEditItemBinding
import com.example.lista_de_compras.models.ItemLista

class AddEditItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditItemBinding
    private var itemId: Int? = null
    private var listId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemId = intent.getIntExtra("ITEM_ID", -1).takeIf { it != -1 }
        listId = intent.getIntExtra("LIST_ID", -1)

        setupCategorySpinner()

        if (itemId != null) {
            loadItem()
            binding.buttonDelete.visibility = View.VISIBLE
        } else if (listId == -1) {
            Toast.makeText(this, "Erro: ID da lista não encontrado.", Toast.LENGTH_SHORT).show()
            finish()
            return
        } else {
            binding.buttonDelete.visibility = View.GONE
        }

        binding.buttonSave.setOnClickListener {
            saveItem()
        }

        binding.buttonDelete.setOnClickListener {
            deleteItem()
        }
    }

    private fun setupCategorySpinner() {
        val categoryArray = resources.getStringArray(R.array.category_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categoryArray)
        binding.spinnerCategory.setAdapter(adapter)
    }

    private fun loadItem() {
        val idToLoad = itemId ?: return
        val item = DataManager.itens.find { it.id == idToLoad }

        item?.let {
            binding.editTextName.setText(it.nome)
            binding.editTextQuantity.setText(it.quantidade.toString())
            binding.editTextUnit.setText(it.unidade)
            binding.checkBoxBought.isChecked = it.comprado
            val categoryArray = resources.getStringArray(R.array.category_array)
            val categoryIndex = categoryArray.indexOf(it.categoria)
            if (categoryIndex != -1) {
                binding.spinnerCategory.setText(categoryArray[categoryIndex], false)
            }
        }
    }

    private fun saveItem() {
        val name = binding.editTextName.text.toString().trim()
        val quantityStr = binding.editTextQuantity.text.toString().trim()
        val unit = binding.editTextUnit.text.toString().trim()
        val category = binding.spinnerCategory.text.toString().trim()
        val bought = binding.checkBoxBought.isChecked

        if (name.isEmpty() || quantityStr.isEmpty() || unit.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos e selecione a categoria.", Toast.LENGTH_SHORT).show()
            return
        }

        val quantity = quantityStr.toDoubleOrNull()
        if (quantity == null || quantity <= 0) {
            Toast.makeText(this, "Quantidade deve ser um número válido (> 0).", Toast.LENGTH_SHORT).show()
            return
        }

        val newItem = ItemLista(
            nome = name,
            quantidade = quantity,
            unidade = unit,
            categoria = category,
            comprado = bought
        )

        if (itemId == null) {
            newItem.listaId = listId
            DataManager.addItem(newItem)
            Toast.makeText(this, "Item adicionado!", Toast.LENGTH_SHORT).show()
        } else {
            DataManager.updateItem(itemId!!, newItem)
            Toast.makeText(this, "Item atualizado!", Toast.LENGTH_SHORT).show()
        }
        finish()
    }

    private fun deleteItem() {
        itemId?.let {
            DataManager.deleteItem(it)
            Toast.makeText(this, "Item excluído!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}