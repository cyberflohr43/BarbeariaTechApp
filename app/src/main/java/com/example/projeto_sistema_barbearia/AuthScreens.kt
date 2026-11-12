package com.example.projeto_sistema_barbearia
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onLogin: () -> Unit, onRegister: () -> Unit) {
    var user by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().statusBarsPadding()) {
        PoleBand(14.dp)
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp, androidx.compose.ui.Alignment.CenterVertically)
        ) {
            Text("Barbearia Techh", style = MaterialTheme.typography.headlineLarge)
            Text("Entre para agendar seu horário.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(10.dp))
            Field(user, { user = it }, "E-mail ou telefone")
            Field(pass, { pass = it }, "Senha", password = true)
            Spacer(Modifier.height(6.dp))
            MainButton("Entrar", onLogin)
            TextButton(onClick = onRegister, modifier = Modifier.fillMaxWidth()) { Text("Criar conta") }
        }
    }
}

@Composable
fun RegisterScreen(onDone: () -> Unit, onBack: () -> Unit) {
    var nome by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var tel by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().statusBarsPadding()) {
        PoleBand(14.dp)
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text("Criar conta", style = MaterialTheme.typography.headlineLarge)
            Text("Leva menos de um minuto.", color = MaterialTheme.colorScheme.onSurfaceVariant)
            Field(nome, { nome = it }, "Nome completo")
            Field(cpf, { cpf = it }, "CPF", KeyboardType.Number)
            Field(tel, { tel = it }, "Telefone", KeyboardType.Phone)
            Field(email, { email = it }, "E-mail", KeyboardType.Email)
            Field(pass, { pass = it }, "Senha", password = true)
            Spacer(Modifier.height(6.dp))
            MainButton("Criar conta", onDone)
            TextButton(onClick = onBack, modifier = Modifier.fillMaxWidth()) { Text("Já tenho conta") }
        }
    }
}
