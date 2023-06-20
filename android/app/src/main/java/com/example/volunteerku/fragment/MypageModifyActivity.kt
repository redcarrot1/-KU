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
        initSpinner()
        initText()
        nickNameEventListner()
        introduceEventListner()

        binding.endEditButton.setOnClickListener {
            editButtonClick()
        }

        return binding.root
    }

    private fun nickNameEventListner(){
        binding.modifyUserName.doOnTextChanged { text, start, before, count ->
            newName = text.toString()
        }
    }
    private fun introduceEventListner(){
        binding.editIntroduce.doOnTextChanged { text, start, before, count ->
            newIntroduce = text.toString()
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
    private fun initSpinner(){
        // Spinner 어댑터 설정
        binding.departmentSpinner.adapter =
            ArrayAdapter.createFromResource(requireContext(), R.array.magjor_array, R.layout.spinner_text)

        binding.addVolunteerTime2.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyVolunteerAddActivity())
                .commit()
        }

        binding.progressBar.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyVolunteerListAvtivity())
                .commit()
        }

        binding.departmentSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    when (position) {
                        0 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.major,
                                R.layout.spinner_text
                            )
                        }

                        1 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_liberal,
                                R.layout.spinner_text
                            )
                        }

                        2 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_sciences,
                                R.layout.spinner_text
                            )
                        }

                        3 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_architrcture,
                                R.layout.spinner_text
                            )
                        }

                        4 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_engineering,
                                R.layout.spinner_text
                            )
                        }

                        5 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_socialsciences,
                                R.layout.spinner_text
                            )
                        }

                        6 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_business,
                                R.layout.spinner_text
                            )
                        }

                        7 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_realestate,
                                R.layout.spinner_text
                            )
                        }

                        8 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_kit,
                                R.layout.spinner_text
                            )
                        }

                        9 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_life,
                                R.layout.spinner_text
                            )
                        }

                        10 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_veterinary,
                                R.layout.spinner_text
                            )
                        }

                        11 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_socialsciences,
                                R.layout.spinner_text
                            )
                        }

                        12 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_design,
                                R.layout.spinner_text
                            )
                        }

                        13 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_education,
                                R.layout.spinner_text
                            )
                        }

                        14 -> {
                            binding.majorDbSpinner.adapter = ArrayAdapter.createFromResource(
                                requireContext(),
                                R.array.spinner_sanghuh,
                                R.layout.spinner_text
                            )
                        }
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(requireContext(), "대학과 학과를 선택해 주세요", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }

}