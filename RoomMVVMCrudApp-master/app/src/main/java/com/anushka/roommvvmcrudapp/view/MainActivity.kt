package com.anushka.roommvvmcrudapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.anushka.roommvvmcrudapp.R
import com.anushka.roommvvmcrudapp.RecyclerAdapter
import com.anushka.roommvvmcrudapp.viewmodel.LibrosViewModel
import com.anushka.roommvvmcrudapp.databinding.ActivityMainBinding
import com.anushka.roommvvmcrudapp.model.LibrosDataClass
import com.anushka.roommvvmcrudapp.model.LibrosDatabase
import com.anushka.roommvvmcrudapp.repositorio.LibrosRepository
import com.anushka.roommvvmcrudapp.viewmodel.LibrosViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var librosViewModel: LibrosViewModel
    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = LibrosDatabase.getInstance(application).librosDAO
        val repository = LibrosRepository(dao)
        val factory = LibrosViewModelFactory(repository)
        librosViewModel = ViewModelProvider(this, factory).get(LibrosViewModel::class.java)
        binding.myViewModel = librosViewModel
        binding.lifecycleOwner = this

        librosViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })

        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.rvLibros.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerAdapter({ selectedItem: LibrosDataClass -> listItemClicked(selectedItem) })
        binding.rvLibros.adapter = adapter
        displayLibrosList()
    }

    private fun displayLibrosList() {
        librosViewModel.getSavedLibros().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(librosDataClass: LibrosDataClass) {
        librosViewModel.initUpdateAndDelete(librosDataClass)
    }
}