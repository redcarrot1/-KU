package com.example.volunteerku.fragment

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.data.MyVolunteerInfoRequest
import com.example.volunteerku.databinding.ActivityMyVolunteerAddBinding
import com.example.volunteerku.service.BASE_URL
import com.example.volunteerku.service.UserRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MyVolunteerAddActivity : Fragment() {

    lateinit var binding: ActivityMyVolunteerAddBinding
    var dateString = "" // 날짜
    var timeString = "" // 시간
  //  var minuteTime: Int = 0
    var title: String? = null
   // var date:String? = null

    private lateinit var retrofitInterface: UserRetrofitInterface

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)//baseUrl("http://34.64.106.246:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMyVolunteerAddBinding.inflate(inflater, container, false)
        retrofitInterface = retrofit.create(UserRetrofitInterface::class.java)
        init()
        binding.backButton.setOnClickListener{
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyPageFragment())
                .commit()
        }
        return binding.root
    }

    private fun init() {
        binding.userName.text = user.nickname
        binding.volunteerTitle.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    title = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    binding.volunteerRegistBtn.isEnabled = false
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    title = s.toString()
                    binding.volunteerRegistBtn.isEnabled = s.toString().length in 6..15
                }
            }
        )
        binding.DateBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, month)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                dateString = sdf.format(selectedDate.time)
                binding.Date.text = dateString
                updateRegisterButtonState()
            }
            DatePickerDialog(requireContext(), dateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                Calendar.DAY_OF_MONTH)).show()
        }

        binding.TimeBtn.setOnClickListener {
            val cal = Calendar.getInstance()
            cal.set(Calendar.HOUR_OF_DAY, 0)
            cal.set(Calendar.MINUTE, 0)

            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val totalMinutes = hourOfDay * 60 + minute
                timeString = totalMinutes.toString()
                binding.Clock.text = "$timeString 분"
                updateRegisterButtonState()
            }

            val timePickerDialog = TimePickerDialog(
                requireContext(),
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            )

            // Set the initial time to 00:00
            timePickerDialog.updateTime(0, 0)

            timePickerDialog.show()
        }



//        binding.volunteerDateEt.addTextChangedListener(
//            object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                    date = s.toString()
//                }
//
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                    binding.volunteerRegistBtn.isEnabled = false
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    date = s.toString()
//                    binding.volunteerRegistBtn.isEnabled = s.toString().length in 6..15
//                }
//            }
//        )
//
//        binding.volunteerTimeEt.addTextChangedListener(
//            object : TextWatcher {
//                override fun afterTextChanged(s: Editable?) {
//                    minuteTime = s.toString().toInt()
//                }
//
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                    binding.volunteerRegistBtn.isEnabled = false
//                }
//
//                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                    minuteTime = s.toString().toInt()
//                    binding.volunteerRegistBtn.isEnabled = s.toString().length in 1..4
//                }
//            }
//        )

        binding.volunteerRegistBtn.setOnClickListener{
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("내 봉사 기록 등록")
            builder.setMessage("봉사기록을 등록하시겠습니까?")
            builder.setPositiveButton("예") { dialog, which ->
                addVolunteerInfo()
                parentFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragmentContainer, MypageActivity())
                    .commit()
            }
            builder.setNegativeButton("아니오") { dialog, which ->
                Toast.makeText(requireContext(), "봉사기록 등록 취소", Toast.LENGTH_SHORT).show()
            }
            builder.show()


        }

    }

    private fun updateRegisterButtonState() {
        val dateString = binding.Date.text.toString()
        val timeString = binding.Clock.text.toString()
        val kakaourlString = binding.volunteerTitle.text.toString()
        binding.volunteerRegistBtn.isEnabled = dateString.isNotEmpty() && timeString.isNotEmpty() && kakaourlString.isNotEmpty()
    }

    private fun addVolunteerInfo(){
        var request = MyVolunteerInfoRequest(timeString.toInt(), title.toString(), dateString)
        val call: Call<Void> = retrofitInterface.volunteerRegist(user.getAccessToken(),request)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "봉사정보 추가 성공", Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    if (errorBody != null) {
                        Log.d("Error Response", errorBody)
                    }
                    Toast.makeText(requireContext(), "봉사정보 추가 실패", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "봉사정보 추가 실패: 네트워크 오류", Toast.LENGTH_SHORT).show()
            }

        })
    }



}