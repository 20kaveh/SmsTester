package com.example.kaveh.smstester.ui.screen

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.kaveh.smstester.SmsSender
import com.example.kaveh.smstester.viewmodel.MainViewModel

@SuppressLint("SuspiciousIndentation")
@Composable
fun MyApp(
    context: Context,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = MainViewModel()
) {
    var phoneNumber by remember {
        mutableStateOf("")
    }
    var message by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = phoneNumber,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone
            ),
            placeholder = { Text(text = "09182712566", color = Color.Gray) },
            onValueChange = { textChange ->
                phoneNumber = textChange
            },
            maxLines = 1
        )
        Spacer(modifier = modifier.height(30.dp))
        OutlinedTextField(
            value = message,
            placeholder = { Text(text = "سلام خوبی", color = Color.Gray) },
            onValueChange = { textChange ->
                message = textChange
            },
            maxLines = 1
        )
        Spacer(modifier = modifier.height(10.dp))

        Button(modifier = Modifier
            .height(40.dp)
            .width(150.dp),

            onClick = {
                if (isValidPhoneNumber(phoneNumber)){
                     if (message==""){
                         Toast.makeText(context,"متن پیام شما خالی است ",Toast.LENGTH_SHORT).show()
                     }else{
                         Toast.makeText(context,"پیام شما ارسال شد",Toast.LENGTH_SHORT).show()
                         val smsSender = SmsSender()

                         smsSender.sendSms(phoneNumber = phoneNumber, message = message)
                         phoneNumber=""
                         message= ""
                     }




                }else{
                    Toast.makeText(context,"شماره تلفن نا درست است درخواست شما ارسال نمی شود",Toast.LENGTH_SHORT).show()

                }



            }) {
            Text(text = "ارسال")
        }
        Spacer(modifier = Modifier.height(30.dp))
        Button(modifier = Modifier
            .height(40.dp),
            onClick = {


                viewModel.enableAutoStartIntent(context =context)


            }) {
            Text(text = "اجازه فعالیت اپلیکیشن در پس زمینه گوشی")
        }
    }
}

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    val regex = """^(\+98|0)?9\d{9}$""".toRegex()

    return regex.matches(phoneNumber)
}