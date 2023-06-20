package com.example.volunteerku.fragment


import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.volunteerku.data.item
import com.example.volunteerku.databinding.ItemSearchlistBinding

class SearchAdapter(): ListAdapter<item, SearchAdapter.ViewHolder>(diffUtil) {

    inner class ViewHolder(private val binding:ItemSearchlistBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener{
                val intent = Intent(binding.root.context, VolunteerItemDetail::class.java)
                intent.putExtra("progrmRegistNo",currentList[adapterPosition].progrmRegistNo)
                intent.putExtra("url",currentList[adapterPosition].url)
                binding.root.context.startActivity(intent)
            }
        }
        fun bind(data:item){
            binding.run{
                volunteerName.text = data.progrmSj
                volunteerDate.text = data.progrmBgnde + " ~ " + data.progrmEndde
                volunteerLocation.text= data.nanmmbyNm
                volunteerTime.text = data.actBeginTm+"시 ~ " + data.actEndTm+"시"
            }
        }
    }

    companion object{
        val diffUtil = object:DiffUtil.ItemCallback<item>(){
            override fun areItemsTheSame(oldItem: item, newItem: item): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: item, newItem: item): Boolean {
                return oldItem==newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemSearchlistBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }


}