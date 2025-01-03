package com.fanntt.dbsqlite_buku_fanntt.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.fanntt.dbsqlite_buku_fanntt.R
import com.fanntt.dbsqlite_buku_fanntt.UpdateCatatan
import com.fanntt.dbsqlite_buku_fanntt.helper.dbhelper
import com.fanntt.dbsqlite_buku_fanntt.model.modelCatatan

class adapterCatatan (
    private var  ListCatatan : List<modelCatatan>,
    val context : Context

) : RecyclerView.Adapter<adapterCatatan.CatatanViewHolder>(){

    private val db : dbhelper = dbhelper (context)

    class CatatanViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val txtJudul : TextView = itemView.findViewById(R.id.txtJudul)
        val txtPenulis : TextView = itemView.findViewById(R.id.txtPenulis)
        val txtIsi : TextView = itemView.findViewById(R.id.txtIsi)


        val btnEdit : ImageView = itemView.findViewById(R.id.btnEditItem)
        val btnDelete : ImageView = itemView.findViewById(R.id.btnDeleteItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data_catatan,
            parent, false)
        return CatatanViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ListCatatan.size
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        val nCatatan = ListCatatan[position]
        holder.txtJudul.text = nCatatan.judul
        holder.txtPenulis.text = nCatatan.penulis
        holder.txtIsi.text = nCatatan.isi

//        holder.cardMahasiswa.setOnClickListener(){
//
//            val intent = Intent(context, DetailPage::class.java)
//            //put Extra
//            intent.putExtra("nama",ListMahasiswa[position].nama)
//            intent.putExtra("nim", ListMahasiswa[position].nim)
//            intent.putExtra("jurusan",ListMahasiswa[position].jurusan)
//            context.startActivity(intent)
//
//            Toast.makeText(context,ListMahasiswa[position].nama, Toast.LENGTH_SHORT).show()
//
//        }

        holder.btnEdit.setOnClickListener(){
            val intent = Intent(holder.itemView.context, UpdateCatatan::class.java).apply {
                putExtra("id_cttn", nCatatan.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener(){
            db.deleteCttn(nCatatan.id)
            refreshData(db.getalldataCatatan())
            Toast.makeText(holder.itemView.context, "Berhasil Delete Data Catatan ${nCatatan.penulis}", Toast.LENGTH_SHORT).show()
        }


    }
    // refresh data
    fun refreshData(newMahasiswa : List <modelCatatan>){
        ListCatatan = newMahasiswa
        notifyDataSetChanged()
    }
    }
