package com.example.volunteerku.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.data.SaveImageResponse
import com.example.volunteerku.data.SignupRequest
import com.example.volunteerku.databinding.ActivityIntroductionBinding
import com.example.volunteerku.service.UserService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class IntroductionActivity : AppCompatActivity() {
    private val PICK_FROM_ALBUM = 1
    lateinit var binding: ActivityIntroductionBinding
    private lateinit var imageView: ImageView
    private var body: MultipartBody.Part? = null
    private var imageUrl: String = "default.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        binding.introductionEndBtn.setOnClickListener {
            if (body != null) saveImage()
            singup()
        }
    }

    private fun saveImage() {
        val userService = UserService()
        userService.setOnResponseListener(object : UserService.OnResponseListener() {
            override fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
                if (isSuccess) {
                    if (body is SaveImageResponse) {
                        imageUrl = body.imageFileName
                    }
                } else {
                    Toast.makeText(applicationContext, err, Toast.LENGTH_SHORT).show()
                }
            }
        }).saveImage(body!!)
    }

    private fun init() {
        imageView = binding.imageView
        imageView.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, PICK_FROM_ALBUM)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FROM_ALBUM && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
            val circularBitmap = getCircularBitmap(bitmap)
            imageView.setImageBitmap(circularBitmap)

            val file = File(absolutelyPath(selectedImage, this))
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            body = MultipartBody.Part.createFormData("profile", file.name, requestFile)
        }
    }

    private fun absolutelyPath(path: Uri?, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(path!!, proj, null, null, null)
        val index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()

        val result = c?.getString(index!!)
        return result!!
    }

    private fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        val outputBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(outputBitmap)

        val paint = Paint()
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        canvas.drawCircle(
            (bitmap.width / 2).toFloat(),
            (bitmap.height / 2).toFloat(),
            (bitmap.width / 2).toFloat(),
            paint
        )

        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, 0f, 0f, paint)

        return outputBitmap
    }

    private fun singup() {

        val requestBody = SignupRequest(
            user.email,
            user.password,
            user.nickname,
            user.major,
            imageUrl,
            binding.introductionEt.text.toString()
        )

        val userService = UserService()
        userService.setOnResponseListener(object : UserService.OnResponseListener() {
            override fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
                if (isSuccess) {
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, err, Toast.LENGTH_SHORT).show()
                }
            }
        }).signup(requestBody)

        val intent = Intent(this, SuccessSignupActivity::class.java)
        startActivity(intent)
    }
}
