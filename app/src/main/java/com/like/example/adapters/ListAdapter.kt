package com.like.example.adapters

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.like.LikeButton
import com.like.example.R

import java.util.ArrayList

import butterknife.BindView
import butterknife.ButterKnife

/**
 * Created by joel on 3/3/18.
 */

class ListAdapter(private val activity: Activity) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    private var numbers: MutableList<Int>? = null
    private val max = 30

    private fun generateNumbers() {
        numbers = ArrayList()

        for (x in 1..max) {
            numbers!!.add(x)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.list_row, parent, false)

        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.title!!.text = position.toString()
        if (position % 2 == 0) {
            holder.starButton!!.setLiked(true)
        } else
            holder.starButton!!.setLiked(false)

    }

    override fun getItemCount(): Int {
        return max
    }


    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        @BindView(R.id.title)
        lateinit var title: TextView
        @BindView(R.id.star_button)
        lateinit var starButton: LikeButton

        init {
            ButterKnife.bind(this, view)
        }
    }
}
