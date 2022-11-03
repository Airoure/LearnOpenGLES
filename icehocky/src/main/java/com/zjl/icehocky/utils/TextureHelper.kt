package com.zjl.icehocky.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES20.*
import android.opengl.GLUtils
import android.util.Log

/**
 * Project Name: LearnOpenGLES
 * ClassName:    TextureHelper
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月03日 10:59
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
object TextureHelper {

    fun loadTexture(context: Context, resourceId: Int): Int {
        val textureObjId = IntArray(1)
        glGenTextures(1, textureObjId, 0)

        if (textureObjId[0] == 0) {
            Log.e("Tesaaaw", "generate texture failed")
            return 0
        }


        val options = BitmapFactory.Options()
        options.inScaled = false

        val bitmap = BitmapFactory.decodeResource(context.resources, resourceId, options)
        if (bitmap == null) {
            glDeleteTextures(1, textureObjId, 0)
            return 0
        }

        glBindTexture(GL_TEXTURE_2D, textureObjId[0])

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR_MIPMAP_LINEAR)
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR)

        GLUtils.texImage2D(GL_TEXTURE_2D, 0, bitmap, 0)
        bitmap.recycle()

        /*glGenerateMipmap(GL_TEXTURE_2D)*/
        glBindTexture(GL_TEXTURE_2D, 0)
        return textureObjId[0]
    }
}