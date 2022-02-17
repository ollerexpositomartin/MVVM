package com.anushka.roommvvmcrudapp.repositorio

import com.anushka.roommvvmcrudapp.model.LibrosDataClass
import com.anushka.roommvvmcrudapp.model.LibrosDAO

class LibrosRepository(private val dao: LibrosDAO) {

    val libros = dao.getAllLibros()

    suspend fun insert(librosDataClass: LibrosDataClass): Long {
        return dao.insertLibros(librosDataClass)
    }

    suspend fun update(librosDataClass: LibrosDataClass): Int {
        return dao.updateLibros(librosDataClass)
    }

    suspend fun delete(librosDataClass: LibrosDataClass): Int {
        return dao.deleteLibro(librosDataClass)
    }

    suspend fun deleteAll(): Int {
        return dao.deleteAll()
    }
}