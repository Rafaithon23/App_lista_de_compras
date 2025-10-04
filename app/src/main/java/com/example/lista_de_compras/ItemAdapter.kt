package com.example.lista_de_compras

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lista_de_compras.databinding.ItemShoppingBinding
import com.example.lista_de_compras.models.ItemLista

class ItemAdapter(private val onItemClick: (ItemLista) -> Unit, private val onBoughtToggle: (ItemLista) -> Unit) :
    ListAdapter<ItemLista, ItemAdapter.ItemViewHolder>(ItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemShoppingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemShoppingBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClick(getItem(position))
                }
            }
            binding.checkBoxBought.setOnCheckedChangeListener { _, isChecked ->
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item.comprado != isChecked) {
                        onBoughtToggle(item)
                    }
                }
            }
        }

        fun bind(item: ItemLista) {
            binding.textViewItemName.text = item.nome
            binding.textViewItemDetails.text = "${item.quantidade} ${item.unidade} - ${item.categoria}"
            binding.checkBoxBought.isChecked = item.comprado
            val alpha = if (item.comprado) 0.5f else 1.0f
            binding.textViewItemName.animate().alpha(alpha).setDuration(300).start()
            binding.textViewItemDetails.animate().alpha(alpha).setDuration(300).start()
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<ItemLista>() {
        override fun areItemsTheSame(oldItem: ItemLista, newItem: ItemLista): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ItemLista, newItem: ItemLista): Boolean {
            return oldItem == newItem
        }
    }
}
