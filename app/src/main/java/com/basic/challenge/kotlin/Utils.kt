package com.basic.challenge.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast

// This class has been created to write all the code that we will
// user more than one time.
class Utils {

    object showToast{
        operator fun invoke(ctx : Context, message : String){
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
        }
    }
}