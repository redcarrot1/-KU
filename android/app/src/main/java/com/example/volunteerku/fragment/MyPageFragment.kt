package com.example.volunteerku.fragment

import MyVolunteerListAvtivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.data.UserDataResponse
import com.example.volunteerku.databinding.FragmentMyPageBinding
import com.example.volunteerku.service.BASE_URL
import com.example.volunteerku.service.UserRetrofitInterface
import com.example.volunteerku.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageFragment() : Fragment() {
    lateinit var binding: FragmentMyPageBinding
    private lateinit var retrofitInterface: UserRetrofitInterface

    var nickname: String = user.nickname
    var major: String = user.major
    var introduce: String = user.introduce

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)//baseUrl("http://34.64.106.246:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        retrofitInterface = retrofit.create(UserRetrofitInterface::class.java)
        val userService = UserService()
        userService.signIn(user.email, user.password)
        getUserData()

        buttonInit()

        println("JWT: ${user.jwt}")
        return binding.root
    }

    private fun getUserData(){
        val call: Call<UserDataResponse> = retrofitInterface.getUserData(user.getAccessToken())
        call.enqueue(object : Callback<UserDataResponse> {
            override fun onResponse(
                call: Call<UserDataResponse>,
                response: Response<UserDataResponse>
            ) {
                Toast.makeText(context, "성공", Toast.LENGTH_SHORT).show()
                introduce = response.body()?.introduction.toString()
                major = response.body()?.major.toString()
                nickname = response.body()?.nickname.toString()

                textViewInit()

            }
            override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                println("실패: $t")
            }
        })
    }
    private fun textViewInit() {
        binding.userName.text = nickname
        binding.departmentInfo.text = major
        binding.multiAutoCompleteTextView.text = introduce

        binding.progressBar.min = 0
        binding.progressBar.max = 1860
        binding.progressBar.progress = user.currentVolunteerTime
    }

    private fun buttonInit() {
        binding.addVolunteerTime1.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyVolunteerAddActivity())
                .commit()
        }

        binding.passwordChangeButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MypasswordChangeActivity())
                .commit()
        }

        binding.informationChangeButton.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MypageModifyActivity())
                .commit()
        }
        binding.progressBar.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyVolunteerListAvtivity())
                .commit()
        }
    }
}
