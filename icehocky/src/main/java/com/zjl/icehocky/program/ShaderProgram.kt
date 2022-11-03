package com.zjl.icehocky.program

import android.content.Context
import android.opengl.GLES20
import com.zjl.icehocky.utils.ShaderHelper
import com.zjl.icehocky.utils.TextResourceReader

/**
 * Project Name: LearnOpenGLES
 * ClassName:    ShaderProgram
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月03日 14:33
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
open class ShaderProgram(
    context: Context,
    vertexShaderResourceId: Int,
    fragmentShaderResourceId: Int
) {

    protected val U_MATRIX = "u_Matrix"
    protected val U_TEXTURE_UNIT = "u_TextureUnit"

    protected val A_POSITION = "a_Position"
    protected val A_COLOR = "a_Color"
    protected val A_TEXTURE_COORDINATES = "a_TextureCoordinates"

    protected val programId: Int

    init {
        programId = ShaderHelper.buildProgram(
            TextResourceReader.readTextFileFromResource(
                context,
                vertexShaderResourceId
            ),
            TextResourceReader.readTextFileFromResource(
                context,
                fragmentShaderResourceId
            )
        )
    }

    fun useProgram() {
        GLES20.glUseProgram(programId)
    }
}