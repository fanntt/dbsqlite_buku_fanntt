package com.fanntt.dbsqlite_buku_fanntt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fanntt.dbsqlite_buku_fanntt.databinding.ActivityTambahCatatanBinding
import com.fanntt.dbsqlite_buku_fanntt.databinding.ActivityUpdateCatatanBinding
import com.fanntt.dbsqlite_buku_fanntt.helper.dbhelper
import com.fanntt.dbsqlite_buku_fanntt.model.modelCatatan

class UpdateCatatan : AppCompatActivity() {

    private lateinit var binding : ActivityUpdateCatatanBinding
    private lateinit var db : dbhelper
    private  var cttnId : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateCatatanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = dbhelper(this)

        cttnId = intent.getIntExtra("id_cttn",-1)
        if(cttnId == -1){
            finish()
            return
        }
        // proses menampilkan data ke edit text di edit view

        val mhs = db.getCttnbyId(cttnId)
        binding.etEditJudul.setText(mhs.judul)
        binding.etEditPenulis.setText(mhs.penulis)
        binding.etEditIsi.setText(mhs.isi)

        //update dari button
        binding.btnEditNote.setOnClickListener(){
            val newJudul= binding.etEditJudul.text.toString()
            val newPenulis = binding.etEditPenulis.text.toString()
            val newIsi = binding.etEditIsi.text.toString()

            val updatedMhs = modelCatatan(cttnId, newJudul,newPenulis,newIsi)
            db.updateDataCatatan(updatedMhs)
            finish()
            Toast.makeText(this, "Update Berhasil", Toast.LENGTH_SHORT).show()
        }

    }
}