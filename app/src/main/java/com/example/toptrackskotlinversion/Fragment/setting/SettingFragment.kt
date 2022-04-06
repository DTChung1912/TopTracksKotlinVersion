package com.example.toptrackskotlinversion.Fragment.setting

import android.app.Activity.RESULT_OK
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
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.toptrackskotlinversion.Model.Constants.KEY_CAMERA_GALLERY
import com.example.toptrackskotlinversion.Model.Constants.KEY_CAMERA_TAKEN
import com.example.toptrackskotlinversion.R
import com.example.toptrackskotlinversion.View.CameraActivity
import com.example.toptrackskotlinversion.View.ImageUtils
import java.io.IOException

class SettingFragment(private val profileImageForMain: ProfileImageForMain) : Fragment(),
    SettingIterator.SettingView {

    private lateinit var presenter: SettingFragmentPresenter
    private lateinit var profileImage: ImageView
    private lateinit var upload: Button
    private lateinit var imageSize: TextView

    private var imageUri: String? = null
    private lateinit var uri: Uri
    private lateinit var imageBitmap: Bitmap

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

        val startForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentData = result.data
                    val dataPicture: ByteArray? = intentData?.getByteArrayExtra(KEY_CAMERA_TAKEN)
                    if (dataPicture != null) {
                        imageBitmap = BitmapFactory.decodeByteArray(
                            dataPicture,
                            0,
                            dataPicture!!.size
                        )
                        imageSize.text = dataPicture.size.toString()
                        ImageUtils.loadCircleImage(profileImage, imageBitmap)
                    } else {
                        imageUri = intentData?.getStringExtra(KEY_CAMERA_GALLERY)
                        uri = Uri.parse(imageUri)
                        try {
                            imageBitmap = MediaStore.Images.Media.getBitmap(
                                requireContext().contentResolver,
                                uri
                            )
                            ImageUtils.loadCircleImage(profileImage, imageBitmap)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }

            }

        profileImage.setOnClickListener {
            val intent = Intent(requireContext(), CameraActivity::class.java)
            startForResult.launch(intent)
        }

        upload.setOnClickListener {
            if (imageBitmap != null) {
                profileImageForMain.getImageBitmap(imageBitmap)
            }
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