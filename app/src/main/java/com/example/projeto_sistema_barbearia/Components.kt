package com.example.projeto_sistema_barbearia

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/** Faixa listrada de poste de barbeiro: o detalhe marcante do app. */
@Composable
fun PoleBand(height: Dp = 10.dp) {
    val brush = Brush.linearGradient(
        colorStops = arrayOf(0f to Pole, 0.33f to Pole, 0.33f to Color.White, 0.66f to Color.White, 0.66f to PoleBlue, 1f to PoleBlue),
        start = Offset(0f, 0f), end = Offset(36f, 36f), tileMode = TileMode.Repeated
    )
    Box(Modifier.fillMaxWidth().height(height).background(brush))
}

@Composable
fun Avatar(name: String, size: Dp = 48.dp) {
    Box(
        Modifier.size(size).clip(CircleShape).background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        Text(name.split(" ").take(2).map { it.first() }.joinToString(""),
            style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatusChip(s: Status) {
    val (bg, fg) = when (s) {
        Status.AGENDADO -> Color(0xFFFFF1D6) to Color(0xFF7A5200)
        Status.CONFIRMADO -> Color(0xFFDDF3E4) to Color(0xFF1B6B3A)
        Status.CONCLUIDO -> Color(0xFFE1E8F2) to Color(0xFF34476B)
        Status.CANCELADO -> Color(0xFFFBE0DD) to Color(0xFF9B2A20)
    }
    Surface(color = bg, shape = RoundedCornerShape(50)) {
        Text(s.label, Modifier.padding(horizontal = 10.dp, vertical = 4.dp), color = fg, style = MaterialTheme.typography.labelMedium)
    }
}

@Composable
fun ServiceCard(s: Servico, selected: Boolean = false, onClick: (() -> Unit)? = null) {
    val shape = RoundedCornerShape(14.dp)
    val border = if (selected) BorderStroke(2.dp, MaterialTheme.colorScheme.secondary)
    else BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    Surface(shape = shape, color = MaterialTheme.colorScheme.surface, border = border,
        modifier = Modifier.fillMaxWidth().clip(shape).let { if (onClick != null) it.clickable(onClick = onClick) else it }) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text(s.nome, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
                Text(s.descricao, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("${s.duracaoMin} min", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(brl(s.preco), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun Field(value: String, onChange: (String) -> Unit, label: String, keyboard: KeyboardType = KeyboardType.Text, password: Boolean = false) {
    var show by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value, onValueChange = onChange, modifier = Modifier.fillMaxWidth(),
        label = { Text(label) }, singleLine = true, shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = if (password) KeyboardType.Password else keyboard),
        visualTransformation = if (password && !show) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = if (password) {
            { IconButton(onClick = { show = !show }) {
                Icon(if (show) Icons.Filled.VisibilityOff else Icons.Filled.Visibility, "Mostrar senha")
            } }
        } else null
    )
}

@Composable
fun MainButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(onClick = onClick, enabled = enabled, modifier = Modifier.fillMaxWidth().height(52.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary)) {
        Text(text, fontWeight = FontWeight.SemiBold)
    }
}
