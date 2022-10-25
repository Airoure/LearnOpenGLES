package com.zjl.learnopengles.draw

import android.content.Context
import android.opengl.GLES20
import android.opengl.Matrix
import android.util.Log
import com.zjl.learnopengles.R
import com.zjl.learnopengles.util.ShaderHelper
import com.zjl.learnopengles.util.TextureHelper
import com.zjl.learnopengles.util.VertexArray
import java.nio.IntBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

/**
 * Project Name: LearnOpenGLES
 * ClassName:    Picture
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年10月25日 11:04
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
class Picture(context: Context): BaseShape(context) {

    private val vertexArrayData = floatArrayOf(
        0f, 0f,
        0.5f, 1f,
        1f, 0f
    )

    // openGL渲染纹理是已左下角为原点,一般图片都是左上角为原点,所以需要翻着y轴
    private val textureArrayData = floatArrayOf(
        0f, 1-0f,
        0.5f, 1-1f,
        1f, 1-0f
    )

    var mVertexArray: VertexArray
    var textureArray: VertexArray

    private val A_POSITION = "a_Position"
    private val A_TEXTURE_COORDINATE = "a_TextureCoordinates"
    private val U_MODEL_MATRIX = "u_ModelMatrix"
    private val U_VIEW_MATRIX = "u_ViewMatrix"
    private val U_PROJECTION_MATRIX = "u_ProjectionMatrix"
    private val U_TEXTURE_UNIT = "u_TextureUnit"

    private var uModelMatrixAttr: Int = 0
    private var uViewMatrixAttr: Int = 0
    private var uProjectionMatrixAttr: Int = 0
    private var aPositionAttr: Int = 0
    private var aTextureCoordinateAttr: Int = 0
    private var uTextureUnitAttr: Int = 0

    private var mTextureId: Int = 0

    init {
        mProgram = ShaderHelper.buildProgram(context, R.raw.picture_vertex_shader, R.raw.picture_fragment_shader)
        GLES20.glUseProgram(mProgram)

        mVertexArray = VertexArray(vertexArrayData)
        textureArray = VertexArray(textureArrayData)

        POSITION_COMPONENT_COUNT = 2
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        super.onSurfaceCreated(gl, config)

        GLES20.glClearColor(0f, 0f, 0f, 1.0f)
        aPositionAttr = GLES20.glGetAttribLocation(mProgram, A_POSITION)
        uModelMatrixAttr = GLES20.glGetUniformLocation(mProgram, U_MODEL_MATRIX)
        uViewMatrixAttr = GLES20.glGetUniformLocation(mProgram, U_VIEW_MATRIX)
        uProjectionMatrixAttr = GLES20.glGetUniformLocation(mProgram, U_PROJECTION_MATRIX)


        aTextureCoordinateAttr = GLES20.glGetAttribLocation(mProgram, A_TEXTURE_COORDINATE)
        uTextureUnitAttr = GLES20.glGetUniformLocation(mProgram, U_TEXTURE_UNIT)


        mTextureId = TextureHelper.loadTexture(context, R.drawable.texture)

        GLES20.glUniform1i(uTextureUnitAttr, 0)


        val intBuffer: IntBuffer = IntBuffer.allocate(1)

        GLES20.glGetIntegerv(GLES20.GL_MAX_COMBINED_TEXTURE_IMAGE_UNITS, intBuffer)

        //Log.d("max combined texture image units " + intBuffer[0])

        Matrix.setIdentityM(modelMatrix, 0)

//        Matrix.rotateM(modelMatrix,0,180f,1f,0f,0f)
        //Matrix.translateM(modelMatrix,0,0f,0.5f,0f)

        Matrix.setIdentityM(viewMatrix, 0)
        Matrix.setIdentityM(projectionMatrix, 0)
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        super.onSurfaceChanged(gl, width, height)
        GLES20.glViewport(0, 0, width, height)

        val aspectRatio = if (width > height) width.toFloat() / height.toFloat() else height.toFloat() / width.toFloat()

        if (width > height) {
            //Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, 0f, 10f)
        } else {
            //Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, 0f, 10f)
        }
    }

    override fun onDrawFrame(gl: GL10?) {
        super.onDrawFrame(gl)
        GLES20.glClearColor(0f, 0f, 0f, 1.0f)

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)

        GLES20.glUniformMatrix4fv(uModelMatrixAttr, 1, false, modelMatrix, 0)
        GLES20.glUniformMatrix4fv(uViewMatrixAttr, 1, false, viewMatrix, 0)
        GLES20.glUniformMatrix4fv(uProjectionMatrixAttr, 1, false, projectionMatrix, 0)

        mVertexArray.setVertexAttribPointer(0, aPositionAttr, POSITION_COMPONENT_COUNT, 0)
        textureArray.setVertexAttribPointer(0, aTextureCoordinateAttr, POSITION_COMPONENT_COUNT, 0)

        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTextureId)

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3)

        GLES20.glDisableVertexAttribArray(aPositionAttr)
        GLES20.glDisableVertexAttribArray(aTextureCoordinateAttr)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0)
    }

    override fun onDrawFrame(gl: GL10?, mvpMatrix: FloatArray?) {
        super.onDrawFrame(gl, mvpMatrix)
    }

    override fun onSurfaceDestroyed() {
        super.onSurfaceDestroyed()
    }
}