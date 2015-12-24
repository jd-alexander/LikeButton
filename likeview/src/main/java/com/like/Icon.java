package com.like;

import android.support.annotation.DrawableRes;

/**
 * Created by Joel on 23/12/2015.
 */
public class Icon {
    private int onIconResourceId;
    private int offIconResourceId;
    private IconType iconType;

    public Icon(@DrawableRes int onIconResourceId,@DrawableRes int offIconResourceId, IconType iconType) {
        this.onIconResourceId = onIconResourceId;
        this.offIconResourceId = offIconResourceId;
        this.iconType = iconType;
    }

    public int getOffIconResourceId() {
        return offIconResourceId;
    }

    public void setOffIconResourceId(@DrawableRes int offIconResourceId) {
        this.offIconResourceId = offIconResourceId;
    }

    public int getOnIconResourceId() {
        return onIconResourceId;
    }

    public void setOnIconResourceId(@DrawableRes int onIconResourceId) {
        this.onIconResourceId = onIconResourceId;
    }

    public IconType getIconType() {
        return iconType;
    }

    public void setIconType(IconType iconType) {
        this.iconType = iconType;
    }
}
