package com.example.lista_de_compras

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.databinding.ActivityAddEditItemBinding
import com.example.lista_de_compras.models.ItemLista // Assumindo o nome ItemLista

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

        // 1. Configura o Spinner de Categorias
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
        // Usa o array de strings que você deve ter adicionado ao strings.xml
        val categoryArray = resources.getStringArray(R.array.category_array)
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categoryArray)
        // Liga o adaptador ao AutoCompleteTextView (o seu 'spinnerCategory')
        binding.spinnerCategory.setAdapter(adapter)
    }

    private fun loadItem() {
        val idToLoad = itemId ?: return // Garante que o ID não é nulo
        val item = DataManager.itens.find { it.id == idToLoad }

        item?.let {
            binding.editTextName.setText(it.nome)
            // CORREÇÃO: Usa o tipo Double correto para a quantidade
            binding.editTextQuantity.setText(it.quantidade.toString())
            binding.editTextUnit.setText(it.unidade)
            binding.checkBoxBought.isChecked = it.comprado

            // Define o valor do Spinner
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
        val category = binding.spinnerCategory.text.toString().trim() // Pega o valor do Spinner
        val bought = binding.checkBoxBought.isChecked

        // Validação (RF004)
        if (name.isEmpty() || quantityStr.isEmpty() || unit.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos e selecione a categoria.", Toast.LENGTH_SHORT).show()
            return
        }

        // CORREÇÃO: Usa toDoubleOrNull para números decimais
        val quantity = quantityStr.toDoubleOrNull()
        if (quantity == null || quantity <= 0) {
            Toast.makeText(this, "Quantidade deve ser um número válido (> 0).", Toast.LENGTH_SHORT).show()
            return
        }

        // Cria o item de modelo
        val newItem = ItemLista(
            nome = name,
            quantidade = quantity,
            unidade = unit,
            categoria = category,
            comprado = bought
            // Não atribui ID aqui; o DataManager cuida disso
        )

        if (itemId == null) {
            // New item: Adiciona o ID da lista
            newItem.listaId = listId
            DataManager.addItem(newItem)
            Toast.makeText(this, "Item adicionado!", Toast.LENGTH_SHORT).show()
        } else {
            // Update existing: usa o método que preserva o ID e o listaId original
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