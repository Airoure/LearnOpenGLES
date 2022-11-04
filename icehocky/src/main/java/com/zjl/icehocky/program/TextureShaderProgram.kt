package com.zjl.icehocky.program

import android.content.Context
import android.opengl.GLES20.*
import com.zjl.icehocky.R

/**
 * Project Name: LearnOpenGLES
 * ClassName:    TextureShaderProgram
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月03日 15:02
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class TextureShaderProgram(
    context: Context,
) : ShaderProgram(context, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader) {
    // uniform
    private val uMatrixLocation: Int
    private val uTextureUnitLocation: Int

    // attribute
    private val aPositionLocation: Int
    private val aTextureCoordinatesLocation: Int

    init {
        uMatrixLocation = glGetUniformLocation(programId, U_MATRIX)
        uTextureUnitLocation = glGetUniformLocation(programId, U_TEXTURE_UNIT)

        aPositionLocation = glGetAttribLocation(programId, A_POSITION)
        aTextureCoordinatesLocation = glGetAttribLocation(programId, A_TEXTURE_COORDINATES)
    }

    fun setUniforms(matrix: FloatArray, textureId: Int) {
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0)
        glActiveTexture(GL_TEXTURE0)
        glBindTexture(GL_TEXTURE_2D, textureId)
        glUniform1i(uTextureUnitLocation, 0)
    }

    fun getPositionAttributeLocation() = aPositionLocation

    fun getTextureCoordinatesAttributeLocation() = aTextureCoordinatesLocation

}