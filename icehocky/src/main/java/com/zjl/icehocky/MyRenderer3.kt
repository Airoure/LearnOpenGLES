package com.zjl.icehocky

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.Matrix
import android.util.Log
import com.zjl.icehocky.utils.ShaderHelper
import com.zjl.icehocky.utils.TextResourceReader
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Project Name: LearnOpenGLES
 * ClassName:    MyRenderer2
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月02日 14:44
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class MyRenderer3(private val context: Context) : GLSurfaceView.Renderer {

    private val A_POSITION = "a_Position"
    private val A_COLOR = "a_Color"
    private val U_MATRIX = "u_Matrix"

    private var aPositionLocation = -1
    private var aColorLocation = -1
    private var uMatrixLocation = -1

    private val POSITION_COMPONENT_COUNT = 2
    private val COLOR_COMPONENT_COUNT = 3
    private val BYTES_PER_FLOAT = 4

    private val STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT

    private val projectMatrix = FloatArray(16)

    private val modelMatrix = FloatArray(16)

    // x,y,r,g,b
    private val destVertex = floatArrayOf(
        0f, 0f, 1f, 1f, 1f,
        0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
        -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
        0.5f, 0.8f, 0.7f, 0.7f, 0.7f,

        // line
        -0.5f, 0f, 1f, 0f, 0f,
        0.5f, 0f, 1f, 0f, 0f,
        // mallets
        0f, 0.25f, 0f, 0f, 1f,
        0f, -0.25f,1f, 0f, 0f
    )

    private val vertexDataBuffer: FloatBuffer



    private var programId = -1

    init {
        vertexDataBuffer = ByteBuffer
            .allocateDirect(BYTES_PER_FLOAT * destVertex.size)
            .order(ByteOrder.nativeOrder())
            .asFloatBuffer()
            .put(destVertex)

        vertexDataBuffer.position(0)
    }


    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        val vertexShader = ShaderHelper.compileVertexShader(TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader3))
        val fragmentShader = ShaderHelper.compileFragmentShader(TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader3))

        programId = ShaderHelper.linkProgram(vertexShader, fragmentShader)
        ShaderHelper.validateProgram(programId)

        GLES20.glUseProgram(programId)

        aPositionLocation = GLES20.glGetAttribLocation(programId, A_POSITION)
        aColorLocation = GLES20.glGetAttribLocation(programId, A_COLOR)
        uMatrixLocation = GLES20.glGetUniformLocation(programId, U_MATRIX)

        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexDataBuffer)
        GLES20.glEnableVertexAttribArray(aPositionLocation)

        vertexDataBuffer.position(POSITION_COMPONENT_COUNT)
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexDataBuffer)
        GLES20.glEnableVertexAttribArray(aColorLocation)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)

        /*val aspectRatio = if (width > height) width.toFloat() / height else height.toFloat() / width
        Log.e("Tesaaaw", "Tesaaaw $aspectRatio")
        if (width > height) {
            Matrix.orthoM(projectMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f)
        } else {
            Matrix.orthoM(projectMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f)
        }*/

        Matrix.perspectiveM(projectMatrix, 0, 45f, width.toFloat() / height, 1f, 10f)

        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f)
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f)

        val tempMatrix = FloatArray(16)
        Matrix.multiplyMM(tempMatrix, 0, projectMatrix, 0, modelMatrix, 0)
        System.arraycopy(tempMatrix, 0, projectMatrix, 0, tempMatrix.size)
    }

    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectMatrix, 0)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6)

        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2)

        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1)

        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1)
    }
}