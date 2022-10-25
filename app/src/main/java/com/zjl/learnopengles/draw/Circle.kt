package com.zjl.learnopengles.draw

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import com.zjl.learnopengles.R
import com.zjl.learnopengles.util.ShaderHelper
import com.zjl.learnopengles.util.VertexArray
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

/**
 * Project Name: LearnOpenGLES
 * ClassName:    Circle
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年10月24日 17:03
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class Circle(context: Context) : BaseShape(context) {
    // 由于OpenGL只能绘制三角形和点,所以使用切割法绘制原型
    private val VERTEX_DATA_NUM = 360
    private val circleVertex = FloatArray(VERTEX_DATA_NUM * 2 + 4)
    private val radian = (2 * PI / VERTEX_DATA_NUM).toFloat()

    private val radius = 0.8f

    private fun initVertexData() {
        circleVertex[0] = 0f
        circleVertex[1] = 0f
        for (i in 0 until VERTEX_DATA_NUM) {
            circleVertex[2 * i + 2] = (radius * cos((radian * i).toDouble())).toFloat()
            circleVertex[2 * i + 3] = (radius * sin((radian * i).toDouble())).toFloat()
        }

        circleVertex[2 * VERTEX_DATA_NUM + 2] = (radius * cos((radian).toDouble())).toFloat()
        circleVertex[2 * VERTEX_DATA_NUM + 3] = (radius * sin((radian).toDouble())).toFloat()
    }


    private val U_COLOR = "u_Color"
    private val A_POSITION = "a_Position"
    private val U_PROJECTIONMATRIX = "u_ProjectionMatrix"
    private var aColorLocation = 0
    private var aPositionLocation = 0
    private var aProjectionMatrixLocation = 0

    init {
        mProgram = ShaderHelper.buildProgram(context, R.raw.circle_vertex_shader, R.raw.circle_fragment_shader)
        initVertexData()
        GLES20.glUseProgram(mProgram)
        vertexArray = VertexArray(circleVertex)

        POSITION_COMPONENT_COUNT = 2
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        aColorLocation = GLES20.glGetUniformLocation(mProgram, U_COLOR)

        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION)

        aProjectionMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_PROJECTIONMATRIX)

        vertexArray?.setVertexAttribPointer(0, aPositionLocation, POSITION_COMPONENT_COUNT, 0)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
        val aspectRatio = if (width > height) width.toFloat() / height else height.toFloat() / width
        if (width > height) {
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, 0f, 10f)
        } else {
            Matrix.orthoM(projectionMatrix,0,-1f,1f,-aspectRatio,aspectRatio,0f,10f)
        }
    }

    override fun onDrawFrame(gl: GL10?) {
        super.onDrawFrame(gl)

        GLES20.glUniform4f(aColorLocation, 0.0f, 0.0f, 1.0f, 1.0f)
        GLES20.glUniformMatrix4fv(aProjectionMatrixLocation, 1, false, projectionMatrix, 0)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, VERTEX_DATA_NUM + 2);
    }
}