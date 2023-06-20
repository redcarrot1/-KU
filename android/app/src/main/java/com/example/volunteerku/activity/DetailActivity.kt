package com.example.volunteerku.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.data.Room
import com.example.volunteerku.databinding.ActivityDetailBinding
import com.example.volunteerku.service.BASE_URL
import com.example.volunteerku.service.UserRetrofitInterface
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONException
import org.json.JSONObject
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
                        binding.LimitheadCountView.text =
                            "${room.currentHeadCount}/${room.limitHeadCount}"
                        binding.contentsView.setText(room.content)
                        if (room.internetUrl.equals("https://www.naver.com")) {
                            binding.internetURL.setText("1365에 존재하지 않는 봉사활동입니다.")
                        } else {
                            binding.internetURL.setText(room.internetUrl)
                        }
                        binding.regButton.setOnClickListener {
                            // 다이얼로그를 띄워서 사용자에게 확인을 받기
                            AlertDialog.Builder(this@DetailActivity)
                                .setTitle("봉사활동 신청")
                                .setMessage("봉사활동에 신청하시겠습니까?")
                                .setPositiveButton("예") { dialog, which ->
                                    // 예를 선택한 경우 처리
                                    applyForVolunteerActivity(roomId, room.kakaoUrl)
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

    fun applyForVolunteerActivity(roomId: Int, kakaoUrl: String?) {
        // 임시토큰
        // val accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3NUb2tlbiIsImF1ZCI6IjEiLCJpc3MiOiJ2b2x1bnRlZXJLVSIsImlhdCI6MTY4NTU0NzI5OH0.19rUh99CYKl8ZtKamntInimMiM5AwGlzXKxpvHadxIQ"
        val accessToken = user.getAccessToken()
        val requestBody = "{\"id\": $roomId}".toRequestBody("application/json".toMediaTypeOrNull())

        val call: Call<Void> = retrofitInterface.applyForVolunteerActivity(accessToken, requestBody)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // 봉사활동 신청이 성공적으로 처리된 경우
                    Toast.makeText(
                        this@DetailActivity,
                        "봉사활동에 신청되었습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("Kakao URL", "Kakao URL: $kakaoUrl")
                    if (kakaoUrl != null) {
                        // 다이얼로그에 링크 표시 kakaoUrl"
                        val message = "오픈 카카오톡 링크:\n $kakaoUrl"
                        val dialog = AlertDialog.Builder(this@DetailActivity)
                            .setMessage(message)
                            .setPositiveButton("확인", null)
                            .setCancelable(false)
                            .show()
                        dialog.findViewById<TextView>(android.R.id.message)?.setOnClickListener {
                            // 링크 클릭 시 카카오톡 링크 열기
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(kakaoUrl))
                            startActivity(intent)
                            dialog.dismiss() // 다이얼로그 닫기
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        try {
                            val errorJson = JSONObject(errorBody)
                            val errorCode = errorJson.getString("code")
                            val errorMessage = errorJson.getString("message")

                            if (errorCode == "RO03") {
                                // 이미 해당 방에 참여 중인 경우
                                runOnUiThread {
                                    AlertDialog.Builder(this@DetailActivity)
                                        .setMessage(errorMessage)
                                        .setPositiveButton("확인", null)
                                        .show()
                                }
                                return
                            }
                            if (errorCode == "RO02") {
                                // 방이 꽉 찼을 경우
                                runOnUiThread {
                                    AlertDialog.Builder(this@DetailActivity)
                                        .setMessage(errorMessage)
                                        .setPositiveButton("확인", null)
                                        .show()
                                }
                                return
                            }

                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                    // API 호출이 실패한 경우 처리
                    Toast.makeText(
                        this@DetailActivity,
                        "API 호출에 실패했습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("Error Response", errorBody ?: "")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 네트워크 오류 등 호출 실패한 경우 처리
                Toast.makeText(this@DetailActivity, "네트워크 연결에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }


}