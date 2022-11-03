package com.zjl.learnopengles.util

import android.opengl.Matrix
import java.nio.ByteBuffer

/**
 * Project Name: LearnOpenGLES
 * ClassName:    MatrixState
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年10月31日 15:26
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
object MatrixState {
    private val mProjMatrix = FloatArray(16)

    private val mVMatrix = FloatArray(16)

    private var currMatrix: FloatArray = floatArrayOf()

    var lightLocation = floatArrayOf(0f, 0f, 0f)
    var cameraFB: FloatArray = floatArrayOf()
    var lightPositionFB: FloatArray = floatArrayOf()

    var mStack = Array(10) {
        FloatArray(
            16
        )
    } //用于保存变换矩阵的栈

    var stackTop = -1 //栈顶索引

    //获取具体物体的总变换矩阵
    var mMVPMatrix = FloatArray(16) //总变换矩阵

    //产生无任何变换的初始矩阵
    fun setInitStack() {
        currMatrix = FloatArray(16)
        Matrix.setRotateM(currMatrix, 0, 0f, 1f, 0f, 0f)
    }

    //将当前变换矩阵存入栈中
    fun pushMatrix() {
        stackTop++ //栈顶索引加1
        for (i in 0..15) {
            mStack[stackTop][i] = currMatrix[i] //当前变换矩阵中的各元素入栈
        }
    }

    //从栈顶取出变换矩阵
    fun popMatrix() {
        for (i in 0..15) {
            currMatrix[i] = mStack[stackTop][i] //栈顶矩阵元素进当前变换矩阵
        }
        stackTop-- //栈顶索引减1
    }

    //沿X、Y、Z轴方向进行平移变换的方法
    fun translate(x: Float, y: Float, z: Float) {
        Matrix.translateM(currMatrix, 0, x, y, z)
    }

    //沿X、Y、Z轴方向进行旋转变换的方法
    fun rotate(angle: Float, x: Float, y: Float, z: Float) //设置绕xyz轴移动
    {
        Matrix.rotateM(currMatrix, 0, angle, x, y, z)
    }

    //沿X、Y、Z轴方向进行放缩变换的方法
    fun scale(x: Float, y: Float, z: Float) {
        Matrix.scaleM(currMatrix, 0, x, y, z)
    }

    //设置摄像机
    var llbb: ByteBuffer = ByteBuffer.allocateDirect(3 * 4)
    var cameraLocation = FloatArray(3) //摄像机位置


}