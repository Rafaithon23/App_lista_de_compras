package com.example.lista_de_compras

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lista_de_compras.databinding.ItemListBinding
import com.example.lista_de_compras.models.ListaDeCompras

class ListAdapter(
    private val onItemClick: (ListaDeCompras) -> Unit
) :
    ListAdapter<ListaDeCompras, ListAdapter.ListViewHolder>(ListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ListViewHolder(private val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
        }

        fun bind(lista: ListaDeCompras) {
            binding.textViewListName.text = lista.titulo

            if (lista.imagemUri != null) {
                binding.imageViewList.setImageURI(android.net.Uri.parse(lista.imagemUri))
            } else {
                binding.imageViewList.setImageResource(R.drawable.placeholder)
            }
        }
    }

    class ListDiffCallback : DiffUtil.ItemCallback<ListaDeCompras>() {
        override fun areItemsTheSame(oldItem: ListaDeCompras, newItem: ListaDeCompras): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ListaDeCompras, newItem: ListaDeCompras): Boolean {
            return oldItem == newItem
        }
    }
}