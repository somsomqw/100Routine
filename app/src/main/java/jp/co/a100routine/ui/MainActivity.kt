package jp.co.a100routine.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import jp.co.a100routine.R
import jp.co.a100routine.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    lateinit var navController: NavController
    private var backKeyPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navView = binding.bottomNavigationView

        navController = findNavController(R.id.fragmentContainer)
        navView.setupWithNavController(navController)
        navController.navigate(R.id.homeFragment)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navController.navigate(R.id.homeFragment)
                }
                R.id.calendarFragment -> {
                    navController.navigate(R.id.calendarFragment)
                }
                R.id.alarmFragment -> {
                    navController.navigate(R.id.alarmFragment)
                }
            }
            true
        }

        navView.selectedItemId = R.id.homeFragment
    }

    /**
     * 端末の戻るボタン監視・
     */
    override fun onBackPressed() {
        //super.onBackPressed()
        // アプリ内の端末をバックさせるボタンをクリック防止
        if(System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis()
            return
        }
        // 2回クリックするとアプリ終了
        if(System.currentTimeMillis() <= backKeyPressedTime + 2500){
            finishAffinity()
        }
    }
}