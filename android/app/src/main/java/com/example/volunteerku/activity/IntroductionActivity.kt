package com.example.volunteerku.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.volunteerku.data.SaveImageResponse
import com.example.volunteerku.databinding.ActivityIntroductionBinding
import com.example.volunteerku.service.UserService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class IntroductionActivity : AppCompatActivity() {
    lateinit var binding: ActivityIntroductionBinding
    private lateinit var imageView: ImageView
    private var body: MultipartBody.Part? = null
    private var imageUrl: String = "default.png"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = binding.imageView
        imageView.setOnClickListener {
            checkPermission()
        }

        binding.introductionEndBtn.setOnClickListener {
            saveImage()
        }
    }

    private fun saveImage() {
        if (body == null) {
            goToSuccessSignupActivity()
        } else {
            val userService = UserService()
            userService.setOnResponseListener(object :
                UserService.OnResponseListener() {
                override fun <T> getResponseBody(body: T, isSuccess: Boolean, err: String) {
                    if (isSuccess) {
                        if (body is SaveImageResponse) {
                            imageUrl = body.imageFileName
                            goToSuccessSignupActivity()
                        }
                    } else {
                        Toast.makeText(applicationContext, err, Toast.LENGTH_SHORT).show()
                    }
                }
            }).saveImage(body!!)
        }
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) { // 권한이 허용되었을 때
                checkPermission()
            } else {  // 권한이 거부되었을 때
                Toast.makeText(
                    this,
                    "권한승인이 거부되었습니다.", Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun showContextPopupPermission() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("권한 체크")
            .setMessage("반드시 저장소 접근 권한이 허용되어야 합니다.")
            .setPositiveButton("OK") { _, _ ->
                // 권한 요청하기
                permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss() // 다이얼로그 종료하기
            }
        builder.create().show()
    }

    private fun checkPermission() {
        when {
            ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> { // 권한이 허용되었을 때
                getProFileImage()
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> { // 권한이 거부되었을 때
                showContextPopupPermission()
            }

            else -> { // 권한이 허용되지도, 거부되지도 않은 경우 -> 권한 요청하기
                permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }
    }

    private fun getProFileImage() {
        Log.d("사진호출", "사진변경 호출")
        val chooserIntent = Intent(Intent.ACTION_CHOOSER)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        chooserIntent.putExtra(Intent.EXTRA_INTENT, intent)
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "사용할 앱을 선택해주세요.")
        launcher.launch(chooserIntent)
    }

    private var launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val imagePath = result.data!!.data

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imagePath)
                val circularBitmap = getCircularBitmap(bitmap)
                imageView.setImageBitmap(circularBitmap)

                val file = File(absolutelyPath(imagePath, this))
                val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

                body = MultipartBody.Part.createFormData("faceImageFile", file.name, requestFile)
            }
        }

    fun absolutelyPath(path: Uri?, context: Context): String {
        val proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        val c: Cursor? = context.contentResolver.query(
            path!!, proj, null,
            null, null
        )
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

    private fun goToSuccessSignupActivity() {
        Log.d("이미지", "goToSuccessSignupActivity")
        val intent =
            Intent(this, SuccessSignupActivity::class.java)
        intent.putExtra("imageUrl", imageUrl)
        if (binding.introductionEt.text.toString().isEmpty())
            intent.putExtra("introduction", " ")
        else intent.putExtra("introduction", binding.introductionEt.text.toString())
        startActivity(intent)
    }
}
