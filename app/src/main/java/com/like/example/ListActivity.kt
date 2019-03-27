package com.like.example

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView

import com.like.example.adapters.ListAdapter

import butterknife.BindView
import butterknife.ButterKnife

class ListActivity : AppCompatActivity() {

    @BindView(R.id.list_view)
    lateinit var listView: RecyclerView
    private var adapter: ListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        ButterKnife.bind(this)

        adapter = ListAdapter(this)
        listView!!.adapter = adapter

    }
}
