package jp.co.a100routine.db

import android.annotation.SuppressLint
import android.app.Notification
import android.content.ContentValues
import android.content.Context
import android.widget.Toast
import jp.co.a100routine.model.Routine
import java.util.*
import kotlin.collections.ArrayList

class DBLoader(context: Context) {

    private val context = context
    private var db : DBHelper
    init {
        db = DBHelper(context)
    }

    fun save(title: String, memo:String, getCalendar: Calendar?) {
        val calendar: Calendar = getCalendar ?: Calendar.getInstance()
        val contentValues = ContentValues()
        contentValues.put("title", title)
        contentValues.put("memo", memo)
        contentValues.put("createTime", calendar.timeInMillis)

        db.writableDatabase.insert("note", null, contentValues)
        db.close()
        Toast.makeText(context, "save", Toast.LENGTH_SHORT).show()
    }

    fun update (itemId: Int?, title: String, memo:String) {
        val contentValues = ContentValues()
        contentValues.put("title", title)
        contentValues.put("memo", memo)

        db.writableDatabase.update("note", contentValues, "id = ?", arrayOf(itemId.toString()))
        db.close()
        Toast.makeText(context, "update", Toast.LENGTH_SHORT).show()
    }

    fun delete(id: Int) {
        db.writableDatabase.delete("note", "id=?", arrayOf(id.toString()))
        db.close()
        Toast.makeText(context, "delete", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("Range")
    fun routineList(createTime: Long?) : ArrayList<Routine> {
        val array = ArrayList<Routine>()
        var sql = ""
        sql = if(createTime == null){
            "select * from note order by createTime desc"
        }else{
            "select * from note where createTime like '%$createTime%' order by createTime desc"
        }

        val cursor = db.readableDatabase.rawQuery(sql, null)
        while(cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val title = cursor.getString(cursor.getColumnIndex("title"))
            val memo = cursor.getString(cursor.getColumnIndex("memo"))
            val datetime = cursor.getLong(cursor.getColumnIndex("createTime"))

            val routine = Routine(id, title, memo, datetime)
            array.add(routine)
        }

        return array
    }


}