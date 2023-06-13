package com.example.volunteerku.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.databinding.ActivityMypageModifyBinding

class MypageModifyActivity : Fragment() {

    lateinit var binding: ActivityMypageModifyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMypageModifyBinding.inflate(inflater, container, false)

        initSpinner()
        initText()
        eventListener()
        return binding.root

    }

    private fun eventListener(){

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
        /*binding.endEditButton.setOnClickListener(

        )*/
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