package com.like;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.like.view.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joel on 23/12/2015.
 */
public class Utils {
    public static double mapValueFromRangeToRange(double value, double fromLow, double fromHigh, double toLow, double toHigh) {
        return toLow + ((value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow));
    }

    public static double clamp(double value, double low, double high) {
        return Math.min(Math.max(value, low), high);
    }

    public static List<Icon> getIcons() {
        List<Icon> icons = new ArrayList<>();
        icons.add(new Icon(R.drawable.heart_on, R.drawable.heart_off, IconType.Heart));
        icons.add(new Icon(R.drawable.star_on, R.drawable.star_off, IconType.Star));
        icons.add(new Icon(R.drawable.thumb_on, R.drawable.thumb_off, IconType.Thumb));

        return icons;
    }

    public static Drawable resizeDrawable(Context context, Drawable drawable, int width, int height) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newDrawable = new BitmapDrawable(context.getResources(), Bitmap.createScaledBitmap(bitmap, width, height, true));

        return newDrawable;
    }

    public static float dipToPixels(Context context, float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }
}

