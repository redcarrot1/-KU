package com.example.volunteerku.fragment

import MyVolunteerListAvtivity
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.databinding.ActivityMypageModifyBinding
import com.example.volunteerku.service.BASE_URL
import com.example.volunteerku.service.UserRetrofitInterface
import com.example.volunteerku.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MypageModifyActivity : Fragment() {

    lateinit var binding: ActivityMypageModifyBinding
    private lateinit var retrofitInterface: UserRetrofitInterface

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)//baseUrl("http://34.64.106.246:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    lateinit var newName: String
    lateinit var newIntroduce: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMypageModifyBinding.inflate(inflater, container, false)
        retrofitInterface = retrofit.create(UserRetrofitInterface::class.java)
        newName = user.nickname
        newIntroduce = user.introduce
        binding.majorTextView.text = user.major

        initText()
        nickNameEventListner()
        introduceEventListner()

        binding.backButton.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyPageFragment())
                .commit()
        }

        binding.endEditButton.setOnClickListener {
            editButtonClick()
        }

        return binding.root
    }

    private fun nickNameEventListner(){
        binding.modifyUserName.doOnTextChanged { text, start, before, count ->
            newName = text.toString()
            user.nickname = newName
        }
    }
    private fun introduceEventListner(){
        binding.editIntroduce.doOnTextChanged { text, start, before, count ->
            newIntroduce = text.toString()
            user.introduce = newIntroduce
        }
    }

    private fun changeIntroduction(){
        val call: Call<Void> = retrofitInterface.changeIntroduction(user.getAccessToken(), newIntroduce)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // 자기소개 정보 변경 성공
                    Toast.makeText(requireContext(), "내정보 변경 성공", Toast.LENGTH_SHORT).show()
                } else {
                    // 자기소개 등록 실패
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        Log.d("Error Response", errorBody)
                    }
                    Toast.makeText(requireContext(), "내정보 변경 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "내정보 변경 실패: 네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun changeNickName(){
        val call: Call<Void> = retrofitInterface.changeNickname(user.getAccessToken(), newName)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // 닉네임 정보 변경 성공
                    Toast.makeText(requireContext(), "닉네임 변경 성공", Toast.LENGTH_SHORT).show()

                } else {
                    // 닉네임 등록 실패
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        Log.d("Error Response", errorBody)
                    }
                    Toast.makeText(requireContext(), "닉네임 변경 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "내정보 변경 실패: 네트워크 오류", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun editButtonClick(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("내 정보 변경")
        builder.setMessage("정말로 내 정보를 변경하시겠습니까?")
        builder.setPositiveButton("예") { dialog, which ->
            changeNickName()
            changeIntroduction()
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MypageActivity())
                .commit()
        }
        builder.setNegativeButton("아니오") { dialog, which ->
            Toast.makeText(requireContext(), "내 정보 변경 취소", Toast.LENGTH_SHORT).show()
        }
        builder.show()
    }

    private fun initText(){
        binding.modifyUserName.setText(user.nickname)
        binding.editIntroduce.setText(user.introduce)
        binding.progressBar.min = 0
        binding.progressBar.max = 1860
        binding.progressBar.progress = user.currentVolunteerTime
    }

}