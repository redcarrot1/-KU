import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.volunteerku.R
import com.example.volunteerku.VolunteerKUApplication.Companion.user
import com.example.volunteerku.activity.DetailActivity
import com.example.volunteerku.data.MyVolunteerInfoRequest
import com.example.volunteerku.data.Room
import com.example.volunteerku.databinding.ActivityMyVolunteerListAvtivityBinding
import com.example.volunteerku.fragment.MyVolunteerAddActivity
import com.example.volunteerku.service.BASE_URL
import com.example.volunteerku.service.UserRetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyVolunteerListAvtivity : Fragment() {
    private lateinit var binding: ActivityMyVolunteerListAvtivityBinding
    private val volunteerDataList: MutableList<MyVolunteerInfoRequest> = mutableListOf()
    private lateinit var myVolunteerAdapter: MyVolunteerAdapter
    private lateinit var retrofitInterface: UserRetrofitInterface

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMyVolunteerListAvtivityBinding.inflate(inflater, container, false)

        binding.userName.text = user.nickname
        binding.progressBar.min = 0
        binding.progressBar.max = 1860
        binding.progressBar.progress = user.currentVolunteerTime

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofitInterface = retrofit.create(UserRetrofitInterface::class.java)
        getVolunteerList()
        onViewCreated(binding.root, savedInstanceState)

        binding.addVolunteerTime1.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainer, MyVolunteerAddActivity())
                .commit()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myVolunteerAdapter = MyVolunteerAdapter(volunteerDataList)
        binding.myVolunteerList.adapter = myVolunteerAdapter

        getVolunteerList()
    }

    private fun getVolunteerList() {
        val call: Call<List<MyVolunteerInfoRequest>> = retrofitInterface.getMyVolunteerInfo(user.getAccessToken())
        call.enqueue(object : Callback<List<MyVolunteerInfoRequest>> {
            override fun onResponse(
                call: Call<List<MyVolunteerInfoRequest>>,
                response: Response<List<MyVolunteerInfoRequest>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()

                    if (body != null) {
                        for (i in body) {
                            volunteerDataList.add(i)
                        }
                        myVolunteerAdapter.notifyDataSetChanged()

                        Toast.makeText(requireContext(), "Response Successful", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<MyVolunteerInfoRequest>>, t: Throwable) {
                // Handle failure
                Toast.makeText(requireContext(), "Response Failure", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
