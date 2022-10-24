package com.zjl.learnopengles.draw

import android.content.Context
import android.opengl.GLES20
import com.zjl.learnopengles.R
import com.zjl.learnopengles.util.ShaderHelper
import com.zjl.learnopengles.util.VertexArray
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Project Name: LearnOpenGLES
 * ClassName:    Triangle
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年10月24日 16:54
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class Triangle(context: Context) : BaseShape(context) {

    private val triangleVertex = floatArrayOf(
        -0.5f, 0.5f,
        -0.5f, -0.5f,
        0.5f, -0.5f
    )

    private val U_COLOR = "u_Color"
    private val A_POSITION = "a_Position"
    private var aColorLocation = 0
    private var aPositionLocation = 0

    init {
        mProgram = ShaderHelper.buildProgram(context, R.raw.line_vertex_shader, R.raw.line_fragment_shader)

        GLES20.glUseProgram(mProgram)
        vertexArray = VertexArray(triangleVertex)

        POSITION_COMPONENT_COUNT = 2
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        aColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR);

        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);

        vertexArray?.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, 0);
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        super.onDrawFrame(gl)

        GLES20.glUniform4f(aColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)

        GLES20.glDrawArrays(GLES20.GL_LINE_LOOP, 0, 3);
    }
}