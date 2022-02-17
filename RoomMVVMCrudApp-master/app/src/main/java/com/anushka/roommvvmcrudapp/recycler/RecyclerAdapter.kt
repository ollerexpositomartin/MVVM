package com.anushka.roommvvmcrudapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.anushka.roommvvmcrudapp.databinding.ListItemBinding
import com.anushka.roommvvmcrudapp.model.LibrosDataClass
import com.anushka.roommvvmcrudapp.recycler.ViewHolder

class RecyclerAdapter(private val clickListener: (LibrosDataClass) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {
    private val librosList = ArrayList<LibrosDataClass>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return librosList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.render(librosList[position], clickListener)
    }

    fun setList(librosDataClasses: List<LibrosDataClass>) {
        librosList.clear()
        librosList.addAll(librosDataClasses)

    }

}