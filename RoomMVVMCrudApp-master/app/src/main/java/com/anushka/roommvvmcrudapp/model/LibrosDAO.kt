package com.anushka.roommvvmcrudapp.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface LibrosDAO {

    @Insert
    suspend fun insertLibros(librosDataClass: LibrosDataClass) : Long

    @Update
    suspend fun updateLibros(librosDataClass: LibrosDataClass) : Int

    @Delete
    suspend fun deleteLibro(librosDataClass: LibrosDataClass) : Int

    @Query("DELETE FROM libros")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM libros")
    fun getAllLibros():Flow<List<LibrosDataClass>>
}