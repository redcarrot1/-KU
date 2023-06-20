package com.example.volunteerku.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.activity.RegisterActivity
import com.example.volunteerku.data.Detailresponse
import com.example.volunteerku.databinding.ActivityVolunteerItemDetailBinding
import com.example.volunteerku.service.VolunteerDataDetailInterface
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class VolunteerItemDetail : AppCompatActivity() {
    private lateinit var binding: ActivityVolunteerItemDetailBinding
    lateinit var progrmRegistNo: String
    lateinit var itemUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityVolunteerItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = intent
        progrmRegistNo = intent.getStringExtra("progrmRegistNo").toString()
        itemUrl = intent.getStringExtra("url").toString().replace("amp;", "")
        if (progrmRegistNo.isNotEmpty()) {
            println("Detail progrmRegistNo: $progrmRegistNo")
            volunteerItemDetail(progrmRegistNo) // 변경된 매개변수 전달
        }
        binding.partyPlayBtn.setOnClickListener {//단체신청버튼
            val intent = Intent(this@VolunteerItemDetail, RegisterActivity::class.java)
            intent.putExtra("itemUrl", itemUrl)
            println("detail : $itemUrl")
            startActivity(intent)
        }
    }

    private fun volunteerItemDetail(progrmRegistNo: String?) {
        println("volunteerItemDetail: $progrmRegistNo")

        val client = OkHttpClient()
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val url = "http://openapi.1365.go.kr"
        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .baseUrl(url)
            .build()
            .create(VolunteerDataDetailInterface::class.java)

        if (progrmRegistNo != null) {
            retrofit.volunteerSearchDetail(progrmRegistNo) // 변경된 매개변수 전달
                .enqueue(object : Callback<Detailresponse> {
                    override fun onResponse(
                        call: Call<Detailresponse>,
                        response: Response<Detailresponse>
                    ) {
                        println("Response: ${response.body()?.body?.items?.item?.get(0)?.progrmCn}")
                        binding.textViewName.text =
                            response.body()?.body?.items?.item?.get(0)?.progrmSj
                        binding.textViewDate.text =
                            response.body()?.body?.items?.item?.get(0)?.progrmBgnde + " ~ " + response.body()?.body?.items?.item?.get(
                                0
                            )?.progrmEndde
                        binding.textViewDetailInfo.text =
                            response.body()?.body?.items?.item?.get(0)?.progrmCn
                        binding.textViewTime.text =
                            response.body()?.body?.items?.item?.get(0)?.actBeginTm + " ~ " + response.body()?.body?.items?.item?.get(
                                0
                            )?.actEndTm
                        binding.textViewLocation.text =
                            response.body()?.body?.items?.item?.get(0)?.postAdres
                        binding.soloPlayBtn.setOnClickListener {
                            println("soloPlayBtn clicked")
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(itemUrl))
                            startActivity(intent)
                        }
                    }

                    override fun onFailure(
                        call: Call<Detailresponse>,
                        t: Throwable
                    ) {
                        error("volunteerItemDetail ${t.message}")
                    }

                })
        }
    }
}
