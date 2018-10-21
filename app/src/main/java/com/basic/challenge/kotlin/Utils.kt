package com.basic.challenge.kotlin

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.support.v4.content.ContextCompat
import android.support.v4.content.ContextCompat.startActivity
import android.widget.Toast
import com.marcoscg.licenser.Library
import com.marcoscg.licenser.License
import com.marcoscg.licenser.LicenserDialog

// This class has been created to write all the code that we will
// user more than one time.
class Utils {

    // A method to show a simple Toast.
    object showToast {
        operator fun invoke(ctx: Context, message: String) {
            Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show()
        }
    }

    // A method to ask for permisions.
    object askForPermissions {
        operator fun invoke(permission: String, requestCode: Int?, ctx: Context, activity: Activity) {
            if (ContextCompat.checkSelfPermission(ctx, permission) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode!!)
                } else {
                    ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode!!)
                }
            } else {
            }
        }
    }

    // A method to show a simple dialog with information related to all libraries used in this app.
    object showLicenseDialog {
        operator fun invoke(ctx: Context) {
            LicenserDialog(ctx)
                    .setTitle("Licenses")
                    .setBackgroundColor(Color.WHITE)
                    .setLibrary(Library("Android Support Libraries",
                            "https://developer.android.com/topic/libraries/support-library/index.html",
                            License.APACHE))
                    .setLibrary(Library("Retrofit",
                            "https://square.github.io/retrofit/",
                            License.APACHE))
                    .setLibrary(Library("RoundedImageView",
                            "https://github.com/vinc3m1/RoundedImageView",
                            License.APACHE))
                    .setLibrary(Library("Picasso",
                            "http://square.github.io/picasso/",
                            License.APACHE))
                    .setLibrary(Library("Licenser",
                            "http://square.github.io/picasso/",
                            License.MIT))
                    .setPositiveButton(android.R.string.ok) { dialogInterface, i ->
                    }
                    .show()
        }
    }
}