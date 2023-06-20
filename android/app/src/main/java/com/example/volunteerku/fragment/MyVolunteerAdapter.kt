import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteerku.data.MyVolunteerInfoRequest
import com.example.volunteerku.databinding.ItemMyvolunteerBinding

class MyVolunteerAdapter(private val volunteerDataList: List<MyVolunteerInfoRequest>) :
        RecyclerView.Adapter<MyVolunteerAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                val binding = ItemMyvolunteerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                )
                return ViewHolder(binding)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val volunteerData = volunteerDataList[position]
                holder.bind(volunteerData)
        }

        override fun getItemCount(): Int {
                return volunteerDataList.size
        }

        inner class ViewHolder(private val binding: ItemMyvolunteerBinding) :
                RecyclerView.ViewHolder(binding.root) {

                fun bind(volunteerData: MyVolunteerInfoRequest) {
                        // Bind the data to the views in the item layout
                        binding.volunteerName.text = volunteerData.volunteerTitle
                        binding.volunteerTime.text = volunteerData.volunteerTime.toString() + "분"
                        binding.volunteerDate.text = volunteerData.volunteerDate
                }
        }
}
