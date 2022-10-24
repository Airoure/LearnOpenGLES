package com.zjl.learnopengles.util

import android.content.Context
import android.opengl.GLES20
import android.util.Log




/**
 * Project Name: LearnOpenGLES
 * ClassName:    ShaderHelper
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年10月24日 11:13
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
object ShaderHelper {

    const val TAG = "ShaderHelper"

    // 编译顶点着色器
    fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_VERTEX_SHADER, shaderCode)
    }

    // 编译片元着色器
    fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GLES20.GL_FRAGMENT_SHADER, shaderCode)
    }

    // 根据类型编译着色器
    private fun compileShader(type: Int, shaderCode: String): Int { // glCreateShader -> glShaderSource -> glCompileShader -> glGetShaderiv ?-> glDeleteShader
        val shaderObjId = GLES20.glCreateShader(type)
        if (shaderObjId == 0) {
            // 创建失败
            Log.e("error", "create shader failed")
            return 0
        }

        GLES20.glShaderSource(shaderObjId, shaderCode)
        GLES20.glCompileShader(shaderObjId)
        val compileStatus = intArrayOf(0)
        GLES20.glGetShaderiv(shaderObjId, GLES20.GL_COMPILE_STATUS, compileStatus, 0)

        if (compileStatus[0] == 0) {
            // 编译失败
            GLES20.glDeleteShader(shaderObjId)
            Log.e("error", "compilation of shader failed")
            return 0
        }

        return shaderObjId
    }

    // 链接着色器
    private fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programObjId = GLES20.glCreateProgram()
        if (programObjId == 0) {
            // 创建失败
            Log.e("error", "Could not create new program")
            return 0
        }

        // 关联着色器
        GLES20.glAttachShader(programObjId, vertexShaderId)
        GLES20.glAttachShader(programObjId, fragmentShaderId)
        GLES20.glLinkProgram(programObjId)
        val linkStatus = intArrayOf(0)
        GLES20.glGetProgramiv(programObjId, GLES20.GL_LINK_STATUS, linkStatus, 0)

        Log.e("result", GLES20.glGetProgramInfoLog(programObjId))

        if (linkStatus[0] == 0) {
            GLES20.glDeleteShader(programObjId)
            Log.e("error", "linking program failed")
            return 0;
        }
        return programObjId
    }

    private fun validateProgram(programObjId: Int): Boolean {
        GLES20.glValidateProgram(programObjId)
        val validateStatus = intArrayOf(0)
        GLES20.glGetProgramiv(programObjId, GLES20.GL_VALIDATE_STATUS, validateStatus, 0)

        return validateStatus[0] != 0
    }

    private fun buildProgram(vertexShaderSource: String, fragmentShaderSource: String): Int {
        val vertexShaderId = compileVertexShader(vertexShaderSource)
        val fragmentShaderId = compileFragmentShader(fragmentShaderSource)

        val program = linkProgram(vertexShaderId, fragmentShaderId)
        validateProgram(program)
        return program
    }

    fun buildProgram(context: Context?, vertexShaderSource: Int, fragmentShaderSource: Int): Int {
        val vertexString: String =
            TextResourceReader.readTextFileFromResource(context, vertexShaderSource)
        val textureString: String =
            TextResourceReader.readTextFileFromResource(context, fragmentShaderSource)
        return buildProgram(vertexString, textureString)
    }

    fun buildProgramFromAssetFile(
        context: Context?,
        vertexFileName: String?,
        fragmentFileName: String?
    ): Int {
        val vertexString: String = TextResourceReader.readTextFileFromAsset(context, vertexFileName)
        val fragmentString: String =
            TextResourceReader.readTextFileFromAsset(context, fragmentFileName)
        Log.d("info","vertex is $vertexString frag is $fragmentString")
        return buildProgram(vertexString, fragmentString)
    }
}