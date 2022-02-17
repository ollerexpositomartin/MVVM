package com.anushka.roommvvmcrudapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "libros")
data class LibrosDataClass(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "titulo")
    var titulo: String,

    @ColumnInfo(name = "autor")
    var autor: String

)