package com.example.advweek4.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.advweek4.model.Student
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val studentLD = MutableLiveData<Student>()
    private var queue: RequestQueue? = null
    fun fetch(studentId: String){

        queue = Volley.newRequestQueue(getApplication())
        var url = "http://adv.jitusolution.com/student.php?id=$studentId"

        val stringRequest = StringRequest(
            Request.Method.GET, url,{
                val sType = object : TypeToken<Student>() {}.type
                val result = Gson().fromJson<Student>(it,sType)
                studentLD.value = result as Student?
                Log.d("showvoley", result.toString())
            },{
                Log.d("showvoley", it.toString())
//                studentLoadErrorLD.value = true
//                loadingLD.value = false
            }
        )

        queue?.add(stringRequest)
//        println(studentLD)
//        val student1 = Student("16055", "Nonie", "1998/03/28","5718444778","http://dummyimage.com/75x100" +
//                ".jpg/cc0000/ffffff")
//        studentLD.value = student1
    }
}