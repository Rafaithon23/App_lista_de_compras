package com.example.lista_de_compras.models

data class ItemLista(
    var id: Int = 0,
    var nome: String = "",
    // CORREÇÃO: Inicializa o Double com 0.0
    var quantidade: Double = 0.0,
    var unidade: String = "un", // Ex: "un", "kg", "L"
    var categoria: String = "",
    var comprado: Boolean = false,
    // Garante que o item está ligado a uma lista (crucial para o DataManager)
    var listaId: Int = 0
)