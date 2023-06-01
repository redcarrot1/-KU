package com.example.volunteerku.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatDialog
import com.bumptech.glide.Glide
import com.example.volunteerku.R


class NotMemberDialog(context: Context) : Dialog(context) {

    private lateinit var progressDialog: AppCompatDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_not_member)
        setCancelable(false)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

//        val gifImageView = findViewById<ImageView>(R.id.loading_progress_bar)
//        Glide.with(context).asGif().load(R.raw.loading).into(gifImageView)


    }
}





