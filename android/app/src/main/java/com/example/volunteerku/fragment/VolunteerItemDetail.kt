package com.example.volunteerku.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.data.VolunteerDetailData
import com.example.volunteerku.databinding.ActivityVolunteerItemDetailBinding
import com.example.volunteerku.service.VolunteerDataInterface
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class VolunteerItemDetail : AppCompatActivity() {
    private lateinit var binding: ActivityVolunteerItemDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        val progrmRegistNo = intent.getStringExtra("progrmRegistNo")
        println("Detail progrmRegistNo: $progrmRegistNo")
        if (progrmRegistNo != null) {
            volunteerItemDetail(progrmRegistNo)
        }

    }

    private fun volunteerItemDetail(progrmRegistNo: String) {
        val client = OkHttpClient()
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val url = "http://openapi.1365.go.kr"
        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .baseUrl(url)
            .build()
            .create(VolunteerDataInterface::class.java)

        // Replace "progrmRegistNo" with the actual value you want to pass
        retrofit.volunteerSearchDetail(progrmRegistNo)
            .enqueue(object : retrofit2.Callback<VolunteerDetailData.Detailresponse> {
                override fun onResponse(
                    call: Call<VolunteerDetailData.Detailresponse>,
                    response: Response<VolunteerDetailData.Detailresponse>
                ) {
                    println("Detail response: $response")
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            println(responseBody)
                        }
                    } else {
                        Log.e("VolunteerSearch", "Request failed: ${response.code()}")
                    }
                }

                override fun onFailure(
                    call: Call<VolunteerDetailData.Detailresponse>,
                    t: Throwable
                ) {
                    Log.e("VolunteerSearch", "Request failed: ${t.message}")
                }
            })
    }
}
