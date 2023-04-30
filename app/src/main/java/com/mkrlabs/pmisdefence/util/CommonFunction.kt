package com.mkrlabs.pmisdefence.util

import android.content.Context
import android.widget.Toast
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.*

object CommonFunction {


    fun successToast(context: Context ,msg : String){
        Toasty.success(context,msg,Toasty.LENGTH_SHORT,true).show()
    }

     fun infoToast(context: Context ,msg : String){
        Toasty.info(context,msg,Toasty.LENGTH_SHORT,true).show()
    }

    fun errorToast(context: Context ,msg : String){
        Toasty.error(context,msg,Toasty.LENGTH_SHORT,true).show()
    }
    fun taskDateFormat(selectedDate: Long, response : (String)->Unit){
        val formatter = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
        val formattedDate = formatter.format(Date(selectedDate))
        response.invoke(formattedDate)
    }
}