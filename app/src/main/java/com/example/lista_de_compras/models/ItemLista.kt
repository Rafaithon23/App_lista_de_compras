package com.example.lista_de_compras.models

data class ItemLista(
    var id: Int = 0,
    var nome: String = "",
    var quantidade: Double = 0.0,
    var unidade: String = "un",
    var categoria: String = "",
    var comprado: Boolean = false,
    var listaId: Int = 0
)