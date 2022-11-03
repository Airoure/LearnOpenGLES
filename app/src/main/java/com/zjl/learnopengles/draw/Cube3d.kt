package com.zjl.learnopengles.draw

import android.content.Context
import android.opengl.GLES20
import android.opengl.GLES30
import com.zjl.learnopengles.R
import com.zjl.learnopengles.util.Constants
import com.zjl.learnopengles.util.MatrixState
import com.zjl.learnopengles.util.ShaderHelper
import com.zjl.learnopengles.util.TextureHelper
import java.nio.ByteBuffer
import java.nio.ByteOrder
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Project Name: LearnOpenGLES
 * ClassName:    Cube3d
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年10月31日 10:46
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class Cube3d(context: Context) : BaseShape(context) {

    private val U_VIEW_MATRIX = "u_ViewMatrix"
    private val U_MODEL_MATRIX = "u_ModelMatrix"
    private val U_PROJECTION_MATRIX = "u_ProjectionMatrix"
    private val A_POSITION = "a_Position"
    private val A_TEXTURE_COORDINATE = "a_TextureCoordinates"
    private val U_TEXTURE_UNIT = "u_TextureUnit"


    protected var uModelMatrixAttr: Int = 0
    protected var uViewMatrixAttr: Int = 0
    protected var uProjectionMatrixAttr: Int = 0
    protected var aPositionAttr: Int = 0
    protected var aTextureCoordinateAttr: Int = 0
    protected var uTextureUnitAttr: Int = 0

    private var mTextureId: IntArray? = null

    var vertexFloatBuffer = ByteBuffer
        .allocateDirect(8 * 6 * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()

    var textureFloatBuffer = ByteBuffer
        .allocateDirect(8 * 6 * 4)
        .order(ByteOrder.nativeOrder())
        .asFloatBuffer()

    val cubeSize = 1f

    val halfCubeSize = cubeSize / 2

    var eyeX = 0.0f
    val eyeY = 0.0f
    var eyeZ = 2.0f

    val lookX = 0.0f
    val lookY = 0.0f
    val lookZ = 0.0f

    val upX = 0.0f
    val upY = 1.0f
    val upZ = 0.0f

    init {
        mProgram = ShaderHelper.buildProgram(context, R.raw.picture_vertex_shader, R.raw.picture_fragment_shader)

        GLES20.glUseProgram(mProgram)

        initVertexData()

        initTextureData()

        POSITION_COMPONENT_COUNT = 2
    }

    private fun initVertexData() {
        val faceLeft = -cubeSize / 2
        val faceRight = -faceLeft
        val faceTop = cubeSize / 2
        val faceBottom = -faceTop

        val vertices = floatArrayOf(
            faceLeft, faceBottom,
            faceRight, faceBottom,
            faceLeft, faceTop,
            faceRight, faceTop
        )
        vertexFloatBuffer.put(vertices)
        vertexFloatBuffer.position(0)
    }

    private fun initTextureData() {
        val texCoords = floatArrayOf(
            0.0f, 1.0f,
            1.0f, 1.0f,
            0.0f, 0.0f,
            1.0f, 0.0f
        )
        textureFloatBuffer.put(texCoords)
        textureFloatBuffer.position(0)
    }

    private val indices = byteArrayOf(
        0, 1, 2, 3
    )

    private var byteBuffer: ByteBuffer? = null

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)
        GLES20.glClearColor(0f,0f,0f,1f)

        GLES30.glEnable(GLES30.GL_DEPTH_TEST)

        aPositionAttr = GLES20.glGetAttribLocation(mProgram, A_POSITION)
        uModelMatrixAttr = GLES20.glGetUniformLocation(mProgram, U_MODEL_MATRIX)
        uViewMatrixAttr = GLES20.glGetUniformLocation(mProgram, U_VIEW_MATRIX)
        uProjectionMatrixAttr = GLES20.glGetUniformLocation(mProgram, U_PROJECTION_MATRIX)

        aTextureCoordinateAttr = GLES20.glGetAttribLocation(mProgram, A_TEXTURE_COORDINATE)
        uTextureUnitAttr = GLES20.glGetUniformLocation(mProgram, U_TEXTURE_UNIT)

        mTextureId = TextureHelper.loadCubeTexture(context, TextureHelper.CUBE)
        GLES20.glUniform1i(uTextureUnitAttr, 0)

        byteBuffer = ByteBuffer.allocateDirect(indices.size * Constants.BYTES_PRE_BYTE).put(indices)
        byteBuffer!!.position(0)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)

        GLES20.glViewport(0, 0, width, height)

        val ratio = width.toFloat() / height
        val left = -ratio
        val bottom = -1.0f
        val top = 1.0f
        val near = 1.0f
        val far = 6.0f

        MatrixState.setCamera(eyeX, eyeY, eyeZ, lookX, lookY, lookZ, upX, upY, upZ)

        MatrixState.setProjectFrustum(left, ratio, bottom, top, near, far)

        MatrixState.setInitStack()
    }
}