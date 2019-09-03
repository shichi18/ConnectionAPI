package com.github.shichi18.connectionapi

import android.os.AsyncTask

class AsyncConnect : AsyncTask<String, Int, Boolean>() {


    override fun doInBackground(vararg params: String?): Boolean {
        println("test")

        return false

    }
}
