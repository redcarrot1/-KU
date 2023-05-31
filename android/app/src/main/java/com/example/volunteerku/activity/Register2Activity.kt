package com.example.volunteerku.activity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.data.Room
import com.example.volunteerku.databinding.ActivityRegister2Binding
import com.example.volunteerku.service.BASE_URL
import com.example.volunteerku.service.UserRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Calendar


class Register2Activity : AppCompatActivity() {
    lateinit var binding: ActivityRegister2Binding
    var currentCount = 0 // 현재 모집 인원을 저장할 변수
    var dateString = "" // 날짜
    var timeString = "" // 시간
    var internetUrlText = "" // 나중에 받아올 봉사활동 주소
    private lateinit var retrofitInterface : UserRetrofitInterface
    lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)//baseUrl("http://34.64.106.246:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitInterface = retrofit.create(UserRetrofitInterface::class.java)

        binding.plus.setOnClickListener{
            if (currentCount < 10) {
                currentCount++ // 모집 인원 증가
                binding.countTextView.text = "$currentCount 명" // 텍스트뷰에 변경된 모집 인원 표시
            }
        }

        binding.minus.setOnClickListener{
            if (currentCount > 0) {
                currentCount--
                binding.countTextView.text = "$currentCount 명"
            }
        }

        binding.kakaotalk.setOnClickListener{
            onKakao()
        }

        binding.backButton.setOnClickListener {
            finish()
        }// 뒤로가기

        binding.DateBtn.setOnClickListener {
            val cal = Calendar.getInstance()    //캘린더뷰 만들기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateString = "${year}년 ${month+1}월 ${dayOfMonth}일"
                binding.Date.text = dateString
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        binding.TimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                timeString = "${hourOfDay}시 ${minute}분"
                binding.Clock.text = timeString
            }
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),true).show()
        }

        binding.RegisterBtn.setOnClickListener{
         //   val intent = Intent(this, ListActivity::class.java)
            createPost()
           // startActivity(intent)
        }


    }

    fun onKakao() {
        if (getPackageList()) {
            val intent = packageManager.getLaunchIntentForPackage("com.kakao.talk")
            intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent) // Manifest에 query 안에 패키지명 추가해야 동작함
        } else {
            val url = "market://details?id=" + "com.kakao.talk"
            val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(i)
        }
    }

    fun getPackageList(): Boolean {
        var isExist = false
        val pkgMgr = packageManager
        val mApps: List<ResolveInfo>
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        mApps = pkgMgr.queryIntentActivities(mainIntent, 0)
        try {
            for (i in mApps.indices) {
                if (mApps[i].activityInfo.packageName.startsWith("com.kakao.talk")) {
                    isExist = true
                    break
                }
            }
        } catch (e: Exception) {
            isExist = false
        }
        return isExist
    } //해당 디바이스에 카카오톡 앱이 설치되었는지를 확인

    private fun createPost() {
        progressDialog = ProgressDialog(this@Register2Activity)
        progressDialog.setMessage("로딩 중...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        // 액세스 토큰 값
        val accessToken = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhY2Nlc3NUb2tlbiIsImF1ZCI6IjEiLCJpc3MiOiJ2b2x1bnRlZXJLVSIsImlhdCI6MTY4NTU0NzI5OH0.19rUh99CYKl8ZtKamntInimMiM5AwGlzXKxpvHadxIQ"
        //임시토큰

        val intent = Intent(this@Register2Activity, ListActivity::class.java)
        val kakaoUrl = binding.KakaoUrl.text.toString()
        val internetUrl = "" // 인터넷 URL 아직 없음
        val title = intent.getStringExtra("title").toString()
        val limitHeadCount = currentCount // 현재 모집 인원을 저장할 변수
        val closedDateTime = "2023-07-01T18:00:00" // 임시날짜
        val content = intent.getStringExtra("content").toString()

        val room = Room(kakaoUrl, internetUrl, title, limitHeadCount, closedDateTime, content)

        val call: Call<Void> = retrofitInterface.createPost(accessToken, room)

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                progressDialog.dismiss()
                if (response.isSuccessful) {
                    // 게시글 등록 성공
                    AlertDialog.Builder(this@Register2Activity)
                        .setMessage("게시글이 성공적으로 등록되었습니다.")
                        .setPositiveButton("확인") { dialog, _ ->
                            dialog.dismiss()
                            finish()
                        }
                        .show()
                    startActivity(intent)
                } else {
                    // 게시글 등록 실패
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        Log.d("Error Response", errorBody)
                    }
                    AlertDialog.Builder(this@Register2Activity)
                        .setMessage("게시글 등록에 실패했습니다.")
                        .setPositiveButton("확인") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // 통신 오류 처리
                progressDialog.dismiss()
                AlertDialog.Builder(this@Register2Activity)
                    .setMessage("네트워크 오류가 발생했습니다.")
                    .setPositiveButton("확인") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        })
    }


}

