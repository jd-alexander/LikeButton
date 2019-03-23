package com.like

/**
 * Created by Joel on 23/12/2015.
 */
interface OnLikeListener {
    fun liked(likeButton: LikeButton)
    fun unLiked(likeButton: LikeButton)
}
