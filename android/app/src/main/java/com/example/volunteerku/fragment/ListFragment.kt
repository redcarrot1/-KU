package com.example.volunteerku.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.activity.DetailActivity
import com.example.volunteerku.data.Applications
import com.example.volunteerku.data.Room
import com.example.volunteerku.databinding.FragmentListBinding
import com.example.volunteerku.service.BASE_URL
import com.example.volunteerku.service.UserRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var retrofitInterface: UserRetrofitInterface
    private var roomList: List<Room>? = null

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
                recruitmentTextView.text = "모집인원: ${room.currentHeadCount}/${room.limitHeadCount}"
            }

            return itemView
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        val view = binding.root

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
                        val adapter = RoomListAdapter(
                            requireContext(),
                            R.layout.custom_list_item,
                            roomList.reversed()
                        )
                        binding.listView.adapter = adapter
                    }
                    applicationView()
                    binding.listView.setOnItemClickListener { parent, view, position, id ->
                        val selectedRoom = roomList?.reversed()?.get(position)
                        val roomId = selectedRoom?.id

                        // DetailActivity로 id 값을 전달
                        val intent = Intent(requireContext(), DetailActivity::class.java)
                        intent.putExtra("roomId", roomId)
                        startActivity(intent)
                    }
                } else {
                    // API 호출이 실패한 경우 처리
                    Toast.makeText(requireContext(), "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                // 네트워크 오류 등 호출 실패한 경우 처리
                Toast.makeText(requireContext(), "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
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

        return view
    }

    override fun onResume() {
        super.onResume()
        applicationView()
        getRoomList()
    }

    //사용자가 신청한 봉사활동 조회
    fun applicationView() {
        val accessToken = user.getAccessToken()
        val call: Call<List<Applications>> = retrofitInterface.getApplicationRooms(accessToken)

        call.enqueue(object : Callback<List<Applications>> {
            override fun onResponse(
                call: Call<List<Applications>>,
                response: Response<List<Applications>>
            ) {
                if (response.isSuccessful) {
                    val applications = response.body()
                    if (applications != null) {
                        val roomTitleList: ArrayList<String> = ArrayList()

                        for (application in applications) {
                            roomTitleList.add(application.title)
                        }

                        if (roomTitleList.isEmpty()) {
                            roomTitleList.add("사용자가 신청한 봉사활동이 없습니다.")
                        }

                        val titleAdapter = ArrayAdapter(
                            requireContext(),
                            R.layout.custom_list_title,
                            R.id.item_title,
                            roomTitleList
                        )
                        binding.listView2.adapter = titleAdapter

                        binding.listView2.setOnItemClickListener { parent, view, position, id ->
                            val selectedRoom = applications.get(position)
                            val roomId = selectedRoom?.id

                            // DetailActivity로 id 값을 전달
                            val intent = Intent(requireContext(), DetailActivity::class.java)
                            intent.putExtra("roomId", roomId)
                            startActivity(intent)
                        }


                    }
                } else {
                    // API 호출이 실패한 경우 처리
                    Toast.makeText(
                        requireContext(),
                        "API 호출에 실패했습니다.",
                        Toast.LENGTH_SHORT
                    ).show()
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        Log.d("Error Response", errorBody)
                    }
                }
            }

            override fun onFailure(call: Call<List<Applications>>, t: Throwable) {
                // 네트워크 오류 등 호출 실패한 경우 처리
                Toast.makeText(
                    requireContext(),
                    "네트워크 오류가 발생했습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    //onResume() 사용하여 게시글 업데이트ㅂ
    private fun getRoomList() {
        val call: Call<List<Room>> = retrofitInterface.getRooms()

        call.enqueue(object : Callback<List<Room>> {
            override fun onResponse(call: Call<List<Room>>, response: Response<List<Room>>) {
                if (response.isSuccessful) {
                    roomList = response.body()
                    if (roomList != null) {
                        // 받아온 게시글 목록을 리스트뷰에 표시
                        val adapter = RoomListAdapter(
                            requireContext(),
                            R.layout.custom_list_item,
                            roomList!!.reversed()
                        )
                        binding.listView.adapter = adapter
                    }
                } else {
                    // API 호출이 실패한 경우 처리
                    Toast.makeText(requireContext(), "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Room>>, t: Throwable) {
                // 네트워크 오류 등 호출 실패한 경우 처리
                Toast.makeText(requireContext(), "API 호출에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
