package com.example.toptrackskotlinversion.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.toptrackskotlinversion.Model.Constants.CAMERA_PICTURE
import com.example.toptrackskotlinversion.R
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraOptions
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Facing

class CameraActivity : AppCompatActivity() {

    private lateinit var cameraView: CameraView
    private lateinit var capturePicture: ImageButton
    private lateinit var switchCamera: ImageButton
    private lateinit var imageGallery: ImageButton
    private val SELECT_PICTURE: Int = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        cameraView = findViewById(R.id.cameraView)
        capturePicture = findViewById(R.id.capturePicture)
        switchCamera = findViewById(R.id.switchCamera)
        imageGallery = findViewById(R.id.switchCamera)

        cameraView.setLifecycleOwner(this)
        cameraView.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)

                val intent = Intent(this@CameraActivity, MainActivity::class.java)
                val dataPicture: ByteArray = result.data
                intent.putExtra(CAMERA_PICTURE, dataPicture)
                startActivity(intent)
            }

            override fun onCameraOpened(options: CameraOptions) {
                super.onCameraOpened(options)
            }
        })

        capturePicture.setOnClickListener {
            cameraView.takePicture()
        }

        switchCamera.setOnClickListener {
            when (cameraView.toggleFacing()) {
                Facing.BACK -> {
                    Toast.makeText(this, "Switched to back camera", Toast.LENGTH_SHORT).show()
                }
                Facing.FRONT -> {
                    Toast.makeText(this, "Switched to front camera", Toast.LENGTH_SHORT).show()
                }
            }
        }

        imageGallery.setOnClickListener {
            openGallery()
        }

    }

    private fun openGallery() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                val selectUri: Uri? = data?.data
                if (selectUri != null) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("CHOOSER_PICTURE", selectUri.toString())
                    startActivity(intent)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cameraView.open()
    }

    override fun onPause() {
        super.onPause()
        cameraView.close()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraView.destroy()
    }
}