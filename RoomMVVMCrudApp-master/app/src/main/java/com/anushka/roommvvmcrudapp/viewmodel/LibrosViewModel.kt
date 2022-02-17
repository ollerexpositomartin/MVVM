package com.anushka.roommvvmcrudapp.viewmodel

import android.util.Patterns
import androidx.lifecycle.*
import com.anushka.roommvvmcrudapp.model.LibrosDataClass
import com.anushka.roommvvmcrudapp.repositorio.LibrosRepository
import com.anushka.roommvvmcrudapp.view.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class LibrosViewModel(private val repository: LibrosRepository) : ViewModel() {
    private var isUpdateOrDelete = false
    private lateinit var librosToUpdateOrDelete: LibrosDataClass
    val inputTitulo = MutableLiveData<String>()
    val inputAutor = MutableLiveData<String>()
    val saveOrUpdateButtonText = MutableLiveData<String>()
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()
    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Guardar"
        clearAllOrDeleteButtonText.value = "Borrar todo"
    }

    fun initUpdateAndDelete(librosDataClass: LibrosDataClass) {
        inputTitulo.value = librosDataClass.titulo
        inputAutor.value = librosDataClass.autor
        isUpdateOrDelete = true
        librosToUpdateOrDelete = librosDataClass
        saveOrUpdateButtonText.value = "Modificar"
        clearAllOrDeleteButtonText.value = "Borrar"
    }

    fun saveOrUpdate() {
        if (inputTitulo.value == null) {
            statusMessage.value = Event("introduce titulo")
        } else if (inputAutor.value == null) {
            statusMessage.value = Event("introduce autor")
        } else {
            if (isUpdateOrDelete) {
                librosToUpdateOrDelete.titulo = inputTitulo.value!!
                librosToUpdateOrDelete.autor = inputAutor.value!!
                updateLibro(librosToUpdateOrDelete)
            } else {
                val titulo = inputTitulo.value!!
                val autor = inputAutor.value!!
                insertLibro(LibrosDataClass(0, titulo, autor))
                inputTitulo.value = ""
                inputAutor.value = ""
            }
        }
    }

    private fun insertLibro(librosDataClass: LibrosDataClass) = viewModelScope.launch {
        val newRowId = repository.insert(librosDataClass)
        if (newRowId > -1) {
            statusMessage.value = Event("Libro insertado $newRowId")
        } else {
            statusMessage.value = Event("Error")
        }
    }


    private fun updateLibro(librosDataClass: LibrosDataClass) = viewModelScope.launch {
        val noOfRows = repository.update(librosDataClass)
        if (noOfRows > 0) {
            inputTitulo.value = ""
            inputAutor.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Guardar"
            clearAllOrDeleteButtonText.value = "Borrar todo"
            statusMessage.value = Event("$noOfRows modificado")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    fun getSavedLibros() = liveData {
        repository.libros.collect {
            emit(it)
        }
    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            deleteLibros(librosToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    private fun deleteLibros(librosDataClass: LibrosDataClass) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(librosDataClass)
        if (noOfRowsDeleted > 0) {
            inputTitulo.value = ""
            inputAutor.value = ""
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Guardar"
            clearAllOrDeleteButtonText.value = "Borrar todo"
            statusMessage.value = Event("$noOfRowsDeleted fila borrarda")
        } else {
            statusMessage.value = Event("Error")
        }
    }

    private fun clearAll() = viewModelScope.launch {
        val noOfRowsDeleted = repository.deleteAll()
        if (noOfRowsDeleted > 0) {
            statusMessage.value = Event("$noOfRowsDeleted Libros borrados")
        } else {
            statusMessage.value = Event("Error")
        }
    }
}