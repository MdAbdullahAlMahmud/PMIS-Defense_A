package com.mkrlabs.pmisdefence.util

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty

object CommonFunction {


    fun successToast(context: Context ,msg : String){
        Toasty.success(context,msg,Toasty.LENGTH_SHORT,true).show()
    }

    fun errorToast(context: Context ,msg : String){
        Toasty.error(context,msg,Toasty.LENGTH_SHORT,true).show()
    }
}