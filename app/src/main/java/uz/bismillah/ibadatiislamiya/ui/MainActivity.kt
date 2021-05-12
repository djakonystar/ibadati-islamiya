package uz.bismillah.ibadatiislamiya.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.ui.unit.UnitFragment

class MainActivity : AppCompatActivity() {
    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeMenuItem -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, HomePageFragment())
                    true
                }
                R.id.bookmarksMenuItem -> {
                    Toast.makeText(this, "Bookmarks Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.searchMenuItem -> {
                    Toast.makeText(this, "Search Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.aboutMenuItem -> {
                    Toast.makeText(this, "About Me Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, UnitFragment()).commit()
    }
}