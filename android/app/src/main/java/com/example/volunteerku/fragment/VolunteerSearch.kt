import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.volunteerku.data.VolunteerData
import com.example.volunteerku.databinding.ActivityVolunteerSearchBinding
import okhttp3.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.io.StringReader

class VolunteerSearch : Fragment() {

    lateinit var binding: ActivityVolunteerSearchBinding
    private val volunteerDataList: MutableList<VolunteerData> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityVolunteerSearchBinding.inflate(inflater, container, false)

        volunteerSearch()

        return binding.root
    }

    private fun volunteerSearch() {
        val client = OkHttpClient()

        val url = "http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrSearchWordList"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle request failure
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val responseData = response.body?.string()
                    activity?.runOnUiThread {
                        binding.textView11.text = responseData

                    }
                }
            }
        })
    }



}
