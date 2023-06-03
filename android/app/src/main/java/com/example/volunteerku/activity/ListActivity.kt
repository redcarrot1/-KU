package com.example.volunteerku.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.volunteerku.R
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
    lateinit var binding: ActivityListBinding
    private lateinit var retrofitInterface: UserRetrofitInterface

    private inner class RoomListAdapter(
        context: Context,
        resource: Int,
        rooms: List<Room>
    ) : ArrayAdapter<Room>(context, resource, rooms) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val itemView: View =
                convertView ?: LayoutInflater.from(context).inflate(
                    R.layout.custom_list_item,
                    parent,
                    false
                )

            val room: Room? = getItem(position)

            // UI 요소를 findViewById로 찾아와서 처리
            val titleTextView: TextView = itemView.findViewById(R.id.item_text)
            val recruitmentTextView: TextView = itemView.findViewById(R.id.item_subtitle)

            if (room != null) {
                titleTextView.text = room.title
            }
            if (room != null) {
                recruitmentTextView.text = "모집인원: ${room.limitHeadCount}"
            }

            return itemView
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val roomTitleList: ArrayList<String> = ArrayList() // 추가한 봉사활동을 추가하기 위한 list

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
                        val adapter = RoomListAdapter(this@ListActivity, R.layout.custom_list_item, roomList.reversed())
                        binding.listView.adapter = adapter

                        // roomTitleList에 제목 추가
                        for (room in roomList) {
                            roomTitleList.add(room.title)
                        }

                        // listView2에 제목들을 표시
                        val titleAdapter = ArrayAdapter(this@ListActivity, R.layout.custom_list_title, R.id.item_title, roomTitleList)
                        binding.listView2.adapter = titleAdapter

                        binding.listView.setOnItemClickListener { parent, view, position, id ->
                            val selectedRoom = roomList.reversed()[position]
                            val roomId = selectedRoom.id

                            // DetailActivity로 id 값을 전달
                            val intent = Intent(this@ListActivity, DetailActivity::class.java)
                            intent.putExtra("roomId", roomId)
                            startActivity(intent)
                        }

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

        binding.ToggleBtnDown.setOnClickListener {
            binding.ToggleBtnDown.visibility = View.GONE
            binding.ToggleBtnUp.visibility = View.VISIBLE
            binding.listView2.visibility = View.VISIBLE
        }

        binding.ToggleBtnUp.setOnClickListener {
            binding.ToggleBtnUp.visibility = View.GONE
            binding.ToggleBtnDown.visibility = View.VISIBLE
            binding.listView2.visibility = View.GONE
        }


    }
}
