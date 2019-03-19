package dbhelper

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.DatabaseErrorHandler
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.annotations.Nullable
import java.lang.String.valueOf

class DatabaseManeger(context: Context?, name: String?, version: Int, errorHandler: DatabaseErrorHandler):
    SQLiteOpenHelper(context,name,null,version,errorHandler){

    val TABLE_NAME: String = "expance"
    val COL_ID = "e_id"
    val COL_DATE = "e_date"
    val COL_WHERE = "e_where"
    val COL_HOWMUCH = "e_howmuch"


    override fun onCreate(db: SQLiteDatabase?) {
        val sqlQuery: String = "CREATE TABLE " + TABLE_NAME + " (\n" +
                "    " + COL_ID + " INTEGER NOT NULL CONSTRAINT employees_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COL_DATE + " varchar(200) NOT NULL,\n" +
                "    " + COL_WHERE + " varchar(200) NOT NULL,\n" +
                "    " + COL_HOWMUCH + " varchar(200) NOT NULL\n" +
                ");";

        db!!.execSQL(sqlQuery)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val sql: String = "DROP TABLE IF EXISTS $TABLE_NAME;";
        db!!.execSQL(sql)
        onCreate(db)
    }

    fun addExpences(date: String, where: String, howmuch: String): Long {
        val contentValues = ContentValues()
        contentValues.put(COL_DATE, date)
        contentValues.put(COL_WHERE, where)
        contentValues.put(COL_HOWMUCH, howmuch)
        val db = writableDatabase
        return db.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllExpences(): Cursor {

        val db = readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)

    }

    fun updateExpences(id: Int, date: String, where: String, howmuch: String): Boolean {
        val sql: String = "";
        val contentValues = ContentValues()
        contentValues.put(COL_DATE, date)
        contentValues.put(COL_WHERE, where)
        contentValues.put(COL_HOWMUCH, howmuch)
        val db = writableDatabase
        return db.update(TABLE_NAME, contentValues, "COL_ID = ?", arrayOf(valueOf(id))) == 1
    }

    fun deleteAllExpences(id: Int): Boolean {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "COL_ID =?", arrayOf(valueOf(id))) == 1
    }

    companion object {
        val DB_NAME: String = "todays_expance"
        val DB_VERSION: Int = 1
    }





}

































