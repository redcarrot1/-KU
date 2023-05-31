package com.example.volunteerku.data

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.volunteerku.R

class RoomAdapter(context: Context, private val roomList: List<Room>) : ArrayAdapter<Room>(context, 0, roomList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.activity_list, parent, false)

        val room = roomList[position]

        val titleTextView = view.findViewById<TextView>(R.id.textView12)
        val countTextView = view.findViewById<TextView>(R.id.countTextView)

        // 제목 크게 표시
        titleTextView.text = room.title
        titleTextView.textSize = 18f

        // 모집인원 작게 표시
        countTextView.text = "모집인원: ${room.limitHeadCount}"
        countTextView.textSize = 12f

        return view
    }
}
