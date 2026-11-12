package com.example.projeto_sistema_barbearia

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle as DayStyle
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookingScreen(onConfirmed: () -> Unit) {
    var servico by remember { mutableStateOf<Servico?>(null) }
    var barbeiro by remember { mutableStateOf<Barbeiro?>(null) }
    val dias = remember { (0L..6L).map { LocalDate.now().plusDays(it) } }
    var dia by remember { mutableStateOf(dias[0]) }
    var hora by remember { mutableStateOf<String?>(null) }
    var dialog by remember { mutableStateOf(false) }
    val pronto = servico != null && barbeiro != null && hora != null

    Column(Modifier.fillMaxSize().statusBarsPadding()) {
        Column(
            Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Agendar horário", style = MaterialTheme.typography.headlineMedium)

            Text("1. Serviço", style = MaterialTheme.typography.titleLarge)
            FakeData.servicos.forEach { s -> ServiceCard(s, selected = servico == s) { servico = s } }

            Text("2. Barbeiro", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FakeData.barbeiros.forEach { b ->
                    FilterChip(selected = barbeiro == b, onClick = { barbeiro = b }, label = { Text(b.nome) })
                }
            }

            Text("3. Dia", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))
            Row(Modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                dias.forEach { d ->
                    val nome = d.dayOfWeek.getDisplayName(DayStyle.SHORT, Locale("pt", "BR"))
                    FilterChip(selected = dia == d, onClick = { dia = d }, label = { Text("$nome ${d.dayOfMonth}") })
                }
            }

            Text("4. Horário", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(top = 8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FakeData.horarios.forEach { h ->
                    FilterChip(selected = hora == h, onClick = { hora = h }, label = { Text(h) })
                }
            }
        }
        Surface(tonalElevation = 3.dp) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                servico?.let {
                    Row {
                        Text(it.nome, Modifier.weight(1f))
                        Text(brl(it.preco), style = MaterialTheme.typography.titleMedium)
                    }
                }
                MainButton("Confirmar agendamento", { dialog = true }, enabled = pronto)
            }
        }
    }

    if (dialog) {
        AlertDialog(
            onDismissRequest = { dialog = false },
            title = { Text("Horário reservado") },
            text = { Text("${servico?.nome} com ${barbeiro?.nome}, dia ${dia.dayOfMonth}/${dia.monthValue} às $hora.") },
            confirmButton = { TextButton(onClick = { dialog = false; onConfirmed() }) { Text("Ver minha agenda") } }
        )
    }
}
