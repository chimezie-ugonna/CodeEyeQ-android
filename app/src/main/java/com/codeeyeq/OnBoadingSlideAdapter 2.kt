package com.codeeyeq

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OnBoadingSlideAdapter(
    private var context: Context,
    private var illustrations: ArrayList<Int>,
    private var titles: ArrayList<String>,
    private var descriptions: ArrayList<String>
) :
    RecyclerView.Adapter<OnBoadingSlideAdapter.ViewHolder>() {
    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val img: ImageView = v.findViewById(R.id.img)
        val title: TextView = v.findViewById(R.id.title)
        val description: TextView = v.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.onboarding_slide_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.img.setImageResource(illustrations[position])
        holder.title.text = titles[position]
        holder.description.text = descriptions[position]
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}