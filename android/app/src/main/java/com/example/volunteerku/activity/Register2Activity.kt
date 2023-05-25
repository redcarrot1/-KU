package com.example.volunteerku.activity

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.databinding.ActivityRegister2Binding


class Register2Activity : AppCompatActivity() {
    lateinit var binding: ActivityRegister2Binding
    var currentCount = 0 // 현재 모집 인원을 저장할 변수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.plus.setOnClickListener{
            if (currentCount < 10) {
                currentCount++ // 모집 인원 증가
                binding.countTextView.text = "$currentCount 명" // 텍스트뷰에 변경된 모집 인원 표시
            }
        }

        binding.minus.setOnClickListener{
            if (currentCount > 0) {
                currentCount-- // 모집 인원 감소
                binding.countTextView.text = "$currentCount 명" // 텍스트뷰에 변경된 모집 인원 표시
            }
        }

        binding.kakaotalk.setOnClickListener{
            onKakao()
        }

        binding.backButton.setOnClickListener {
            finish()
        }//뒤로가기
    }

    fun onKakao() {
        if (getPackageList()) {
            val intent = packageManager.getLaunchIntentForPackage("com.kakao.talk")
            intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent) //Manifest에 query 안에 패키지명 추가해야 동작함
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

}
