package com.example.volunteerku.activity

import android.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.volunteerku.data.Room
import com.example.volunteerku.databinding.ActivityListBinding
import com.example.volunteerku.service.BASE_URL
import com.example.volunteerku.service.UserRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListActivity : AppCompatActivity() {
    lateinit var binding : ActivityListBinding
    private lateinit var retrofitInterface: UserRetrofitInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInterface = retrofit.create(UserRetrofitInterface::class.java)

        val call: Call<List<Room>> = retrofitInterface.getRooms()

        call.enqueue(object : Callback<List<Room>> {
            override fun onResponse(call: Call<List<Room>>, response: Response<List<Room>>) {
                if (response.isSuccessful) {
                    val roomList = response.body()
                    if (roomList != null) {
                        // 받아온 게시글 목록을 리스트뷰에 표시
                        val adapter = ArrayAdapter(this@ListActivity, R.layout.simple_list_item_1, roomList)
                        binding.listView.adapter = adapter
                    }
                } else {
                    // API 호출이 실패한 경우 처리
                    Toast.makeText(this@ListActivity, "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                // 네트워크 오류 등 호출 실패한 경우 처리
                Toast.makeText(this@ListActivity, "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })

        binding.ToggleBtnDown.setOnClickListener{
            binding.ToggleBtnDown.visibility = View.GONE
            binding.ToggleBtnUp.visibility = View.VISIBLE
        }

        binding.ToggleBtnUp.setOnClickListener{
            binding.ToggleBtnUp.visibility = View.GONE
            binding.ToggleBtnDown.visibility = View.VISIBLE
        }
    }
}