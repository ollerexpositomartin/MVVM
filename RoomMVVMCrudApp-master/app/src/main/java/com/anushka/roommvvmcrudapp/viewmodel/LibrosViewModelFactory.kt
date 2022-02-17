package com.anushka.roommvvmcrudapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.anushka.roommvvmcrudapp.repositorio.LibrosRepository
import java.lang.IllegalArgumentException

class LibrosViewModelFactory(
        private val repository: LibrosRepository
        ):ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
     if(modelClass.isAssignableFrom(LibrosViewModel::class.java)){
         return LibrosViewModel(repository) as T
     }
        throw IllegalArgumentException("Unknown View Model class")
    }

}