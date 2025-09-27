package com.example.lista_de_compras

import android.os.Bundle
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

        if (itemId != null) {
            loadItem()
            binding.buttonDelete.visibility = android.view.View.VISIBLE
        } else if (listId == -1) {
            finish()
            return
        }

        binding.buttonSave.setOnClickListener {
            saveItem()
        }

        binding.buttonDelete.setOnClickListener {
            deleteItem()
        }
    }

    private fun loadItem() {
        val item = DataManager.itens.find { it.id == itemId }
        item?.let {
            binding.editTextName.setText(it.nome)
            binding.editTextQuantity.setText(it.quantidade.toString())
            binding.editTextUnit.setText(it.unidade)
            binding.editTextCategory.setText(it.categoria)
            binding.checkBoxBought.isChecked = it.comprado
        }
    }

    private fun saveItem() {
        val name = binding.editTextName.text.toString().trim()
        val quantityStr = binding.editTextQuantity.text.toString().trim()
        val unit = binding.editTextUnit.text.toString().trim()
        val category = binding.editTextCategory.text.toString().trim()
        val bought = binding.checkBoxBought.isChecked

        if (name.isEmpty()) {
            Toast.makeText(this, "Nome é obrigatório", Toast.LENGTH_SHORT).show()
            return
        }

        val quantity = quantityStr.toIntOrNull() ?: 1
        if (quantity <= 0) {
            Toast.makeText(this, "Quantidade deve ser maior que 0", Toast.LENGTH_SHORT).show()
            return
        }

        if (itemId == null) {
            // New item
            val newItem = ItemLista(
                nome = name,
                quantidade = quantity,
                unidade = unit,
                categoria = category,
                comprado = bought,
                listaId = listId
            )
            DataManager.addItem(newItem)
            Toast.makeText(this, "Item adicionado", Toast.LENGTH_SHORT).show()
        } else {
            // Update existing
            val existingItem = DataManager.itens.find { it.id == itemId }
            existingItem?.let {
                val updatedItem = ItemLista(
                    id = itemId!!,
                    nome = name,
                    quantidade = quantity,
                    unidade = unit,
                    categoria = category,
                    comprado = bought,
                    listaId = it.listaId
                )
                DataManager.updateItem(itemId!!, updatedItem)
                Toast.makeText(this, "Item atualizado", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    private fun deleteItem() {
        itemId?.let {
            DataManager.deleteItem(it)
            Toast.makeText(this, "Item excluído", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
