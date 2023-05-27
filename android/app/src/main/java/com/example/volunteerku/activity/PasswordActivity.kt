package com.example.volunteerku.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.databinding.ActivityPasswordBinding

class PasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.passwordEt.addTextChangedListener(
            object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    binding.passwordEndBtn.isEnabled = false
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    Log.d("meg", "Text Change!!")
                    val isSatisCond1 = s.toString().length in 6..15
                    val isSatisCond2 = Regex("^(?=.*[A-Za-z])(?=.*\\d).+\$").matches(s.toString())

                    if (isSatisCond1) {
                        binding.condition1.setImageResource(R.drawable.ok_img)
                    } else {
                        binding.condition1.setImageResource(R.drawable.error_img)
                    }
                    if (isSatisCond2) {
                        binding.condition2.setImageResource(R.drawable.ok_img)
                    } else {
                        binding.condition2.setImageResource(R.drawable.error_img)
                    }
                    binding.passwordEndBtn.isEnabled = isSatisCond1 && isSatisCond2

                }
            }
        )

        binding.passwordEndBtn.setOnClickListener {
            savepassword()
        }
    }


    private fun savepassword() {
        user.password = binding.passwordEt.text.toString()
        val intent = Intent(this, IntroductionActivity::class.java)
        startActivity(intent)
    }
}
