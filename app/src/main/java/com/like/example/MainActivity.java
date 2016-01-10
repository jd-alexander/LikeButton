package com.like.example;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.like.LikeButton;
import com.like.OnLikeListener;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnLikeListener {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.star_button)
    LikeButton starButton;
    @Bind(R.id.heart_button)
    LikeButton likeButton;
    @Bind(R.id.thumb_button)
    LikeButton thumbButton;
    @Bind(R.id.smile_button)
    LikeButton smileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        starButton.setOnLikeListener(this);
        likeButton.setOnLikeListener(this);
        smileButton.setOnLikeListener(this);
        thumbButton.setOnLikeListener(this);

        thumbButton.setLiked(true);

        usingCustomIcons();

    }

    public void usingCustomIcons() {

        //shown when the button is in its default state or when unLiked.
        smileButton.setUnlikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(this, CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.darker_gray).sizeDp(25).toBitmap()));

        //shown when the button is liked!
        smileButton.setLikeDrawable(new BitmapDrawable(getResources(), new IconicsDrawable(this, CommunityMaterial.Icon.cmd_emoticon).colorRes(android.R.color.holo_purple).sizeDp(25).toBitmap()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void liked(LikeButton likeButton) {
        Toast.makeText(this, "Liked!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void unLiked(LikeButton likeButton) {
        Toast.makeText(this, "Disliked!", Toast.LENGTH_SHORT).show();
    }
}
