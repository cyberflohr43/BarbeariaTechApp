package com.example.projeto_sistema_barbearia

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCut
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BarbeariaTheme {
                Surface(color = MaterialTheme.colorScheme.background) { AppNav() }
            }
        }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()
    NavHost(nav, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onLogin = { nav.navigate("main") { popUpTo("login") { inclusive = true } } },
                onRegister = { nav.navigate("register") })
        }
        composable("register") {
            RegisterScreen(onDone = { nav.popBackStack() }, onBack = { nav.popBackStack() })
        }
        composable("main") {
            MainScreen(onLogout = { nav.navigate("login") { popUpTo("main") { inclusive = true } } })
        }
    }
}

@Composable
fun MainScreen(onLogout: () -> Unit) {
    var tab by rememberSaveable { mutableIntStateOf(0) }
    val itens = listOf("Início" to Icons.Filled.Home, "Agendar" to Icons.Filled.ContentCut, "Agenda" to Icons.Filled.Event)
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            NavigationBar {
                itens.forEachIndexed { i, (label, icon) ->
                    NavigationBarItem(selected = tab == i, onClick = { tab = i },
                        icon = { Icon(icon, label) }, label = { Text(label) })
                }
            }
        }
    ) { pad ->
        Box(Modifier.padding(pad)) {
            when (tab) {
                0 -> HomeScreen("Victhor", onBook = { tab = 1 }, onLogout = onLogout)
                1 -> BookingScreen(onConfirmed = { tab = 2 })
                else -> AppointmentsScreen()
            }
        }
    }
}
