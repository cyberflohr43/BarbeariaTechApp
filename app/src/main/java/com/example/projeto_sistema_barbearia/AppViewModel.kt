package com.example.projeto_sistema_barbearia

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import java.io.IOException

class AppViewModel : ViewModel() {
    var nomeLogado by mutableStateOf("")
        private set
    var carregando by mutableStateOf(false)
        private set
    var erro by mutableStateOf<String?>(null)
        private set
    var aviso by mutableStateOf<String?>(null)
        private set
    var barbeiros by mutableStateOf(FakeData.barbeiros)   // dados de exemplo até a API responder
        private set
    var barbeirosErro by mutableStateOf(false)
        private set

    fun limparMensagens() { erro = null; aviso = null }

    fun login(usuario: String, senha: String, onSucesso: () -> Unit) = executar {
        val r = Api.service.login(LoginRequest(usuario.trim(), senha))
        if (r.isSuccessful) {
            nomeLogado = r.body()?.nome ?: "cliente"
            aviso = null
            carregarBarbeiros()
            onSucesso()
        } else {
            erro = mensagemDeErro(r.errorBody()?.string(), "Usuário ou senha incorretos.")
        }
    }

    fun cadastrar(req: CadastroRequest, onSucesso: () -> Unit) = executar {
        val r = Api.service.cadastrar(req)
        if (r.isSuccessful) {
            aviso = "Conta criada! Agora é só entrar."
            onSucesso()
        } else {
            erro = mensagemDeErro(r.errorBody()?.string(), "Não foi possível criar a conta.")
        }
    }

    fun carregarBarbeiros() {
        viewModelScope.launch {
            try {
                val r = Api.service.barbeiros()
                val lista = r.body()
                if (r.isSuccessful && lista != null) {
                    barbeiros = lista.map { Barbeiro(it.id ?: 0, it.nome ?: "Sem nome", it.especialidade ?: "Barbeiro") }
                    barbeirosErro = false
                } else {
                    barbeirosErro = true
                }
            } catch (e: Exception) {
                barbeirosErro = true
            }
        }
    }

    private fun executar(bloco: suspend () -> Unit) {
        viewModelScope.launch {
            carregando = true
            erro = null
            try {
                bloco()
            } catch (e: IOException) {
                erro = "Não foi possível conectar ao servidor. A API está rodando?"
            } catch (e: Exception) {
                erro = "Erro inesperado: ${e.message}"
            } finally {
                carregando = false
            }
        }
    }

    private fun mensagemDeErro(json: String?, padrao: String): String =
        try { Gson().fromJson(json, ApiMensagem::class.java)?.erro ?: padrao } catch (e: Exception) { padrao }
}
