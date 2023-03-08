package jp.co.a100routine.ui.home

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import jp.co.a100routine.R
import jp.co.a100routine.model.Routine
import jp.co.a100routine.ui.MemoActivity
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RoutineAdapter(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val context = context
    private val arrayList = ArrayList<Routine>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_routine, parent, false)

        return HolderView(view)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = arrayList[position]
        val view = holder as HolderView
        view.textTitle.text = item.title
        view.textMemo.text = item.memo

        val date = Date()
        date.time = item.createTime
        view.textCreatetime.text = SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date)

        view.itemView.setOnClickListener {
            val intent = Intent(context, MemoActivity::class.java)
            intent.putExtra("item", item)
            context.startActivity(intent)
        }

    }

    fun setList(array: ArrayList<Routine>) {
        arrayList.clear()
        arrayList.addAll(array)
        notifyDataSetChanged()
    }

    private class HolderView(view : View) : RecyclerView.ViewHolder(view){
        val textTitle: TextView = view.findViewById(R.id.text_title)
        val textMemo: TextView = view.findViewById(R.id.text_memo)
        val textCreatetime: TextView = view.findViewById(R.id.text_createtime)
    }
}