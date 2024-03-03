package com.example.coffeecraft

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coffeecraft.ui.theme.CoffeeCraftTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoffeeCraftTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LoginScreen()
                }
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.padding(16.dp)
        )
        Button(
            onClick = {
                // Launching login process
                login(email, password) { loginResult ->
                    result = loginResult
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Log In")
        }
        Text(
            text = result,
            modifier = Modifier.padding(16.dp)
        )
    }
}

private fun login(email: String, password: String, onResult: (String) -> Unit) {
    // Simulating login process (replace with actual login logic)
    // Launching a coroutine to simulate background work
    // Replace this with your actual login logic
    CoroutineScope(Dispatchers.IO).launch {
        delay(2000) // Simulating network delay or heavy processing
        val loginResult = "Login success $email"
        withContext(Dispatchers.Main) {
            onResult(loginResult)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoffeeCraftTheme {
        LoginScreen()
    }
}
