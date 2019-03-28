package com.like.example

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.like.LikeButton
import com.like.OnAnimationEndListener
import com.like.OnLikeListener
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

class MainActivity : AppCompatActivity(), OnLikeListener, OnAnimationEndListener {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.star_button)
    lateinit var starButton: LikeButton
    @BindView(R.id.heart_button)
    lateinit var likeButton: LikeButton
    @BindView(R.id.thumb_button)
    lateinit var thumbButton: LikeButton
    @BindView(R.id.smile_button)
     lateinit var smileButton: LikeButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)

        starButton!!.setOnAnimationEndListener(this)
        starButton!!.setOnLikeListener(this)

        likeButton!!.setOnLikeListener(this)
        likeButton!!.setOnAnimationEndListener(this)

        smileButton!!.setOnLikeListener(this)
        smileButton!!.setOnAnimationEndListener(this)

        thumbButton!!.setOnLikeListener(this)
        thumbButton!!.setOnAnimationEndListener(this)

        thumbButton!!.setLiked(true)

        usingCustomIcons()

    }

    fun usingCustomIcons() {

        //shown when the button is in its default state or when unLiked.
        smileButton!!.setUnlikeDrawable(BitmapDrawable(resources, IconicsDrawable(this, CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.darker_gray).sizeDp(25).toBitmap()))

        //shown when the button is liked!
        smileButton!!.setLikeDrawable(BitmapDrawable(resources, IconicsDrawable(this, CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.holo_purple).sizeDp(25).toBitmap()))
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun liked(likeButton: LikeButton) {
        Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show()
    }

    override fun unLiked(likeButton: LikeButton) {
        Toast.makeText(this, "Disliked!", Toast.LENGTH_SHORT).show()
    }

    override fun onAnimationEnd(likeButton: LikeButton) {
        Log.d(TAG, "Animation End for %s$likeButton")
    }

    @OnClick(R.id.button)
    fun navigateToList() {
        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
    }

    @OnClick(R.id.coordinator_button)
    fun navigateToCoordinator() {
        val intent = Intent(this, CoordinatorActivity::class.java)
        startActivity(intent)
    }

    companion object {

        val TAG = "MainActivity"
    }
}
