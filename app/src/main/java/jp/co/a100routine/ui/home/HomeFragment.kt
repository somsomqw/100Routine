package jp.co.a100routine.ui.home

import android.content.Intent
import android.content.SyncAdapterType
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import jp.co.a100routine.R
import jp.co.a100routine.databinding.FragmentHomeBinding
import jp.co.a100routine.db.DBLoader
import jp.co.a100routine.ui.MemoActivity

class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding?= null
    private val binding get() = _binding!!

    private lateinit var adapter : RoutineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            startActivity(Intent(requireContext(), MemoActivity::class.java))
        }

        adapter = RoutineAdapter(requireContext())
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        adapter.setList(DBLoader(requireContext()).routineList(null))

        if(adapter.itemCount > 0){
            binding.textMsg.visibility = View.INVISIBLE
        } else {
            binding.textMsg.visibility = View.VISIBLE
        }
    }
}