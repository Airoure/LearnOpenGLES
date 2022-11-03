package com.zjl.icehocky

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.zjl.icehocky.utils.ShaderHelper
import com.zjl.icehocky.utils.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Project Name: LearnOpenGLES
 * ClassName:    MyRenderer
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月01日 16:39
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class MyRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private val U_COLOR = "u_Color"
    private val A_POSITION = "a_Position"

    private var uColorLocation = -1
    private var aPositionLocation = -1

    private val tableVertices = floatArrayOf(
        -0.5f, -0.5f,
        0.5f, 0.5f,
        -0.5f, 0.5f,

        -0.5f, -0.5f,
        0.5f, -0.5f,
        0.5f, 0.5f,
        // line
        -0.5f, 0f,
        0.5f, 0f,
        // mallets
        0f, 0.25f,
        0f, -0.25f,
        0f, 0f,

        // 边框
        -0.75f, -0.75f,
        0.75f, 0.75f,
        -0.75f, 0.75f,

        -0.75f, -0.75f,
        0.75f, -0.75f,
        0.75f, 0.75f
    )

    private val BYTES_PER_FLOAT = 4

    private var vertexData: FloatBuffer

    private var programId: Int = -1

    init {
        vertexData = ByteBuffer
            .allocateDirect(BYTES_PER_FLOAT * tableVertices.size)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(tableVertices)

        vertexData.position(0)
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //GLES20.glClearColor(1.0f, 0.0f, 0.0f, 0.0f)

        val vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader)
        val fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader)

        val vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource)
        val fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource)
        programId = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        ShaderHelper.validateProgram(programId)
        GLES20.glUseProgram(programId)

        uColorLocation = GLES20.glGetUniformLocation(programId, U_COLOR)
        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION)

        GLES20.glVertexAttribPointer(aPositionLocation, 2, GLES20.GL_FLOAT, false, 0, vertexData)
        GLES20.glEnableVertexAttribArray(aPositionLocation)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
    }

    override fun onDrawFrame(gl: GL10?) {
        // 清空屏幕,并且用之前的glClearColor来填充
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        GLES20.glUniform4f(uColorLocation, 0.2f, 0.3f, 0.4f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 11, 6)

        GLES20.glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6)

        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2)

        GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)

        GLES20.glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)

        GLES20.glUniform4f(uColorLocation, 1.0f, 0.5f, 0.0f, 1.0f)
        GLES20.glDrawArrays(GLES20.GL_POINTS, 10, 1)



    }
}