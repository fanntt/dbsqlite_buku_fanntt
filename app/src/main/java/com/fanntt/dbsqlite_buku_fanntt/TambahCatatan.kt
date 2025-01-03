package com.fanntt.dbsqlite_buku_fanntt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fanntt.dbsqlite_buku_fanntt.databinding.ActivityTambahCatatanBinding
import com.fanntt.dbsqlite_buku_fanntt.helper.dbhelper
import com.fanntt.dbsqlite_buku_fanntt.model.modelCatatan

class TambahCatatan : AppCompatActivity() {

    //binding : cara ringkas untuk deklarasi variabel dan widget
    private lateinit var  binding : ActivityTambahCatatanBinding
    private lateinit var db : dbhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityTambahCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        db = dbhelper(this)
        binding.btnTambahData.setOnClickListener(){
            val judul = binding.txtInputJudul.text.toString()
            val penulis = binding.txtInputPenulis.text.toString()
            val isi = binding.txtInputIsi.text.toString()

            val dataCatatan = modelCatatan(0,judul,penulis,isi)
            db.insertDataCatatan(dataCatatan)
            finish();
            Toast.makeText(this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show()
        }

    }
}