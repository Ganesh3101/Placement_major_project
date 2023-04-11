package com.test.placement_major_project

import com.google.firebase.database.FirebaseDatabase
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
       var uid = FirebaseDatabase.getInstance().getReference()
       println(uid)
    }
}