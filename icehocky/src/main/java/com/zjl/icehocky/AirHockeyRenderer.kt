package com.zjl.icehocky

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.zjl.icehocky.obj.Mallet
import com.zjl.icehocky.obj.Table
import com.zjl.icehocky.program.ColorShaderProgram
import com.zjl.icehocky.program.TextureShaderProgram
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLES20.*
import android.opengl.Matrix
import android.util.Log
import com.zjl.icehocky.utils.TextureHelper

/**
 * Project Name: LearnOpenGLES
 * ClassName:    AirHockeyRenderer
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月04日 10:40
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class AirHockeyRenderer(private val context: Context) : GLSurfaceView.Renderer {

    private val projectionMatrix = FloatArray(16)
    private val modelMatrix = FloatArray(16)

    private var table: Table? = null
    private var mallet: Mallet? = null

    private var textureProgram: TextureShaderProgram? = null
    private var colorProgram: ColorShaderProgram? = null

    private var texture: Int = 0

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        glClearColor(0f, 0f, 0f, 0f)
        table = Table()
        mallet = Mallet()

        textureProgram = TextureShaderProgram(context)
        colorProgram = ColorShaderProgram(context)

        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        glViewport(0, 0, width, height)

        Matrix.perspectiveM(projectionMatrix, 0,45f, width.toFloat() / height, 1f, 10f)

        Matrix.setIdentityM(modelMatrix, 0)
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -2.5f)
        Matrix.rotateM(modelMatrix, 0 ,-60f, 1f, 0f, 0f)

        val tmpArray = FloatArray(16)
        Matrix.multiplyMM(tmpArray, 0, projectionMatrix, 0, modelMatrix, 0)
        System.arraycopy(tmpArray, 0, projectionMatrix,0, tmpArray.size)
    }

    override fun onDrawFrame(gl: GL10?) {
        glClear(GL_COLOR_BUFFER_BIT)

        textureProgram!!.apply {
            useProgram()
            setUniforms(projectionMatrix, texture)
            table?.bindData(this)
            table?.draw()
        }

        colorProgram!!.apply {
            useProgram()
            setUniforms(projectionMatrix)
            mallet?.bindData(this)
            mallet?.draw()
        }
    }

}