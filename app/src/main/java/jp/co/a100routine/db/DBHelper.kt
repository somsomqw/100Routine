package jp.co.a100routine.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "routine.db", null, 1, null) {

    override fun onCreate(db: SQLiteDatabase?) {
        //id, title, memo, datetime

        val sql =
            "create table note (id integer primary key autoincrement, title text, memo text not null, createTime text)"
        db!!.execSQL(sql)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}