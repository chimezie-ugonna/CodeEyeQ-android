package com.codeeyeq.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codeeyeq.R
import com.codeeyeq.controller.activities.Home
import com.codeeyeq.data.MoreListChild

class MoreListChildAdapter(val context: Context, val data: ArrayList<MoreListChild>) :
    RecyclerView.Adapter<MoreListChildAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_more_list_child_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = data[position]
        holder.img.setImageResource(dataItem.icon)
        holder.text.text = dataItem.text
        if (data.size == 1) {
            holder.parent.background = ContextCompat.getDrawable(
                context,
                R.drawable.selected_list_item_ripple_curved_edges
            )
        } else {
            when (holder.adapterPosition) {
                0 -> {
                    holder.parent.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.selected_list_item_ripple_curved_top
                    )
                }
                data.size - 1 -> {
                    holder.parent.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.selected_list_item_ripple_curved_bottom
                    )
                }
                else -> {
                    holder.parent.background = ContextCompat.getDrawable(
                        context,
                        R.drawable.selected_list_item_ripple_straight
                    )
                }
            }
        }
        holder.parent.setOnClickListener {
            if (context is Home && dataItem.text.equals(
                    context.resources.getString(R.string.log_out),
                    true
                )
            ) {
                context.logOut()
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var parent: RelativeLayout = v.findViewById(R.id.parent)
        var img: ImageView = v.findViewById(R.id.img)
        var text: TextView = v.findViewById(R.id.text)
    }
}