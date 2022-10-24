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
 * ClassName:    Point
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年10月24日 14:53
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class Point(context: Context) : BaseShape(context) {

    private val U_COLOR = "u_Color"
    private val A_POSITION = "a_Position"

    private var aColorLocation: Int = 0
    private var aPositionLocation: Int = 0

    val pointVertex = floatArrayOf(
        0f, 0f,
        0.01f, 0.01f,
        -0.1f, -0.1f,
        0.02f, 0.02f,
        0.03f, 0.03f,
        0.04f, 0.04f,
        0.05f, 0.05f,
        0.06f, 0.06f,
    )

    init {
        mProgram = ShaderHelper.buildProgram(context, R.raw.point_vertex_shader, R.raw.point_fragment_shader)
        GLES20.glUseProgram(mProgram)

        vertexArray = VertexArray(pointVertex)
        POSITION_COMPONENT_COUNT = 2
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)

        // 绑定值
        aColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR)
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION)

        vertexArray?.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, 0)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        super.onDrawFrame(gl)
        // 给绑定的值赋值
        GLES20.glUniform4f(aColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, pointVertex.size / POSITION_COMPONENT_COUNT);
    }

    override fun onDrawFrame(gl: GL10?, mvpMatrix: FloatArray?) {
        super.onDrawFrame(gl, mvpMatrix)
    }

    override fun onSurfaceDestroyed() {
        super.onSurfaceDestroyed()
        GLES20.glDeleteProgram(mProgram)
    }
}