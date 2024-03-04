package com.example.coffeecraft
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.get
import com.example.coffeecraft.R
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
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { context ->
                // Inflate XML layout
                LayoutInflater.from(context).inflate(R.layout.mainactivity, null)
            },
            update = { view ->
                // Access elements from the inflated view
                val editTextEmail = view.findViewById<EditText>(R.id.editTextEmail)
                val editTextPassword = view.findViewById<EditText>(R.id.editTextPassword)
                val buttonLogin = view.findViewById<Button>(R.id.buttonLogin)
                val textViewResult = view.findViewById<TextView>(R.id.textViewResult)

                // Set onClickListener for the button
                buttonLogin.setOnClickListener {
                    val email = editTextEmail.text.toString()
                    val password = editTextPassword.text.toString()
                    // Launching login process
                    login(email, password) { loginResult ->
                        textViewResult.text = loginResult
                    }
                }
            }
        )
    }
}

private fun login(email: String, password: String, onResult: (String) -> Unit) {
    // Simulating login process (replace with actual login logic)
    // For demonstration, just consider login successful
    val loginResult = "Login success $email"
    onResult(loginResult)
}
