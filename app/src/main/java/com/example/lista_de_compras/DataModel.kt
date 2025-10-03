package com.example.lista_de_compras
import android.net.Uri
enum class Categoria(val nome: String) {
    FRUTA("Fruta"),
    VERDURA("Verdura"),
    CARNE("Carne"),
    PADARIA("Padaria"),
    OUTROS("Outros")
}

data class ItemDeCompra(
    val id: String = java.util.UUID.randomUUID().toString(), // ID único para fácil manipulação
    var nome: String,
    var quantidade: Double,
    var unidade: String, // Ex: "kg", "un", "L"
    var categoria: Categoria,
    var comprado: Boolean = false // Requisito: marcar como comprado/não comprado
)

data class ListaDeCompras(
    val id: String = java.util.UUID.randomUUID().toString(), // ID único
    var titulo: String,
    var imagemUri: Uri? = null, // Imagem opcional (Uri para imagens locais)
    val itens: MutableList<ItemDeCompra> = mutableListOf() // A lista de itens que ela contém
)