package com.example.toptrackskotlinversion.View

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import com.example.toptrackskotlinversion.Fragment.setting.SettingFragment
import com.example.toptrackskotlinversion.Fragment.toptracks.TopTracksFragment
import com.example.toptrackskotlinversion.Model.Constants.CAMERA_PICTURE
import com.example.toptrackskotlinversion.Model.Constants.CHOOSER_PICTURE
import com.example.toptrackskotlinversion.Model.Constants.IMAGE_DATA
import com.example.toptrackskotlinversion.Model.Constants.KEY_CURRENT_USER
import com.example.toptrackskotlinversion.Model.Constants.PICTURE_DATA
import com.example.toptrackskotlinversion.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.IOException

class MainActivity : AppCompatActivity(), MainIterator.MainView {
    private lateinit var searchMusic: EditText
    private lateinit var currentUser: TextView
    private lateinit var profileImage: ImageView
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var presenter: MainActivityPresenter

    private val topTracksFragment = TopTracksFragment()
    private val freeFragment = FreeFragment()
    private val purchasesFragment = PurchasesFragment()
    private val settingFragment = SettingFragment()

    private var sharedPreferences: SharedPreferences? = null

    private lateinit var imagePath: String
    private lateinit var uri: Uri
    private val REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchMusic = findViewById(R.id.searchMusic)
        currentUser = findViewById(R.id.currentUser)
        profileImage = findViewById(R.id.profileImage)
        bottomNavigationView = findViewById(R.id.navigation_bar)

        ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE
        )
        ActivityCompat.requestPermissions(
            this@MainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE
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

        val intent = Intent()
        val dataPicture: ByteArray? = intent.getByteArrayExtra(CAMERA_PICTURE)
        if (dataPicture != null) {
            bottomNavigationView.selectedItemId = R.id.navigation_setting
            val bundle = Bundle()
            bundle.putByteArray(PICTURE_DATA, dataPicture)
            settingFragment.arguments = bundle
            replaceFragment(settingFragment)
        } else {
            imagePath = getIntent().getStringExtra(CHOOSER_PICTURE).toString()
            if (imagePath != null) {
                bottomNavigationView.selectedItemId = R.id.navigation_setting
                val bundle = Bundle()
                bundle.putString(PICTURE_DATA, imagePath)
                settingFragment.arguments = bundle
                replaceFragment(settingFragment)
            } else {
                bottomNavigationView.selectedItemId = R.id.navigation_toptracks
                replaceFragment(topTracksFragment)
            }
        }

        val dataProfile: ByteArray? = intent.getByteArrayExtra(IMAGE_DATA)
        if (dataProfile != null) {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(dataProfile, 0, dataProfile.size)
            ImageUtils.loadCircleImage(profileImage, bitmap)
        } else {
            imagePath = getIntent().getStringExtra(IMAGE_DATA).toString()
            if (imagePath != null) {
                uri = Uri.parse(imagePath)
                try {
                    val bitmap: Bitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                    ImageUtils.loadCircleImage(profileImage, bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
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
}