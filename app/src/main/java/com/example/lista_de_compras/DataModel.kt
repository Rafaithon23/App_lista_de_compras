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
    val id: String = java.util.UUID.randomUUID().toString(),
    var nome: String,
    var quantidade: Double,
    var unidade: String,
    var categoria: Categoria,
    var comprado: Boolean = false
)

data class ListaDeCompras(
    val id: String = java.util.UUID.randomUUID().toString(),
    var titulo: String,
    var imagemUri: Uri? = null,
    val itens: MutableList<ItemDeCompra> = mutableListOf()
)