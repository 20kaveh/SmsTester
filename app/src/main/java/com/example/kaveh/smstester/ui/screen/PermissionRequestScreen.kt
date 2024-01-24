package com.example.kaveh.smstester.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun PermissionRequestScreen(
    navHostController: NavHostController,
    requestOnClick: () -> Unit,
    modifier: Modifier = Modifier,
    text :String="از Android 13 و بالاتر، برنامه ها برای نمایش اعلانها به مجوز اعلان نیاز دارند."
){
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = modifier.size(50.dp),
                imageVector = Icons.Default.Check,
                contentDescription = null)
            Text(text = "مجوز لازم است", fontSize = 30.sp)
            Spacer(modifier = modifier.height(12.dp))
            Box(
                modifier = modifier.align(Alignment.CenterHorizontally),
                contentAlignment = Alignment.Center
            ){
                Text(
                    modifier = modifier.padding(start = 20.dp, end = 20.dp),
                    text = text,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = modifier.height(16.dp))
            Button(onClick = {
                requestOnClick()
            }) {
                Text(text = "درخواست مجوز")
            }
        }
    }
}