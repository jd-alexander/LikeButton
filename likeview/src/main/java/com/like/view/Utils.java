package com.like.view;

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

    public static List<Icon> getIcons()
    {
        List<Icon> icons = new ArrayList<>();
        icons.add(new Icon(R.drawable.heart_on,R.drawable.heart_off,IconType.Heart));
        icons.add(new Icon(R.drawable.star_on,R.drawable.star_off,IconType.Star));
        icons.add(new Icon(R.drawable.thumb_on,R.drawable.thumb_off,IconType.Thumb));

        return icons;
    }
}

