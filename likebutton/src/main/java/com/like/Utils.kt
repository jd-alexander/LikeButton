package com.like

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue
import com.like.view.R
import java.util.*

/**
 * Created by Joel on 23/12/2015.
 */
object Utils {

    val icons: List<Icon>
        get() {
            val icons = ArrayList<Icon>()
            icons.add(Icon(R.drawable.heart_on, R.drawable.heart_off, IconType.Heart))
            icons.add(Icon(R.drawable.star_on, R.drawable.star_off, IconType.Star))
            icons.add(Icon(R.drawable.thumb_on, R.drawable.thumb_off, IconType.Thumb))

            return icons
        }

    fun mapValueFromRangeToRange(value: Double, fromLow: Double, fromHigh: Double, toLow: Double, toHigh: Double): Double {
        return toLow + (value - fromLow) / (fromHigh - fromLow) * (toHigh - toLow)
    }

    fun clamp(value: Double, low: Double, high: Double): Double {
        return Math.min(Math.max(value, low), high)
    }

    fun resizeDrawable(context: Context, drawable: Drawable, width: Int, height: Int): Drawable {

        val bitmap = getBitmap(drawable, width, height)
        return BitmapDrawable(context.resources, Bitmap.createScaledBitmap(bitmap, width, height, true))
    }

    private fun getBitmap(drawable: Drawable, width: Int, height: Int): Bitmap? {
        return if(drawable is BitmapDrawable){
            drawable.bitmap
        }
        else{
            getBitmapFromCanvas(drawable,width,height)
        }
    }

    private fun getBitmapFromCanvas(vectorDrawable: Drawable, width: Int, height: Int): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0, 0, canvas.width, canvas.height)
        vectorDrawable.draw(canvas)
        return bitmap
    }

    fun dipToPixels(context: Context, dipValue: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
    }
}

