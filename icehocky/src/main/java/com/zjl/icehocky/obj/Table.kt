package com.zjl.icehocky.obj

import com.zjl.icehocky.Constants
import com.zjl.icehocky.data.VertexArray

/**
 * Project Name: LearnOpenGLES
 * ClassName:    Table
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月03日 14:12
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class Table {

    companion object {
        private const val POSITION_COMPONENT_COUNT = 2
        private const val TEXTURE_COORDINATES_COMPONENT_COUNT = 2
        private const val STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT

        private val VERTEX_DATA = floatArrayOf(
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0.8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
        )
    }

    private val vertexArray: VertexArray

    init {
        vertexArray = VertexArray(VERTEX_DATA)
    }

    fun bindData(textureProgram: S)
}