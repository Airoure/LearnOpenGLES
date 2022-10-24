package com.zjl.learnopengles.draw

import android.content.Context
import android.opengl.GLES20
import com.zjl.learnopengles.util.VertexArray
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * Project Name: LearnOpenGLES
 * ClassName:    BaseShape
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年10月24日 11:52
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
abstract class BaseShape(protected val context: Context) {
    protected var vertexArray: VertexArray? = null

    protected var indexArray: VertexArray? = null
    protected var mProgram = 0

    protected var modelMatrix = FloatArray(16)
    protected var viewMatrix = FloatArray(16)
    protected var projectionMatrix = FloatArray(16)
    protected var mvpMatrix = FloatArray(16)

    protected var POSITION_COMPONENT_COUNT = 0

    protected var TEXTURE_COORDINATES_COMPONENT_COUNT = 2

    protected var STRIDE = 0

    open fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {}

    open fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {}

    open fun onDrawFrame(gl: GL10?) {
        GLES20.glClearColor(0f, 0f, 0f, 1f)
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT)
    }


    open fun onDrawFrame(gl: GL10?, mvpMatrix: FloatArray?) {
        GLES20.glClearColor(0f, 0f, 1f, 1f)
        GLES20.glClear(GLES20.GL_DEPTH_BUFFER_BIT or GLES20.GL_COLOR_BUFFER_BIT)
    }


    open fun onSurfaceDestroyed() {}

}