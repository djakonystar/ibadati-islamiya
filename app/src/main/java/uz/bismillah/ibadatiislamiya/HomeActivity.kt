package uz.bismillah.ibadatiislamiya

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_home.*
import uz.bismillah.ibadatiislamiya.fragments.HomePageFragment

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeMenuItem -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentLayout, HomePageFragment())
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
    }
}