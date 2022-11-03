package com.zjl.icehocky.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Project Name: LearnOpenGLES
 * ClassName:    TextResourceReader
 *
 * Description:
 *
 * @author  zjl
 * @date    2022年11月02日 10:29
 *
 * Copyright (c) 2022年, 4399 Network CO.ltd. All Rights Reserved.
 */
object TextResourceReader {

    @JvmStatic
    fun readTextFileFromResource(context: Context, resourceId: Int): String {

        val body = StringBuilder()
        try {
            val inputStream = context.resources.openRawResource(resourceId)

            val inputStreamReader = InputStreamReader(inputStream)

            val bufferReader = BufferedReader(inputStreamReader)

            var nextLine: String? = bufferReader.readLine()
            while (nextLine != null) {
                body.append(nextLine)
                body.append("\n")
                nextLine = bufferReader.readLine()
            }
        } catch (e: Exception) {

        }
        return body.toString()
    }
}