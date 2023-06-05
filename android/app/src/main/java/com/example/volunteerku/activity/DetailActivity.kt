package com.example.volunteerku.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication
import com.example.volunteerku.data.Room
import com.example.volunteerku.databinding.ActivityDetailBinding
import com.example.volunteerku.service.BASE_URL
import com.example.volunteerku.service.UserRetrofitInterface
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var retrofitInterface: UserRetrofitInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val roomId = intent.getIntExtra("roomId", -1)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInterface = retrofit.create(UserRetrofitInterface::class.java)

        val call: Call<Room> = retrofitInterface.getRoomDetail(roomId)

        call.enqueue(object : Callback<Room> {
            override fun onResponse(call: Call<Room>, response: Response<Room>) {
                if (response.isSuccessful) {
                    val room = response.body()

                    if (room != null) {
                        // 게시글 정보를 받아와서 UI에 표시
                        binding.titleView.setText(room.title)
                        // binding.location.text = room.
                        binding.ClosedDateView.text = room.closedDateTime
                        binding.LimitheadCountView.text = room.limitHeadCount.toString()
                        binding.contentsView.setText(room.content)

                        binding.regButton.setOnClickListener {
                            // 다이얼로그를 띄워서 사용자에게 확인을 받기
                            AlertDialog.Builder(this@DetailActivity)
                                .setTitle("봉사활동 신청")
                                .setMessage("봉사활동에 신청하시겠습니까?")
                                .setPositiveButton("예") { dialog, which ->
                                    // 예를 선택한 경우 처리
                                    applyForVolunteerActivity(room.title)
                                    val kakaoUrl = room.kakaoUrl
                                    if (kakaoUrl != null) {
                                        // 다이얼로그에 링크 표시
                                        val message = "오픈 카카오톡 링크:\n $kakaoUrl"
                                        AlertDialog.Builder(this@DetailActivity)
                                            .setMessage(message)
                                            .setPositiveButton("확인", null)
                                            .setCancelable(false)
                                            .show()
                                            .findViewById<TextView>(android.R.id.message)
                                            ?.setOnClickListener {
                                                // 링크 클릭 시 카카오톡 링크 열기
                                                val intent =
                                                    Intent(Intent.ACTION_VIEW, Uri.parse(kakaoUrl))
                                                startActivity(intent)
                                            }
                                    }
                                }
                                .setNegativeButton("아니오", null)
                                .show()
                        }
                    }

                } else {
                    // API 호출이 실패한 경우 처리
                    Toast.makeText(this@DetailActivity, "API 호출에 실패했습니다.", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<Room>, t: Throwable) {
                // 네트워크 오류 등 호출 실패한 경우 처리
                Toast.makeText(this@DetailActivity, "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
        binding.backButton.setOnClickListener {
            finish()
        }// 뒤로가기
    }

    fun applyForVolunteerActivity(roomTitle : String) {
        //임시토큰
        val accessToken = VolunteerKUApplication.user.getAccessToken()
        val requestBody = RequestBody.create("text/plain".toMediaTypeOrNull(), roomTitle)

        val call: Call<Void> = retrofitInterface.applyForVolunteerActivity(accessToken, requestBody)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // 봉사활동 신청이 성공적으로 처리된 경우
                    Toast.makeText(this@DetailActivity, "봉사활동에 신청되었습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    // API 호출이 실패한 경우 처리
                    Toast.makeText(this@DetailActivity, "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        Log.d("Error Response", errorBody)
                    }
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 네트워크 오류 등 호출 실패한 경우 처리
                Toast.makeText(this@DetailActivity, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
