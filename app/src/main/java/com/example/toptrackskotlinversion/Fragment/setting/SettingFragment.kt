package com.example.toptrackskotlinversion.Fragment.setting

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.toptrackskotlinversion.Model.Constants.IMAGE_DATA
import com.example.toptrackskotlinversion.Model.Constants.PICTURE_DATA
import com.example.toptrackskotlinversion.R
import com.example.toptrackskotlinversion.View.CameraActivity
import com.example.toptrackskotlinversion.View.ImageUtils
import com.example.toptrackskotlinversion.View.MainActivity
import java.io.IOException

class SettingFragment : Fragment(), SettingIterator.SettingView {

    private lateinit var presenter: SettingFragmentPresenter
    private lateinit var profileImage: ImageView
    private lateinit var upload: Button

    private var imageUri: String? = null
    private lateinit var uri: Uri
    private var dataPicture: ByteArray? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_setting, container, false)

        profileImage = view.findViewById(R.id.profileImage)
        upload = view.findViewById(R.id.upload)

        presenter = SettingFragmentPresenter()
        presenter.attachView(this)
        presenter.fetchSetting()

        profileImage.setOnClickListener {
            startActivity(Intent(requireContext(), CameraActivity::class.java))
        }

        val bundle = arguments
        if (bundle != null) {
            dataPicture = bundle.getByteArray(PICTURE_DATA)
            if (dataPicture != null) {
                val bitmap: Bitmap =
                    BitmapFactory.decodeByteArray(dataPicture, 0, dataPicture!!.size)
                ImageUtils.loadCircleImage(profileImage, bitmap)
            } else {
                imageUri = bundle.getString(PICTURE_DATA)
                uri = Uri.parse(imageUri)
                try {
                    val bitmap: Bitmap =
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
                    ImageUtils.loadCircleImage(profileImage, bitmap)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        upload.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            if (dataPicture != null) {
                intent.putExtra(IMAGE_DATA, dataPicture)
            }
            if (imageUri != null) {
                intent.putExtra(IMAGE_DATA, imageUri)
            }
            startActivity(intent)
        }

        return view
    }


    override fun onFetchSuccess() {
    }

    override fun onFailed(msg: String) {
    }

    override fun onError(msg: String?) {
    }
}