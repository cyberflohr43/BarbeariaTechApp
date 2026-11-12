package com.example.projeto_sistema_barbearia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape

@Composable
fun HomeScreen(nome: String, onBook: () -> Unit, onLogout: () -> Unit) {
    LazyColumn(
        Modifier.fillMaxSize().statusBarsPadding(),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text("Olá, $nome", style = MaterialTheme.typography.headlineMedium)
                    Text("Pronto para o próximo corte?", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                IconButton(onClick = onLogout) { Icon(Icons.AutoMirrored.Filled.ExitToApp, "Sair") }
            }
        }
        item { NextCard(FakeData.agendamentos.first(), onBook) }
        item { Text("Serviços", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp)) }
        items(FakeData.servicos) { ServiceCard(it) }
        item { Text("Barbeiros", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp)) }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                items(FakeData.barbeiros) { BarberCard(it) }
            }
        }
    }
}

@Composable
private fun NextCard(a: Agendamento, onBook: () -> Unit) {
    Surface(shape = RoundedCornerShape(18.dp), color = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary) {
        Column {
            PoleBand(8.dp)
            Column(Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text("Seu próximo horário", style = MaterialTheme.typography.labelLarge)
                Text(a.servico.nome, style = MaterialTheme.typography.headlineMedium)
                Text("${a.data} às ${a.hora}, com ${a.barbeiro.nome}")
                Spacer(Modifier.height(10.dp))
                Button(onClick = onBook, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary)) { Text("Agendar outro horário") }
            }
        }
    }
}

@Composable
private fun BarberCard(b: Barbeiro) {
    Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Column(Modifier.width(150.dp).padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Avatar(b.nome, 56.dp)
            Text(b.nome, style = MaterialTheme.typography.titleSmall, textAlign = TextAlign.Center)
            Text(b.especialidade, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}
