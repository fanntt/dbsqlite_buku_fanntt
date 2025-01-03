package com.fanntt.dbsqlite_buku_fanntt.helper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.fanntt.dbsqlite_buku_fanntt.model.modelCatatan

class dbhelper  (context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    fun closeDatabase(){
        if(database.isOpen && databaseOpen){
            database.close()
            databaseOpen = false

            Log.i("Database", "Database Closed")
        }
    }

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                JUDUL + " TEXT," +
                PENULIS + " TEXT," +
                ISI + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addName(judul : String, penulis : String ,isi  : String){

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(JUDUL, judul)
        values.put(PENULIS, penulis)
        values.put(ISI, isi)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getName(): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)

    }
    //get all data
    fun getalldataCatatan() : List<modelCatatan>{
        val catatanList = mutableListOf<modelCatatan>()
        val db = readableDatabase
        val query ="SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
            val judul = cursor.getString(cursor.getColumnIndexOrThrow(JUDUL))
            val penulis = cursor.getString(cursor.getColumnIndexOrThrow(PENULIS))
            val isi = cursor.getString(cursor.getColumnIndexOrThrow(ISI))

            Log.d("Databasehelper", "Fetched ID : $id , namaMahasiswa : $judul")
            val nCatatan = modelCatatan(id, judul, penulis ,isi)
            catatanList.add(nCatatan)
        }
        cursor.close()
        db.close()
        return catatanList
    }

    //get all data
    /*fun getAllData () : MutableList<ModelMahasiswa>{
        if(!databaseOpen){
            database = INSTANCE.writableDatabase
            databaseOpen = true //dbnya kita open

            Log.i("Database", "Database Open")
        }

       val data: MutableList<ModelMahasiswa> = ArrayList()
        val cursor = database.rawQuery("SELECT * FROM "+ TABLE_NAME,null)
        cursor.use { cur ->
            if (cursor.moveToFirst()){
                do{
                    val mahasiswa = ModelMahasiswa()
                    mahasiswa.id = cur.getInt(cur.getColumnIndex(ID_COL))
                    mahasiswa.nama = cur.getString(cur.getColumnIndex(NAMA_MAHASISWA))
                    mahasiswa.nim= cur.getInt(cur.getColumnIndex(NIM))
                    mahasiswa.jurusan=cur.getString(cur.getColumnIndex(JURUSAN))
                    data.add(mahasiswa)
                }while (cursor.moveToNext())
            }
        }
        return data
    }*/


    companion object{
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "DB_CATATAN"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "tb_catatan"

        // below is the variable for id column
        val ID_COL = "id"


        val JUDUL = "judul"
        val PENULIS = "penulis"
        val ISI = "isi"


        private lateinit var database: SQLiteDatabase
        private var  databaseOpen : Boolean = false

        private lateinit var INSTANCE : dbhelper
    }

    fun insertDataCatatan(mhs: modelCatatan) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(JUDUL, mhs.judul)
            put(PENULIS, mhs.penulis)
            put(ISI, mhs.isi)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    fun updateDataCatatan(cttn : modelCatatan)
    {
        val db = readableDatabase
        val values = ContentValues().apply {
            put(JUDUL, cttn.judul)
            put(PENULIS, cttn.penulis)
            put(ISI, cttn.isi)
        }
        val whereClause = "$ID_COL =?"
        val whereArgs = arrayOf(cttn.id.toString())

        db.update(TABLE_NAME, values,whereClause,whereArgs)
        db.close()
    }

    fun getCttnbyId(cttnId : Int): modelCatatan{
        val db = readableDatabase
        val query = "SELECT * FROM  $TABLE_NAME WHERE  $ID_COL = $cttnId"
        val cursor= db.rawQuery(query,null)
        cursor.moveToNext()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL))
        val judul = cursor.getString(cursor.getColumnIndexOrThrow(JUDUL))
        val penulis = cursor.getString(cursor.getColumnIndexOrThrow(PENULIS))
        val isi = cursor.getString(cursor.getColumnIndexOrThrow(ISI))

        cursor.close()
        db.close()
        return modelCatatan(id,judul,penulis,isi)
    }

    fun deleteCttn (CttnId : Int){
        val db = readableDatabase
        val whereClause = "$ID_COL =?"
        val whereArgs = arrayOf(CttnId.toString())

        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }
}