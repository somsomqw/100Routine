package jp.co.a100routine.ui.calendar

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.a100routine.R
import jp.co.a100routine.databinding.FragmentCalendarBinding
import jp.co.a100routine.db.DBLoader
import jp.co.a100routine.ui.MemoActivity
import jp.co.a100routine.ui.home.RoutineAdapter
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {
    private var _binding: FragmentCalendarBinding?= null
    private val binding get() = _binding!!
    private lateinit var adapter : RoutineAdapter
    private var selectDay = ""
    private lateinit var calendarView: CalendarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView = binding.calendarView
        val recyclerView = binding.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RoutineAdapter(requireContext())
        recyclerView.adapter = adapter

        calendarView.setOnDateChangeListener(object :CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(p0: CalendarView, p1: Int, p2: Int, p3: Int) {
                if(selectDay.equals(String.format("%04d/%02d/%02d", p1, p2+1, p3))){
                    val date = "$p1/$p2/$p3"
                    val intent = Intent(requireContext(), MemoActivity::class.java)
                    intent.putExtra("date", date)
                    startActivity(intent)
                } else {
                    val calendar = Calendar.getInstance()
                    calendar.set(p1, p2, p3)
//                    val day = calendar.timeInMillis.toString().substring(0,6)
//                    adapter.setList(DBLoader(requireContext()).routineList(day.toLong()))
                    changeList(calendar)

                    selectDay = String.format("%04d/%02d/%02d", p1, p2+1, p3)
                }

            }
        })


        val date = Date()
        date.time = calendarView.date
        selectDay = SimpleDateFormat("yyyy/MM/dd").format(date)
        adapter.setList(DBLoader(requireContext()).routineList(calendarView.date.toString().substring(0,6).toLong()))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_calendar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_calendar->{
                val date = selectDay.split("/")

                DatePickerDialog(requireContext(), object :
                    DatePickerDialog.OnDateSetListener{
                    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
                        val calendar = Calendar.getInstance()
                        calendar.set(p1, p2, p3)
                        calendarView.setDate(calendar.timeInMillis, true, false)
                    }
                }, date[0].toInt(), date[1].toInt(), date[2].toInt()).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        val date = selectDay.split("/")
        val calendar = Calendar.getInstance()
        calendar.set(date[0].toInt(), date[1].toInt()-1, date[2].toInt())

        changeList(calendar)
    }

    private fun changeList (calendar: Calendar) {
        val day = calendar.timeInMillis.toString().substring(0,6)
        adapter.setList(DBLoader(requireContext()).routineList(day.toLong()))

        if(adapter.itemCount > 0){
            binding.textMsg.visibility = View.INVISIBLE
        } else {
            binding.textMsg.visibility = View.VISIBLE
        }
    }
}