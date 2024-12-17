package com.example.pertemuan10.repository

import com.example.pertemuan10.data.entity.Mahasiswa
import kotlinx.coroutines.flow.Flow


interface RepositoryMhs {
    suspend fun  insertMhs(mahasiswa: Mahasiswa)

    fun getAllMhs() : Flow<List<Mahasiswa>>

    fun getMhs (nim: String) : Flow<Mahasiswa>

    suspend fun  deleteMhs (mahasiswa: Mahasiswa)

    suspend fun updateMhs (mahasiswa: Mahasiswa)
}