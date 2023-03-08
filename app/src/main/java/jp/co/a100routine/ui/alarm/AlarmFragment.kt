package jp.co.a100routine.ui.alarm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import jp.co.a100routine.R
import jp.co.a100routine.databinding.FragmentAlarmBinding

class AlarmFragment : Fragment(R.layout.fragment_alarm) {
    private var _binding: FragmentAlarmBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlarmBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }
}