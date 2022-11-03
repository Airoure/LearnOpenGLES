package com.zjl.icehocky

import android.app.ActivityManager
import android.content.Context
import android.opengl.GLSurfaceView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var glSurfaceView: GLSurfaceView? = null

    private var renderSet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glSurfaceView = GLSurfaceView(this)

        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val configurationInfo = activityManager.deviceConfigurationInfo
        val supportE2 = configurationInfo.reqGlEsVersion >= 0x20000

        if (supportE2) {
            glSurfaceView?.setEGLContextClientVersion(2)
            glSurfaceView?.setRenderer(MyRenderer3(this))
            renderSet = true
        } else {
            Toast.makeText(this, "not support", Toast.LENGTH_SHORT).show()
        }
        setContentView(glSurfaceView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_menu, menu)
        return true
    }

    override fun onPause() {
        super.onPause()
        if (renderSet) {
            glSurfaceView?.onPause()
        }
    }

    override fun onResume() {
        super.onResume()
        if (renderSet) {
            glSurfaceView?.onResume()
        }
    }
}