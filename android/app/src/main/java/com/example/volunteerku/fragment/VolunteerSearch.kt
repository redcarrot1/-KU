import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.volunteerku.fragment.SearchAdapter
import com.example.volunteerku.data.response
import com.example.volunteerku.databinding.ActivityVolunteerSearchBinding
import com.example.volunteerku.service.VolunteerDataInterface
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException

class VolunteerSearch : Fragment() {

    lateinit var binding: ActivityVolunteerSearchBinding
    private val volunteerDataList: MutableList<response> = mutableListOf()
    private lateinit var searchAdapter: SearchAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityVolunteerSearchBinding.inflate(inflater, container, false)

        volunteerSearch()

        searchAdapter = SearchAdapter()
        binding.searchRecyclerView.adapter = searchAdapter
        return binding.root
    }

    private fun volunteerSearch() {

        val client = OkHttpClient()
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()

        val url = "http://openapi.1365.go.kr"

        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(TikXmlConverterFactory.create(parser))
            .baseUrl(url)
            .build()
            .create(VolunteerDataInterface::class.java)


        retrofit.volunteerSearch().enqueue(object: retrofit2.Callback<response> {
            override fun onResponse(call: Call<response>, response: Response<response>) {
                if (!response.isSuccessful) {
                    try {
                        throw IOException("Unexpected code $response")
                    } catch (e: Exception) {
                        println("volunteerSearch ${e.message}")
                    }
                }

                val responseBody = response.body()
                println("responseBody $responseBody")
                responseBody?.let{
                    activity?.runOnUiThread {
                        searchAdapter.submitList(it.body?.items?.item ?: emptyList())
                    }
                }

            }

            override fun onFailure(call: Call<response>, t: Throwable) {
                Log.i("VolunteerSearch", "VolunteerSearch ${t.message}")
            }

        })
    }



}


