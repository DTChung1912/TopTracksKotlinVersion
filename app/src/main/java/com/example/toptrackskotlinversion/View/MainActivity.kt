package com.example.toptrackskotlinversion.View

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.toptrackskotlinversion.Fragment.freetracks.FreeFragment
import com.example.toptrackskotlinversion.Fragment.purchasestracks.PurchasesFragment
import com.example.toptrackskotlinversion.Fragment.setting.ProfileImageForMain
import com.example.toptrackskotlinversion.Fragment.setting.SettingFragment
import com.example.toptrackskotlinversion.Fragment.toptracks.TopTracksFragment
import com.example.toptrackskotlinversion.Model.Constants.KEY_CURRENT_USER
import com.example.toptrackskotlinversion.Model.Constants.REQUEST_CODE_PERMISSION
import com.example.toptrackskotlinversion.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), MainIterator.MainView, ProfileImageForMain {
    private lateinit var searchMusic: EditText
    private lateinit var currentUser: TextView
    private lateinit var profileImage: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var presenter: MainActivityPresenter

    private val topTracksFragment = TopTracksFragment()
    private val freeFragment = FreeFragment()
    private val purchasesFragment = PurchasesFragment()
    private val settingFragment = SettingFragment(this)

    private var sharedPreferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchMusic = findViewById(R.id.searchMusic)
        currentUser = findViewById(R.id.currentUser)
        profileImage = findViewById(R.id.profileImage)
        bottomNavigationView = findViewById(R.id.navigation_bar)

        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            REQUEST_CODE_PERMISSION
        )
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            REQUEST_CODE_PERMISSION
        )

        presenter = MainActivityPresenter()
        presenter.attachView(this)
        presenter.fetchMain()

        replaceFragment(topTracksFragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_toptracks -> replaceFragment(topTracksFragment)
                R.id.navigation_free -> replaceFragment(freeFragment)
                R.id.navigation_purchases -> replaceFragment(purchasesFragment)
                R.id.navigation_setting -> replaceFragment(settingFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_container, fragment)
            addToBackStack(null)
            commit()
        }

    override fun onSuccess() {
        sharedPreferences = getSharedPreferences("PREFS", MODE_PRIVATE) as SharedPreferences
        var userName = sharedPreferences!!.getString(KEY_CURRENT_USER, null)
        if (userName != null) {
            currentUser.text = userName
        }
    }

    override fun onFailed(msg: String) {
    }

    override fun onError(msg: String?) {
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                sharedPreferences!!.edit().remove(KEY_CURRENT_USER).commit()
                startActivity(
                    Intent(
                        this,
                        StartActivity::class.java
                    ).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
                return true
            }
        }
        return false
    }

    override fun getImageBitmap(imageBitmap: Bitmap) {
        ImageUtils.loadCircleImage(profileImage, imageBitmap)
    }

}