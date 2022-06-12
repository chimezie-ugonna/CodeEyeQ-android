package com.codeeyeq.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.codeeyeq.R
import com.codeeyeq.data.MoreListParent
import com.codeeyeq.model.ListItemDecoration
import de.hdodenhof.circleimageview.CircleImageView

class MoreListParentAdapter(
    private val context: Context,
    private val data: ArrayList<MoreListParent>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var headerView = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == headerView) {
            HeaderViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.content_more_list_header, parent, false)
            )
        } else {
            ViewHolder(
                LayoutInflater.from(context)
                    .inflate(R.layout.list_more_list_parent_item, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataItem = data[position]
        if (getItemViewType(position) == headerView) {
            val headerViewHolder = (holder as HeaderViewHolder)
            if (dataItem.section == "1") {
                headerViewHolder.name.text =
                    if (!dataItem.name.equals("null", true) && dataItem.name != "") {
                        dataItem.name
                    } else {
                        context.getString(R.string.no_name)
                    }
                headerViewHolder.email.text =
                    if (!dataItem.email.equals("null", true) && dataItem.email != "") {
                        dataItem.email
                    } else {
                        context.getString(R.string.no_email)
                    }
            }
            headerViewHolder.parent.setOnClickListener {

            }
        } else {
            val viewHolder = (holder as ViewHolder)
            if (dataItem.section != "1") {
                viewHolder.list.apply {
                    addItemDecoration(
                        ListItemDecoration(
                            ContextCompat.getDrawable(
                                context,
                                R.drawable.list_divider
                            )
                        )
                    )
                    adapter = MoreListChildAdapter(context, dataItem.list)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            headerView
        } else {
            1
        }
    }

    class HeaderViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var parent: RelativeLayout = v.findViewById(R.id.parent)
        var img: CircleImageView = v.findViewById(R.id.img)
        var name: TextView = v.findViewById(R.id.name)
        var email: TextView = v.findViewById(R.id.email)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var parent: RelativeLayout = v.findViewById(R.id.parent)
        var list: RecyclerView = v.findViewById(R.id.list)
    }
}
