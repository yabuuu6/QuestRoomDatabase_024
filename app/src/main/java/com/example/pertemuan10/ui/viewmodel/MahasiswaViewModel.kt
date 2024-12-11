package com.example.pertemuan10.ui.viewmodel

import androidx.compose.runtime.currentComposer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pertemuan10.data.entity.Mahasiswa
import com.example.pertemuan10.repository.RepositoryMhs
import kotlinx.coroutines.launch

class MahasiswaViewModel(
    private val repositoryMhs: RepositoryMhs
) :ViewModel(){

    var uiState by mutableStateOf(MhsUIState())

    fun updateState(mahasiswaEvent: MahasiswaEvent){
        uiState = uiState.copy(
            mahasiswaEvent = mahasiswaEvent,
        )

    }
    private  fun validateFields() : Boolean{
        val event = uiState.mahasiswaEvent
        val errorState = FormErrorState(
            nim = if (event.nim.isNotEmpty()) "NIM tidak boleh kosong" else null,
            nama = if (event.nama.isNotEmpty()) "Nama tidak boleh kosong" else null,
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) "Jenis Kelamin tidak boleh kosong" else null,
            alamat = if (event.alamat.isNotEmpty()) "Alamat tidak boleh kosong" else null,
            kelas = if (event.kelas.isNotEmpty()) "Kelas tidak boleh kosong" else null,
            angkatan = if (event.angkatan.isNotEmpty()) "Angkatan tidak boleh kosong" else null
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun saveData() {

        val currentEvent = uiState.mahasiswaEvent

        if (validateFields()) {
            viewModelScope.launch {
                try {
                    repositoryMhs.insertMhs(currentEvent.toMahasiswaEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        mahasiswaEvent = MahasiswaEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception) {
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        }else {
            uiState = uiState.copy(
                snackBarMessage = "Data tidak valid. Periksa kembali data Anda."
            )
        }
    }
    //reset pesan snackbar setelah ditampilkan
    fun resetSnackBarMessage() {
        uiState = uiState.copy(
            snackBarMessage = null
        )
    }
}
//data class variable yang menyimpan
//data input form
data class MahasiswaEvent(
    val nim : String = "",
    val nama : String = "",
    val jenisKelamin : String = "",
    val alamat : String = "",
    val kelas : String = "",
    val angkatan : String = ""
)

//Menyimpan input form ke dalam entity
fun MahasiswaEvent.toMahasiswaEntity(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    jenisKelamin = jenisKelamin,
    alamat = alamat,
    kelas = kelas,
    angkatan = angkatan
)

data class FormErrorState(
    val nim : String? = null,
    val nama : String? = null,
    val jenisKelamin : String? = null,
    val alamat : String? = null,
    val kelas : String? = null,
    val angkatan : String? = null
) {
    fun isValid(): Boolean {
        return nim != null && nama != null && jenisKelamin != null &&
                alamat != null && kelas != null && angkatan != null

    }
}

data class MhsUIState(
    val mahasiswaEvent: MahasiswaEvent = MahasiswaEvent(),
    val isEntryValid: FormErrorState = FormErrorState(),
    val snackBarMessage: String? = null,
)