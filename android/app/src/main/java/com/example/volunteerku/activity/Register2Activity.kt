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
import androidx.core.widget.addTextChangedListener
import com.example.volunteerku.VolunteerKUApplication.Companion.user
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
    var currentCount = 1 // 현재 모집 인원을 저장할 변수
    var dateString = "" // 날짜
    var timeString = "" // 시간
    var internetUrlText = "" // 나중에 받아올 봉사활동 주소
    private lateinit var retrofitInterface: UserRetrofitInterface
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

        binding.plus.setOnClickListener {
            if (currentCount < 10) {
                currentCount++ // 모집 인원 증가
                binding.countTextView.text = "$currentCount 명" // 텍스트뷰에 변경된 모집 인원 표시
            }
        }

        binding.minus.setOnClickListener {
            if (currentCount > 1) {
                currentCount--
                binding.countTextView.text = "$currentCount 명"
            }
        }

        binding.kakaotalk.setOnClickListener {
            onKakao()
        }

        binding.backButton.setOnClickListener {
            finish()
        }// 뒤로가기

        binding.DateBtn.setOnClickListener {
            val cal = Calendar.getInstance()    // 현재 시간 가져오기
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val currentDate = Calendar.getInstance()

                if (selectedDate.before(currentDate)) { // 선택한 날짜가 현재 날짜보다 이전인 경우
                    // 과거 날짜 선택은 불가능하도록 처리
                    AlertDialog.Builder(this@Register2Activity)
                        .setMessage("과거의 날짜는 선택할 수 없습니다.")
                        .setPositiveButton("확인") { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                } else {
                    dateString = String.format("%04d-%02d-%02dT", year, month + 1, dayOfMonth)
                    binding.Date.text = dateString
                    updateRegisterButtonState()
                }
            }
            DatePickerDialog(this, dateSetListener, cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()

        }

        binding.TimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                timeString = String.format("%02d:%02d:00", hourOfDay, minute)
                binding.Clock.text = timeString
                updateRegisterButtonState()
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()

        }

        if (!dateString.isEmpty() && !timeString.isEmpty() && !binding.kakaourl.text.toString().isEmpty()) {
            binding.RegisterBtn.isEnabled = true
        }

        binding.RegisterBtn.setOnClickListener {
                createPost()
        }

        binding.kakaourl.addTextChangedListener {
            updateRegisterButtonState()
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
        val accessToken = user.getAccessToken()

        val intent = intent
        val kakaoUrl = binding.kakaourl.text.toString()
        val internetUrl = "" // TODO
        val title = intent.getStringExtra("title").toString()
        val limitHeadCount = currentCount // 현재 모집 인원을 저장할 변수
        val closedDateTime = dateString+timeString // 임시날짜
        val content = intent.getStringExtra("content").toString()

        val room = Room(id = 0, kakaoUrl, internetUrl, title, limitHeadCount, closedDateTime, content)

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
                            val intent = Intent(this@Register2Activity, ListActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        .show()
                    //startActivity(intent)
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
    private fun updateRegisterButtonState() {
        val dateString = binding.Date.text.toString()
        val timeString = binding.Clock.text.toString()
        val kakaourlString = binding.kakaourl.text.toString()
        binding.RegisterBtn.isEnabled = dateString.isNotEmpty() && timeString.isNotEmpty() && kakaourlString.isNotEmpty()
    }

}

