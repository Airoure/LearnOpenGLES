package com.zjl.icehocky.program

import android.content.Context
import android.opengl.GLES20.*
import com.zjl.icehocky.R

/**
 * Project Name: LearnOpenGLES
 * ClassName:    ColorShaderProgram
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月04日 10:03
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class ColorShaderProgram(
    context: Context
) : ShaderProgram(context, R.raw.simple_vertex_shader, R.raw.texture_color_fragment_shader) {

    private val uMatrixLocation: Int
    private val aPositionLocation: Int
    private val aColorLocation: Int

    init {
        uMatrixLocation = glGetUniformLocation(programId, U_MATRIX)
        aPositionLocation = glGetAttribLocation(programId, A_POSITION)
        aColorLocation = glGetAttribLocation(programId, A_COLOR)
    }

    fun setUniforms(matrix: FloatArray) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
    }

    fun getPositionAttributeLocation() = aPositionLocation

    fun getColorAttributeLocation() = aColorLocation
}