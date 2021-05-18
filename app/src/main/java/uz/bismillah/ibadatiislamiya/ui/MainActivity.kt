package uz.bismillah.ibadatiislamiya.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import uz.bismillah.ibadatiislamiya.R
import uz.bismillah.ibadatiislamiya.ui.bookmarks.BookmarksFragment
import uz.bismillah.ibadatiislamiya.ui.search.SearchFragment
import uz.bismillah.ibadatiislamiya.ui.unit.UnitFragment

class MainActivity : AppCompatActivity() {
    companion object {
        const val TEXT_SIZE = "textSize"
    }

    lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        preferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        preferences.getFloat(TEXT_SIZE, resources.getDimension(R.dimen.text_normal))

        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, UnitFragment()).addToBackStack(UnitFragment::class.simpleName).commit()

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeMenuItem -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, UnitFragment()).addToBackStack(UnitFragment::class.simpleName).commit()
                    true
                }
                R.id.bookmarksMenuItem -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, BookmarksFragment()).addToBackStack(null).commit()
                    true
                }
                R.id.searchMenuItem -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, SearchFragment()).addToBackStack(SearchFragment::class.simpleName).commit()
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            this.finish()
        } else {
            supportFragmentManager.popBackStackImmediate(
                    supportFragmentManager.getBackStackEntryAt(
                            supportFragmentManager.backStackEntryCount - 1
                    ).id, FragmentManager.POP_BACK_STACK_INCLUSIVE
            )
            bottomNav.menu.getItem(0).isChecked = true
        }
    }
}