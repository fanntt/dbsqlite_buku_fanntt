package com.fanntt.dbsqlite_buku_fanntt

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.fanntt.dbsqlite_buku_fanntt.adapter.adapterCatatan
import com.fanntt.dbsqlite_buku_fanntt.databinding.ActivityMainBinding
import com.fanntt.dbsqlite_buku_fanntt.helper.dbhelper

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var db : dbhelper
    private lateinit var adapterCatatan: adapterCatatan

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        db = dbhelper (this)
        adapterCatatan = adapterCatatan(db.getalldataCatatan(),this,)

        binding.rvDataCatatan.layoutManager = LinearLayoutManager(this)
        binding.rvDataCatatan.adapter = adapterCatatan


        //silahkan buat


        binding.btntambahdata.setOnClickListener(){
            val intent = Intent(this,TambahCatatan::class.java)
            startActivity(intent)
        }




    }
    override fun onResume () {
        super.onResume()
        val newCatatan = db.getalldataCatatan()
        adapterCatatan.refreshData(newCatatan)
    }

}