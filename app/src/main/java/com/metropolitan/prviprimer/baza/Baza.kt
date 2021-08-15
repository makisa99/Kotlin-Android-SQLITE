package com.metropolitan.prviprimer.baza

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast

class Baza(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    companion object {
        val DATABASE_NAME = "Ispit.db"
        val TABLE_NAME = "student"
        val ID = "ID"
        val IME = "IME"
        val FAKULTET = "FAKULTET"
        val SMER = "SMER"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create table $TABLE_NAME (ID INTEGER PRIMARY KEY AUTOINCREMENT,IME TEXT,FAKULTET TEXT,SMER TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun insertData(ime: String, fakultet: String, smer: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(IME, ime)
        contentValues.put(FAKULTET, fakultet)
        contentValues.put(SMER, smer)
        db.insert(TABLE_NAME, null, contentValues)
        Log.d("Uspesno insertovao: ", "ime: $ime, fakultet: $fakultet, smer: $smer")
        return true;
    }

    fun listOfStudentInfo(): ArrayList<StudentInfo> {
        val db = this.writableDatabase
        val res = db.rawQuery("select * from " + TABLE_NAME, null)
        val studentList = ArrayList<StudentInfo>()
        while (res.moveToNext()) {
            var studentInfo = StudentInfo()
            studentInfo.id = Integer.valueOf(res.getString(0))
            studentInfo.ime = res.getString(1)
            studentInfo.fakultet = res.getString(2)
            studentInfo.smer = res.getString(3)
            studentList.add(studentInfo)
        }
        return studentList
    }

    fun getAllStudentData(): ArrayList<StudentInfo> {
        val stuList: ArrayList<StudentInfo> = arrayListOf<StudentInfo>()
        val cursor: Cursor = getReadableDatabase().query(
            TABLE_NAME,
            arrayOf(ID, IME, FAKULTET, SMER),
            null,
            null,
            null,
            null,
            null
        )
        try {
            if (cursor.getCount() != 0) {
                cursor.moveToFirst()
                if (cursor.getCount() > 0) {
                    do {
                        val id: Int = cursor.getInt(cursor.getColumnIndex(ID))
                        val ime: String = cursor.getString(cursor.getColumnIndex(IME))
                        val fakultet: String = cursor.getString(cursor.getColumnIndex(FAKULTET))
                        val smer: String = cursor.getString(cursor.getColumnIndex(SMER))
                        val studentInfo = StudentInfo()
                        studentInfo.id = id
                        studentInfo.ime = ime
                        studentInfo.fakultet = fakultet
                        studentInfo.smer = smer
                        stuList.add(studentInfo)
                    } while ((cursor.moveToNext()))
                }
            }
        } finally {
            cursor.close()
        }

        return stuList
    }

    fun getParticularStudentData(id: Int): StudentInfo {
        var studentInfo = StudentInfo()
        val db = this.readableDatabase
        val selectQuery = "SELECT  * FROM " + TABLE_NAME + " WHERE " + ID + " = '" + id + "'"
        val cursor = db.rawQuery(selectQuery, null)
        try {
            if (cursor.count != 0) {
                cursor.moveToFirst();
                if (cursor.count > 0) {
                    do {
                        studentInfo.id = cursor.getInt(cursor.getColumnIndex(ID))
                        studentInfo.ime = cursor.getString(cursor.getColumnIndex(IME))
                        studentInfo.fakultet = cursor.getString(cursor.getColumnIndex(FAKULTET))
                        studentInfo.smer = cursor.getString(cursor.getColumnIndex(SMER))
                    } while ((cursor.moveToNext()));
                }
            }
        } finally {
            cursor.close();
        }
        return studentInfo
    }

    fun updateData(id: String, ime: String, fakultet: String, smer: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, id)
        contentValues.put(IME, ime)
        contentValues.put(FAKULTET, fakultet)
        contentValues.put(SMER, smer)
        db.update(TABLE_NAME, contentValues, "ID = ?", arrayOf(id))
        return true
    }

    fun deleteData(id : String) : Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME,"ID = ?", arrayOf(id))

    }


}