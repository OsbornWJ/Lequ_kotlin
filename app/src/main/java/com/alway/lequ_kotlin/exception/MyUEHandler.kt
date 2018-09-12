package com.alway.lequ_kotlin.exception

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import com.alway.lequ_kotlin.MainActivity
import com.alway.lequ_kotlin.app.LeQuApp
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.text.SimpleDateFormat

class MyUEHandler(internal var softApp: LeQuApp) : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        // fetch Excpetion Info
        Log.e(this.javaClass.name, thread.name)
        Log.e(this.javaClass.name, ex.toString())
        var info: String? = null
        var baos: ByteArrayOutputStream? = null
        var printStream: PrintStream? = null
        try {
            baos = ByteArrayOutputStream()
            printStream = PrintStream(baos)
            ex.printStackTrace(printStream)
            var data: ByteArray? = baos.toByteArray()
            info = String(data!!)
            data = null
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (printStream != null) {
                    printStream.close()
                }
                if (baos != null) {
                    baos.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
        // print
        val threadId = thread.id
        Log.d("texts", "Thread.getName()=" + thread.name + " id="
                + threadId + " state=" + thread.state)
        Log.d("texts", "Error[$info]")
        if (threadId != 1L) {
            // 此处示例跳转到汇报异常界面。
            // Intent intent = new Intent(softApp, ActErrorReport.class);
            // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            // intent.putExtra("error", info);
            // intent.putExtra("by", "uehandler");
            // softApp.startActivity(intent);
        } else {
            // 此处示例发生异常后，重新启动应用
            val intent = Intent(softApp, MainActivity::class.java)
            // 如果<span
            // style="background-color: rgb(255, 255, 255); ">没有NEW_TASK标识且</span>是UI线程抛的异常则界面卡死直到ANR
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            softApp.startActivity(intent)
            // write 2 /data/data/<app_package>/files/error.log
            write2ErrorLog(info)
            // kill App Progress
            android.os.Process.killProcess(android.os.Process.myPid())
        }

    }

    @SuppressLint("SimpleDateFormat")
    private fun write2ErrorLog(content: String?) {
        var fos: FileOutputStream? = null
        try {
            if (!LeQuApp.PATH_ERROR_LOG.exists())
                LeQuApp.PATH_ERROR_LOG.mkdirs()
            val formart = SimpleDateFormat(
                    "yyyy-MM-dd-HH:mm:ss")
            val fileName = formart.format(System.currentTimeMillis())
            val file = File(LeQuApp.PATH_ERROR_LOG, "exp"
                    + fileName + ".txt")
            fos = FileOutputStream(file)
            fos!!.write(content!!.toByteArray())
            fos.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (fos != null) {
                    fos.close()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

}
