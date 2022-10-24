package com.zjl.learnopengles

import android.annotation.SuppressLint
import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.Renderer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zjl.learnopengles.draw.Circle
import com.zjl.learnopengles.draw.Line
import com.zjl.learnopengles.draw.Point
import com.zjl.learnopengles.draw.Triangle
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var point: Point? = null
        var line: Line? = null
        var triangle: Triangle? = null
        var circle: Circle? = null
        findViewById<GLSurfaceView>(R.id.v_gl_surface).apply {
            setEGLContextClientVersion(2)
            setRenderer(object : Renderer {
                override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
                    //point = Point(this@MainActivity)
                    //line = Line(this@MainActivity)
                    //triangle = Triangle(this@MainActivity)
                    circle = Circle(this@MainActivity)
                    circle?.onSurfaceCreated(gl, config)
                }

                override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
                    circle?.onSurfaceChanged(gl, width, height)
                }

                override fun onDrawFrame(gl: GL10?) {
                    circle?.onDrawFrame(gl)
                }

            })
            renderMode = GLSurfaceView.RENDERMODE_WHEN_DIRTY
        }
    }
}