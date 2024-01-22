package com.example.kaveh.smstester

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.kaveh.smstester.ui.theme.SmsTesterTheme
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

const val SMS_PERMISSION_REQUEST_CODE = 123

class MainActivity : ComponentActivity(), EasyPermissions.PermissionCallbacks {
    private var smsPermission = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmsTesterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("sent", smsPermission)
                    requestSmsPermissions()
                }
            }
        }
    }



    private fun requestSmsPermissions() {
        val permissions = arrayOf(
            android.Manifest.permission.SEND_SMS,
            android.Manifest.permission.RECEIVE_SMS

        )

        if (EasyPermissions.hasPermissions(this, *permissions)) {
            // اجازه‌ها دارای مجوز است، می‌توانید عملیات مربوط به SMS را انجام دهید.
            smsPermission = true
        } else {
            // اجازه‌ها دارای مجوز نیستند، درخواست مجوز از کاربر.
            EasyPermissions.requestPermissions(
                PermissionRequest.Builder(this, SMS_PERMISSION_REQUEST_CODE, *permissions)
                    .setRationale("برای ارسال و دریافت پیام‌ها اجازه‌ی دسترسی به پیام‌ها لازم است.")
                    .setPositiveButtonText("تایید")
                    .setNegativeButtonText("لغو")
                    .build()
            )
        }
    }


    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

        // اجازه‌های لازم درخواست شده و توسط کاربر تایید شده‌اند.
        // اینجا می‌توانید عملیات مربوط به SMS را انجام دهید.
        smsPermission = true
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        // کاربر اجازه‌ها را تایید نکرده است.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            // کاربر دائماً اجازه‌ها را رد کرده است. می‌توانید او را به تنظیمات دستگاه هدایت کنید.
//            EasyPermissions.goToSettings(this)
        } else {

            // کاربر به طور موقت اجازه‌ها را رد کرده است.
            // در اینجا می‌توانید اطلاعات بیشتری به کاربر ارائه دهید.
        }
    }


@Composable
fun Greeting(name: String, smsPermission: Boolean) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(modifier = Modifier
            .height(40.dp)
            .width(150.dp),

            onClick = {


                val smsSender = SmsSender()
                if (smsPermission){

                    smsSender.sendSms(phoneNumber = "+989182712566", message = "hello android")
                }
                else{
                    requestSmsPermissions()
                }
            }) {
            Text(text = name)
        }
    }

    }
}

