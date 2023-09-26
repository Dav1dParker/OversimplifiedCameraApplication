package com.example.cameraapplication


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.Preview
import androidx.camera.view.PreviewView
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var backCameraIsOn: Boolean = false
        setContentView(R.layout.activity_main)
        if (checkCameraPermission() and checkStoragePermission()){
            CameraService(getPreview()).startCameraALT(this)
        }
        val btnShot: Button = findViewById(R.id.btn_take_photo)
        btnShot.setOnClickListener {
            saveCurrentDateTime()
        }
        val changeBtn: ImageButton = findViewById(R.id.changeButton)
        changeBtn.setOnClickListener {
            backCameraIsOn = backCameraIsOn != true
            if (backCameraIsOn)
            {
                CameraService(getPreview()).startCamera(this)
            }
            else
            {
                CameraService(getPreview()).startCameraALT(this)
            }
        }



        val btnDate: ImageButton = findViewById(R.id.btn_date)
        btnDate.setOnClickListener {
            val fragment = DateFragment()
            fragment.show(supportFragmentManager, fragment.tag)
        }

    }

    private fun checkCameraPermission(): Boolean {
        return if(checkSelfPermission(Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED) {        
            true
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
            false
        }
    }

    private fun checkStoragePermission(): Boolean {
        return if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
            PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
            false
        }
    }

    private fun getPreview(): Preview{
        val viewFinder: PreviewView = findViewById(R.id.viewFinder)
        return Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }
    }

    private fun saveCurrentDateTime() {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault())
        val dateTime = Date()
        val dateTimeString = dateFormat.format(dateTime)
        val file = File(
            getExternalFilesDir("photos"),
            "date.txt"
        )
        file.appendText("$dateTimeString\n")
    }
}
