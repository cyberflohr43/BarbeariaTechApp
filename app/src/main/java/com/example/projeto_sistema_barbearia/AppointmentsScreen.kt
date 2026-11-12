package com.example.projeto_sistema_barbearia

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppointmentsScreen() {
    var tab by remember { mutableIntStateOf(0) }
    val proximos = setOf(Status.AGENDADO, Status.CONFIRMADO)
    val lista = FakeData.agendamentos.filter { (it.status in proximos) == (tab == 0) }

    Column(Modifier.fillMaxSize().statusBarsPadding()) {
        Text("Minha agenda", style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(20.dp, 20.dp, 20.dp, 8.dp))
        TabRow(selectedTabIndex = tab) {
            listOf("Próximos", "Histórico").forEachIndexed { i, t ->
                Tab(selected = tab == i, onClick = { tab = i }, text = { Text(t) })
            }
        }
        LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            if (lista.isEmpty()) item { Text("Nenhum horário por aqui. Que tal agendar um corte?") }
            items(lista) { AppointmentCard(it) }
        }
    }
}

@Composable
private fun AppointmentCard(a: Agendamento) {
    Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Avatar(a.barbeiro.nome)
            Spacer(Modifier.width(14.dp))
            Column(Modifier.weight(1f)) {
                Text(a.servico.nome, style = MaterialTheme.typography.titleMedium)
                Text("${a.data} às ${a.hora}", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("com ${a.barbeiro.nome}", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(6.dp)) {
                StatusChip(a.status)
                Text(brl(a.servico.preco), style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}
