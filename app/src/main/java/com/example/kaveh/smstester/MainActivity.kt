package com.example.kaveh.smstester

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.kaveh.smstester.navigation.Screen
import com.example.kaveh.smstester.navigation.SetupNavGraph
import com.example.kaveh.smstester.ui.screen.PermissionRequestScreen
import com.example.kaveh.smstester.viewmodel.MainViewModel
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest
import kotlin.properties.Delegates

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 100
    }

    val permissions = arrayOf(
        android.Manifest.permission.SEND_SMS,
        android.Manifest.permission.POST_NOTIFICATIONS,
        android.Manifest.permission.RECEIVE_SMS

    )

    private lateinit var navController: NavHostController

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val context = LocalContext.current

            navController = rememberNavController()
            SetupNavGraph(navController = navController, context)


            // بررسی اجازه‌ها قبل از استفاده از آن‌ها
            if (!checkPermissions()) {
                // اگر اجازه‌ها داده نشده باشند، درخواست آن‌ها
                PermissionRequestScreen(navHostController = navController, requestOnClick = {
                    if (checkPermissions()) {
                        navController.navigate(route = Screen.MyApp.route) {
                            popUpTo(Screen.PermissionScreen.route) {
                                inclusive = true
                            }
                        }
                    } else{
                        requestPermissions()
                    }
                })

            } else {
                navController.navigate(route = Screen.MyApp.route) {
                    popUpTo(Screen.PermissionScreen.route) {
                        inclusive = true
                    }

                }
            }


        }
    }

    // درخواست اجازه‌ها از کاربر
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_PERMISSIONS)
    }

    // بررسی اجازه‌ها
    private fun checkPermissions(): Boolean {
        var allGranted = true
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                allGranted = false
                break
            }
        }
        return allGranted
    }

    // پردازش نتیجه درخواست اجازه
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            var allGranted = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allGranted = false
                    break
                }
            }

            if (allGranted) {
                this.apply {
                    this.finish()
                    this.startActivity(Intent(this, MainActivity::class.java))
                }

                Toast.makeText(this, " اجازه‌ها داده شده است.", Toast.LENGTH_SHORT).show()



            } else {
                Toast.makeText(this, "برخی از اجازه‌ها رد شده است.", Toast.LENGTH_SHORT).show()
            }
        }
    }


}

