package com.anushka.roommvvmcrudapp.recycler

import androidx.recyclerview.widget.RecyclerView
import com.anushka.roommvvmcrudapp.databinding.ListItemBinding
import com.anushka.roommvvmcrudapp.model.LibrosDataClass

class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){

    fun render(libro: LibrosDataClass, clickListener: (LibrosDataClass) -> Unit){
        binding.tvTitulo.text= libro.titulo
        binding.tvAutor.text = libro.autor
        binding.listItemLayout.setOnClickListener {
            clickListener(libro)
        }
    }
}