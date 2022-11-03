package com.zjl.icehocky.data

import android.opengl.GLES20
import com.zjl.icehocky.Constants
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

/**
 * Project Name: LearnOpenGLES
 * ClassName:    VertexArray
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月03日 11:30
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class VertexArray(vertexData: FloatArray) {

    private val floatBuffer: FloatBuffer

    init {
        floatBuffer = ByteBuffer
            .allocateDirect(vertexData.size * Constants.BYTES_PER_FLOAT)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(vertexData)

    }

    fun setVertexAttribPointer(
        offset: Int,
        attributeLocation: Int,
        componentCount: Int,
        stride: Int
    ) {
        floatBuffer.position(offset)
        GLES20.glVertexAttribPointer(
            attributeLocation,
            componentCount,
            GLES20.GL_FLOAT,
            false,
            stride,
            floatBuffer
        )
        GLES20.glEnableVertexAttribArray(attributeLocation)

        floatBuffer.position(0)
    }
}