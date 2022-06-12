package com.codeeyeq.controller.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.codeeyeq.R
import com.codeeyeq.adapters.MoreListParentAdapter
import com.codeeyeq.controller.activities.Home
import com.codeeyeq.data.MoreListChild
import com.codeeyeq.data.MoreListParent
import com.codeeyeq.model.NoDividerListItemDecoration


class HomeFragmentMore : Fragment() {
    private lateinit var list: RecyclerView
    private lateinit var parentListItems: ArrayList<MoreListParent>
    private lateinit var childListItems: ArrayList<MoreListChild>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_home_more, container, false)
        list = v.findViewById(R.id.list)
        parentListItems = ArrayList()
        childListItems = ArrayList()
        parentListItems.add(
            MoreListParent(
                parentListItems.size.inc().toString(),
                "",
                "",
                "",
                childListItems
            )
        )

        childListItems = ArrayList()
        childListItems.add(MoreListChild(getString(R.string.my_posts), R.drawable.my_posts))
        childListItems.add(MoreListChild(getString(R.string.saved), R.drawable.saved))
        childListItems.add(MoreListChild(getString(R.string.interests), R.drawable.interests))
        parentListItems.add(
            MoreListParent(
                parentListItems.size.inc().toString(),
                "",
                "",
                "",
                childListItems
            )
        )

        childListItems = ArrayList()
        childListItems.add(MoreListChild(getString(R.string.membership), R.drawable.membership))
        parentListItems.add(
            MoreListParent(
                parentListItems.size.inc().toString(),
                "",
                "",
                "",
                childListItems
            )
        )

        childListItems = ArrayList()
        childListItems.add(
            MoreListChild(
                getString(R.string.push_notification),
                R.drawable.push_notification
            )
        )
        childListItems.add(MoreListChild(getString(R.string.clear_cache), R.drawable.clear_cache))
        parentListItems.add(
            MoreListParent(
                parentListItems.size.inc().toString(),
                "",
                "",
                "",
                childListItems
            )
        )

        childListItems = ArrayList()
        childListItems.add(MoreListChild(getString(R.string.about_codeeyeq), R.drawable.about))
        childListItems.add(
            MoreListChild(
                getString(R.string.privacy_policy),
                R.drawable.privacy_policy
            )
        )
        childListItems.add(MoreListChild(getString(R.string.help_support), R.drawable.help))
        childListItems.add(
            MoreListChild(
                getString(R.string.tell_a_friend),
                R.drawable.tell_a_friend
            )
        )
        parentListItems.add(
            MoreListParent(
                parentListItems.size.inc().toString(),
                "",
                "",
                "",
                childListItems
            )
        )

        childListItems = ArrayList()
        childListItems.add(MoreListChild(getString(R.string.log_out), R.drawable.log_out))
        parentListItems.add(
            MoreListParent(
                parentListItems.size.inc().toString(),
                "",
                "",
                "",
                childListItems
            )
        )

        list.apply {
            addItemDecoration(
                NoDividerListItemDecoration(
                    resources.getDimension(R.dimen.padding).toInt(),
                    resources.getDimension(R.dimen.paragraph).toInt(),
                    0,
                    resources.getDimension(R.dimen.more_list_bottom_padding).toInt()
                )
            )
            adapter = MoreListParentAdapter(requireContext(), parentListItems)
            var scrollState = "SCROLL_STATE_IDLE"
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (scrollState != "SCROLL_STATE_IDLE") {
                        if (dy > 0) {
                            (activity as Home).hideBottomNavigation()
                        } else {
                            (activity as Home).showBottomNavigation()
                        }
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    scrollState = if (newState == SCROLL_STATE_IDLE) {
                        "SCROLL_STATE_IDLE"
                    } else {
                        "SCROLL_STATE_ACTIVE"
                    }
                }
            })
        }

        v.findViewById<ImageView>(R.id.share).setOnClickListener {

        }
        return v
    }
}