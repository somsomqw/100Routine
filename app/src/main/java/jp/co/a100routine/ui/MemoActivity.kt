package jp.co.a100routine.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import jp.co.a100routine.R
import jp.co.a100routine.db.DBLoader
import jp.co.a100routine.model.Routine
import java.util.*

class MemoActivity: AppCompatActivity() {
    private lateinit var editTitle: EditText
    private lateinit var editMemo: EditText
    private lateinit var deleteButton: Button
    private lateinit var saveButton: Button
    private var item : Routine? = null
    private var itemId : Int? = 0
    private var date : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memo)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "memo"

        editTitle = findViewById(R.id.edit_title)
        editMemo = findViewById(R.id.edit_memo)
        deleteButton = findViewById(R.id.delete_button)
        saveButton = findViewById(R.id.save_button)



        intent?.let {
            item = intent.getSerializableExtra("item") as Routine?
            editTitle.setText(item?.title)
            editMemo.setText(item?.memo)
            itemId = item?.id
            date = intent.getStringExtra("date")
        }

        Log.d("aaaaa", date.toString())
        buttonInit(itemId.toString())
    }

    private fun buttonInit(itemId: String) {
        if(itemId != null){
            Log.d("aaaaa", "aaaaa")
        } else {
            Log.d("aaaaa", "bbbbb")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_memo, menu)
        val deleteBtn = menu!!.findItem(R.id.action_delete)
        if(this.item == null){
            deleteBtn.isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
            R.id.action_save->{
                val title = editTitle.text.toString()
                val memo = editMemo.text.toString()
                if(memo != ""){
                    var calendar: Calendar? = null
                    if(date != null){
                        calendar = Calendar.getInstance()
                        val date = this.date!!.split("/")
                        calendar.set(date[0].toInt(),date[1].toInt(), date[2].toInt() )
                    }
                    if(this.item != null) {
                        //이미 있으면 업데이트
                        DBLoader(applicationContext).update(itemId, title, memo)
                        finish()
                    } else {
                        DBLoader(applicationContext).save(title, memo, calendar)
                        finish()
                    }

                }
            }
            R.id.action_delete->{
                if(this.item != null) {
                    DBLoader(applicationContext).delete((this.item!!.id))
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}