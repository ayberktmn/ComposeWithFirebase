package com.ayberk.composeapp

import android.app.Activity
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ayberk.composeapp.viewmodel.RegisterViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import com.ayberk.composeapp.data.User
import com.ayberk.composeapp.util.Resource
import com.ayberk.composeapp.viewmodel.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun login(navHostController: NavHostController, viewModel: RegisterViewModel = hiltViewModel(), viewLoginModel: LoginViewModel = hiltViewModel()){

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isEmailValid by remember { mutableStateOf(true) }
        var isPasswordValid by remember { mutableStateOf(true) }
        var checkedState by remember { mutableStateOf(false) }
        var isShowingEmailError by remember { mutableStateOf(false) }
        var isShowingPasswordError by remember { mutableStateOf(false) }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.user),
                        contentDescription = "Kullanıcı Resim",
                        modifier = Modifier.size(100.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = email)

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(value = email,
                        onValueChange = {
                            email = it
                        }, label = { Text(text = stringResource(id = R.string.email)) },
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(bottom = 8.dp),
                        leadingIcon = { // İşte burada sol tarafına simge (ikon) ekliyoruz
                            Icon(imageVector = Icons.Default.Email, contentDescription = null)
                        }
                    )

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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .padding(bottom = 8.dp)
                    ) {
                        Checkbox(
                            checked = checkedState,
                            onCheckedChange = { isChecked ->
                                checkedState = isChecked
                                // Burada işaret değiştiğinde yapılması gereken işlemleri ekleyebilirsiniz.
                                // savePassword(password)
                            }
                        )
                        Text(
                            text = "Beni Hatırla"
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 40.dp)
                        ) {
                            Text(
                                text = "Şifremi unuttum",
                                modifier = Modifier
                                    .background(Color.White)
                                    .clickable {
                                        navHostController.navigate("register")
                                    }
                            )
                            Spacer(
                                modifier = Modifier
                                    .background(Color.Gray)
                                    .height(1.dp)
                                    .width(103.dp)
                                    .align(Alignment.BottomStart) // Çizgiyi yazının altına hizalar
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            isEmailValid = isValidEmail(email)
                            isPasswordValid = isValidPassword(password)

                            if (isEmailValid && isPasswordValid) {
                                viewLoginModel.login(email, password)
                            } else {
                                isShowingEmailError = true
                                isShowingPasswordError = true
                            }
                        },
                        enabled = email.isNotBlank() && password.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(bottom = 16.dp)
                    ) {
                        Text("Giriş Yap")
                    }

// Daha sonra LoginViewModel'den gelen değeri dinleyerek işlemi kontrol edebilirsiniz.
                    LaunchedEffect(Unit) {
                        viewLoginModel.login.collect() { resource ->
                            when (resource) {
                                is Resource.Loading -> {
                                    // Yükleme durumunu burada kontrol et
                                }

                                is Resource.Success -> {
                                    // Giriş başarılı olduğunda yapılacak işlemler burada
                                    navHostController.navigate("anasayfa")
                                }

                                is Resource.Error -> {
                                    // Hata durumunu burada kontrol et
                                    // resource.message ile hata mesajına erişebilirsiniz: resource.message


                                }

                                else -> {}
                            }
                        }
                    }


                    Button(
                        onClick = {
                            isEmailValid = isValidEmail(email) // Email kontrolü burada yapılıyor
                            isPasswordValid = isValidPassword(password)

                            if (isEmailValid && isPasswordValid) {
                                saveEmail(email)
                            } else {
                                isShowingEmailError = true
                                isShowingPasswordError = true
                            }
                            val user = User(email)
                            val password = password
                            viewModel.createEmailandPassword(user, password)
                        },
                        enabled = email.isNotBlank() && password.isNotBlank(),
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .padding(bottom = 16.dp)
                    ) {
                        Text("Kayıt Ol")

                    }

                    if (!isEmailValid && isShowingEmailError) {
                        Text(
                            text = "Lütfen geçerli bir e-posta adresi giriniz.",
                            color = Color.Red,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    } else if (!isPasswordValid && isShowingPasswordError) {
                        Text(
                            text = "Şifre uzunluğu 5 karakterden fazla olmalıdır.",
                            color = Color.Red,
                            fontSize = 18.sp,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }
                }
       }


fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

private fun isValidPassword(password: CharSequence): Boolean {
    return password.length >= 5 // Şifrenin en az 5 karakter olmasını isteyelim.
}

var savedPassword: String? = null

fun savePassword(password: String) {
    savedPassword = password
    println("Şifre kaydedildi: $password")
}
var saveEmail: String? = null

fun saveEmail(email: String) {
    saveEmail = email
    println("email kaydedildi: $email")
}
