package com.ayberk.composeapp


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun register(navHostController:NavHostController){

    var password by remember { mutableStateOf("") }
    var passwordagain by remember { mutableStateOf("") }
    var isPasswordValid by remember { mutableStateOf(true) }
    var isShowingPasswordError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image( painter = painterResource(id = R.drawable.changepassword),
            contentDescription = "Şifre Değişikliği",
            modifier = Modifier.size(100.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))
        
        OutlinedTextField(value = password,
            onValueChange = {
                password = it
            }, label = { Text(text = stringResource(id = R.string.password)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(bottom = 8.dp),
            leadingIcon = { // İşte burada sol tarafına simge (ikon) ekliyoruz
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            }
        )

        OutlinedTextField(value = passwordagain,
            onValueChange = {
                passwordagain = it
            }, label = { Text(text = stringResource(id = R.string.passwordagain)) },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(bottom = 8.dp),
            leadingIcon = { // İşte burada sol tarafına simge (ikon) ekliyoruz
                Icon(imageVector = Icons.Default.Lock, contentDescription = null)
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            isPasswordValid = isValidPassword(password)

            if (isPasswordValid && password == passwordagain) {
                navHostController.navigate("login")
            } else {
                isShowingPasswordError = true
            }
        }) {
            Text(text = stringResource(id = R.string.passwordchange))
        }

        if (!isPasswordValid || isShowingPasswordError) {
            if (!isPasswordValid) {
                Text(
                    text = "Şifre uzunluğu 5 karakterden fazla olmalıdır.",
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

            } else if (isShowingPasswordError) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = "Şifreler uyuşmuyor.",
                    color = Color.Red,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }
    }
}

private fun isValidPassword(password: CharSequence): Boolean {
    return password.length >= 5 // Şifrenin en az 5 karakter olmasını isteyelim.
}