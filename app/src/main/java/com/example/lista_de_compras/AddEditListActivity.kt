package com.example.lista_de_compras

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.databinding.ActivityAddEditListBinding
import com.example.lista_de_compras.models.ListaDeCompras

class AddEditListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditListBinding
    private var listId: Int? = null
    private var selectedImageUri: String? = null

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedImageUri = it.toString()
            binding.imageViewPreview.setImageURI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listId = intent.getIntExtra("LIST_ID", -1).takeIf { it != -1 }

        if (listId != null) {
            loadList()
            binding.buttonDelete.visibility = android.view.View.VISIBLE
        }

        binding.buttonSelectImage.setOnClickListener {
            imagePickerLauncher.launch("image/*")
        }

        binding.buttonSave.setOnClickListener {
            saveList()
        }

        binding.buttonDelete.setOnClickListener {
            deleteList()
        }
    }

    private fun loadList() {
        val list = DataManager.getListaById(listId!!)
        list?.let {
            binding.editTextTitle.setText(it.titulo)
            selectedImageUri = it.imagemUri
            if (it.imagemUri != null) {
                binding.imageViewPreview.setImageURI(Uri.parse(it.imagemUri))
            }
        }
    }

    private fun saveList() {
        val title = binding.editTextTitle.text.toString().trim()
        if (title.isEmpty()) {
            Toast.makeText(this, "Título é obrigatório", Toast.LENGTH_SHORT).show()
            return
        }

        if (listId == null) {
            // New list
            val newList = ListaDeCompras(
                titulo = title,
                userId = DataManager.currentUser?.email ?: "",
                imagemUri = selectedImageUri
            )
            DataManager.addLista(newList)
            Toast.makeText(this, "Lista criada", Toast.LENGTH_SHORT).show()
        } else {
            // Update existing
            val existingList = DataManager.getListaById(listId!!)
            existingList?.let {
                val updatedList = ListaDeCompras(
                    titulo = title,
                    userId = it.userId,
                    imagemUri = selectedImageUri
                )
                DataManager.updateLista(listId!!, updatedList)
                Toast.makeText(this, "Lista atualizada", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }

    private fun deleteList() {
        listId?.let {
            DataManager.deleteLista(it)
            Toast.makeText(this, "Lista excluída", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
