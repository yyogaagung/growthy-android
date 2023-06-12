package com.yyogadev.growthyapplication.ui.home.deteksi

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.yyogadev.growthyapplication.R
import com.yyogadev.growthyapplication.databinding.ActivityUploadBinding
import com.yyogadev.growthyapplication.ml.TfliteModelPlantsPredict
import com.yyogadev.growthyapplication.retrofit.ApiConfig
import com.yyogadev.growthyapplication.rotateFile
import com.yyogadev.growthyapplication.uriToFile

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder



class UploadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploadBinding
    private lateinit var imageView: ImageView
    private lateinit var button: Button
    private val GALLERY_REQUEST_CODE = 123
//    private var getFile: File? = null
//    private var imageSize: Int = 150

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == REQUEST_CODE_PERMISSIONS) {
//            if (!allPermissionsGranted()) {
//                Toast.makeText(
//                    this,
//                    "Tidak mendapatkan permission.",
//                    Toast.LENGTH_SHORT
//                ).show()
//                finish()
//            }
//        }
//    }
//
//    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
//        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)

        setContentView(binding.root)

        imageView = binding.previewImageView
        button = binding.cameraXButton
        val buttonGalery = binding.galleryButton

        button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED
            ){
                //startCameraX()
                takePicture.launch(null)
            }
            else {
                requestPermission.launch(android.Manifest.permission.CAMERA)
            }
        }
        buttonGalery.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                intent.type = "image/*"
                val mimeType = arrayOf("image/jpg", "image/png", "image/jpeg")
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
                intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                onresult.launch(intent)

            } else {
                requestPermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }


//        if (!allPermissionsGranted()) {
//            ActivityCompat.requestPermissions(
//                this,
//                REQUIRED_PERMISSIONS,
//                REQUEST_CODE_PERMISSIONS
//            )
//        }

//        binding.cameraXButton.setOnClickListener {
//            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
//                == PackageManager.PERMISSION_GRANTED
//            ){
//                //startCameraX()
//                takePicture.launch(null)
//            }
//            else {
//                requestPermissions.launch(android.Manifest.permission.CAMERA)
//            }
//
//        }
//
//        private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){bitmap ->
//            if(bitmap != null){
//                imageView.set
//            }
//        }

//        binding.cameraButton.setOnClickListener { startTakePhoto() }
//        binding.galleryButton.setOnClickListener { startGallery() }
//        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    //camera permission
    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){granted ->
        if (granted){
            takePicture.launch(null)
        } else {
            Toast.makeText(this, "Beri Perizinan, Coba lagi!", Toast.LENGTH_SHORT).show()
        }
    }

    //launch camera
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){bitmap ->
        if (bitmap != null){
            imageView.setImageBitmap(bitmap)

        }
    }

    //get img gallery
    private val onresult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){result->
        Log.i("TAG", "This is the result :  ${result.data} ${result.resultCode}")
        onResultReceived(GALLERY_REQUEST_CODE, result)
    }

    private fun onResultReceived(requestCode: Int, result: ActivityResult?) {
        when(requestCode){
            GALLERY_REQUEST_CODE ->{
                if(result?.resultCode == Activity.RESULT_OK){
                    result.data?.data?.let{uri ->
                        Log.i("TAG", "onResultReceived : $uri")
                        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
                        imageView.setImageBitmap(bitmap)
                        outputGenerator(bitmap)
                    }
                } else {
                    Log.e("TAG", "onActivityResult: error in selecting image")
                }
            }
        }
    }

    private fun outputGenerator(bitmap: Bitmap){
        val model = TfliteModelPlantsPredict.newInstance(this)

// Creates inputs for reference.
        val newBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
        val tfImage = TensorImage.fromBitmap(newBitmap)
        //val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
        //inputFeature0.loadBuffer(byteBuffer)

// Runs model inference and gets result.
        //val outputs = model.process(tfImage)
        //val outputFeature0 = outputs.outputFeature0AsTensorBuffer

// Releases model resources if no longer used.
        model.close()
    }


//    private fun uploadImage() {
////        val intent = Intent(this, PredictionActivity::class.java)
////        startActivity(intent)
////        if (getFile != null) {
////            val file = getFile as File
////
////            val description = "Ini adalah deksripsi gambar".toRequestBody("text/plain".toMediaType())
////            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
////            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
////                "photo",
////                file.name,
////                requestImageFile
////            )
////            val apiService = ApiConfig().getApiUploadService()
////        } else {
////            Toast.makeText(this@UploadActivity, "Silahkan masukan berkas gambar atau ambil gambar dahulu.", Toast.LENGTH_SHORT).show()
////        }
//        val model = TfliteModelPlantsPredict.newInstance(context)
//
//// Creates inputs for reference.
//        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 150, 150, 3), DataType.FLOAT32)
//        val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
//        byteBuffer.order(ByteOrder.nativeOrder())
//
//// get 1D array of 224 * 224 pixels in image
//        val intValues = IntArray(imageSize * imageSize)
//        getFile.getPixels(intValues, 0, getFile.width, 0, 0, getFile.width, getFile.height)
//
//// iterate over pixels and extract R, G, and B values. Add to bytebuffer.
//        var pixel = 0
//        for (i in 0 until imageSize) {
//            for (j in 0 until imageSize) {
//                val value = intValues[pixel++] // RGB
//                byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255f))
//                byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255f))
//                byteBuffer.putFloat((value and 0xFF) * (1f / 255f))
//            }
//        }
//
//        inputFeature0.loadBuffer(byteBuffer)
//
//// Runs model inference and gets result.
//        val outputs = model.process(inputFeature0)
//        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
//
//// Releases model resources if no longer used.
//        model.close()
//
//    }

    private fun startGallery() {
//        val intent = Intent()
//        intent.action = ACTION_GET_CONTENT
//        intent.type = "image/*"
//        val chooser = Intent.createChooser(intent, "Choose a Picture")
//        launcherIntentGallery.launch(chooser)
    }
//
//    private fun startTakePhoto() {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        launcherIntentCamera.launch(intent)
//    }

    private fun startCameraX() {
//        val intent = Intent(this, CameraActivity::class.java)
//        launcherIntentCameraX.launch(intent)
    }

//    private val launcherIntentCameraX = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == CAMERA_X_RESULT) {
//            val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                it.data?.getSerializableExtra("picture", File::class.java)
//            } else {
//                @Suppress("DEPRECATION")
//                it.data?.getSerializableExtra("picture")
//            } as? File
//
//            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
//
//            myFile?.let { file ->
//                rotateFile(file, isBackCamera)
//                getFile = file
//                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
//            }
//        }
//    }
//
//    private val launcherIntentGallery = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        result ->
//        if (result.resultCode == RESULT_OK) {
//            val selectedImg = result.data?.data as Uri
//            selectedImg.let { uri ->
//                val myFile = uriToFile(uri, this@UploadActivity)
//                getFile = myFile
//                binding.previewImageView.setImageURI(uri)
//            }
//        }
//    }
//
//    private lateinit var currentPhotoPath: String
//    private val launcherIntentCamera = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ) {
//        if (it.resultCode == RESULT_OK) {
//            val myFile = File(currentPhotoPath)
//
//            myFile.let {file ->
//                binding.previewImageView.setImageBitmap(BitmapFactory.decodeFile(file.path))
//            }
//        }
//    }

}