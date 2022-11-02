package com.example.carparkingsystem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart()
    {
        super.onStart()
        print("onStart")
    }

    override fun onResume()
    {
        super.onResume()
        print("onResume")
    }

    override fun onPause()
    {
        super.onPause()
        print("onPause")
    }

    override fun onStop()
    {
        super.onStop()
        print("onStop")
    }

    override fun onRestart()
    {
        super.onRestart()
        print("onRestart")
    }

    override fun onDestroy()
    {
        super.onDestroy()
        print("onDestroy")
    }

    fun print(msg: String)
    {
        Log.d("Activity State ",msg)
    }
}