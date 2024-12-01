package com.rupak.analyticsdemo.model.local

import android.annotation.SuppressLint
import java.time.Instant
import java.util.Date

data class Todo(
    var id:Int = System.currentTimeMillis().toInt(),
    var title:String,
    @SuppressLint("NewApi") var createdAt: Date= Date.from(Instant.now()),
)


fun getFakeTodo():List<Todo>{
    return listOf<Todo>(
     Todo(1,"1st Todo" ),
     Todo(2,"Wash clothes"),
     Todo(3,"Study"),
     Todo(4,"Make Dinner"),
     Todo(5,"Workout"),
    )
}
