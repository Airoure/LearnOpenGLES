package com.zjl.learnopengles.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.GLES20
import android.opengl.GLUtils
import android.util.Log


/**
 * Project Name: LearnOpenGLES
 * ClassName:    TextureHelper
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年10月25日 10:34
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
object TextureHelper {

    fun loadTexture(context: Context, resourceId: Int): Int {
        val options = BitmapFactory.Options()
        options.inScaled = false
        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options) ?: return 0

        return loadTextureByBitmap(bitmap)
    }

    private fun loadTextureByBitmap(bitmap: Bitmap): Int {
        val textureObjId = IntArray(1)
        GLES20.glGenTextures(1, textureObjId, 0)

        if (textureObjId[0] == 0) {
            Log.e("error", "generate texture failed")
            return 0
        }
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjId[0])

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR)

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

        bitmap.recycle()
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
        return textureObjId[0]
    }
}