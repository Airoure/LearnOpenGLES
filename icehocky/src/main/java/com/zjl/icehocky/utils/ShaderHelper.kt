package com.zjl.icehocky.utils

import android.opengl.GLES20.*
import android.util.Log

/**
 * Project Name: LearnOpenGLES
 * ClassName:    ShaderHelper
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月02日 10:49
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
object ShaderHelper {

    fun buildProgram(vertexShaderSource: String, fragmentShaderSource: String): Int {
        var program: Int
        val vertexShader = compileVertexShader(vertexShaderSource)
        val fragmentShader = compileFragmentShader(fragmentShaderSource)

        program = linkProgram(vertexShader, fragmentShader)
        validateProgram(program)

        return program
    }

    fun linkProgram(vertexShaderId: Int, fragmentShaderId: Int): Int {
        val programObjId = glCreateProgram()
        if (programObjId == 0) {
            Log.e("ShaderHelper", "create program failed")
            return 0
        }
        glAttachShader(programObjId, vertexShaderId)
        glAttachShader(programObjId, fragmentShaderId)

        glLinkProgram(programObjId)
        val linkStatus = IntArray(1)
        glGetProgramiv(programObjId, GL_LINK_STATUS, linkStatus, 0)
        if (linkStatus[0] == 0) {
            Log.e("ShaderHelper", "link program failed")
            return 0
        }
        return programObjId
    }

    fun compileVertexShader(shaderCode: String): Int {
        return compileShader(GL_VERTEX_SHADER, shaderCode)
    }

    fun compileFragmentShader(shaderCode: String): Int {
        return compileShader(GL_FRAGMENT_SHADER, shaderCode)
    }

    fun validateProgram(programId: Int): Boolean {
        glValidateProgram(programId)

        val validateStatus = IntArray(1)
        glGetProgramiv(programId, GL_VALIDATE_STATUS, validateStatus, 0)
        if (validateStatus[0] == 0) {
            Log.e("ShaderHelper", "validate program failed")
        }
        return validateStatus[0] != 0
    }

    private fun compileShader(type: Int, shaderCode: String): Int {
        val shaderObjId = glCreateShader(type)
        if (type == 0) {
            Log.e("ShaderHelper", "create shader failed")
            return 0
        }
        glShaderSource(shaderObjId, shaderCode)
        glCompileShader(shaderObjId)
        val compileStatus = IntArray(1)
        glGetShaderiv(shaderObjId, GL_COMPILE_STATUS, compileStatus, 0)
        if (compileStatus[0] == 0) {
            val tip = if (type == GL_VERTEX_SHADER) "compile vertexShader failed"  else "compile fragmentShader failed"
            Log.e("ShaderHelper", tip)
            glDeleteShader(shaderObjId)
        }
        return shaderObjId
    }
}