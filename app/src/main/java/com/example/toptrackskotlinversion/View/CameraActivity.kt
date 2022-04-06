package com.example.toptrackskotlinversion.View

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.toptrackskotlinversion.Model.Constants.KEY_CAMERA_GALLERY
import com.example.toptrackskotlinversion.Model.Constants.KEY_CAMERA_TAKEN
import com.example.toptrackskotlinversion.R
import com.otaliastudios.cameraview.CameraListener
import com.otaliastudios.cameraview.CameraView
import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.controls.Facing

class CameraActivity : AppCompatActivity() {

    private lateinit var cameraView: CameraView
    private lateinit var capturePicture: ImageButton
    private lateinit var switchCamera: ImageButton
    private lateinit var imageGallery: ImageButton
    private lateinit var dataPicture: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        cameraView = findViewById(R.id.cameraView)
        capturePicture = findViewById(R.id.capturePicture)
        switchCamera = findViewById(R.id.switchCamera)
        imageGallery = findViewById(R.id.imageGallery)

        cameraView.setLifecycleOwner(this)
        cameraView.addCameraListener(object : CameraListener() {
            override fun onPictureTaken(result: PictureResult) {
                super.onPictureTaken(result)
                dataPicture = result.data
                if (dataPicture != null) {
                    val intentTaken = Intent()
                    intentTaken.putExtra(KEY_CAMERA_TAKEN, dataPicture)
                    setResult(RESULT_OK, intentTaken)
                    finish()
                }
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

        val getContent =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                val intentGalley = Intent()
                intentGalley.putExtra(KEY_CAMERA_GALLERY, uri.toString())
                setResult(RESULT_OK, intentGalley)
                finish()
            }

        imageGallery.setOnClickListener {
            getContent.launch("image/*")
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