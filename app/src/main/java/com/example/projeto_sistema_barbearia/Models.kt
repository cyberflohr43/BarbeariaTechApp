package com.example.projeto_sistema_barbearia

import java.util.Locale

// Espelham as tabelas do MySQL (servicos, barbeiros, agendamentos)
enum class Status(val label: String) {
    AGENDADO("Agendado"), CONFIRMADO("Confirmado"), CONCLUIDO("Concluído"), CANCELADO("Cancelado")
}
data class Servico(val id: Int, val nome: String, val descricao: String, val preco: Double, val duracaoMin: Int)
data class Barbeiro(val id: Int, val nome: String, val especialidade: String)
data class Agendamento(
    val id: Int, val servico: Servico, val barbeiro: Barbeiro,
    val data: String, val hora: String, val status: Status
)

fun brl(v: Double): String = String.format(Locale("pt", "BR"), "R$ %.2f", v)

// Dados fake: depois trocamos por Retrofit sem mexer nas telas
object FakeData {
    val servicos = listOf(
        Servico(1, "Corte Masculino", "Corte tradicional masculino", 35.0, 40),
        Servico(2, "Barba", "Modelagem completa da barba", 25.0, 30),
        Servico(3, "Corte + Barba", "Pacote completo", 55.0, 60)
    )
    val barbeiros = listOf(
        Barbeiro(1, "Marcos Barber", "Degradê e navalhado"),
        Barbeiro(2, "Lucas Fade", "Corte social")
    )
    val agendamentos = listOf(
        Agendamento(1, servicos[2], barbeiros[0], "10/05/2026", "14:00", Status.AGENDADO),
        Agendamento(2, servicos[0], barbeiros[1], "11/05/2026", "16:30", Status.CONFIRMADO),
        Agendamento(3, servicos[1], barbeiros[0], "02/05/2026", "10:00", Status.CONCLUIDO),
        Agendamento(4, servicos[0], barbeiros[1], "28/04/2026", "18:00", Status.CANCELADO)
    )
    val horarios = listOf("09:00", "09:40", "10:20", "11:00", "14:00", "14:40", "15:20", "16:00", "16:40", "17:20")
}
