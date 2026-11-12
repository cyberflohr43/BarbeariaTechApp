package com.example.projeto_sistema_barbearia

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Formatos de JSON conforme o Program.cs da API
data class LoginRequest(val usuario: String, val senha: String)
data class LoginResponse(val nome: String?, val email: String?, val mensagem: String?)
data class CadastroRequest(val nome: String, val cpf: String, val telefone: String, val email: String, val senha: String)
data class ApiMensagem(val mensagem: String?, val erro: String?)
data class BarbeiroDto(
    @SerializedName("id_barbeiro") val id: Int?,
    val nome: String?, val status: String?, val telefone: String?,
    val email: String?, val especialidade: String?
)

interface BarbeariaApi {
    @POST("api/login") suspend fun login(@Body body: LoginRequest): Response<LoginResponse>
    @POST("api/cadastrar") suspend fun cadastrar(@Body body: CadastroRequest): Response<ApiMensagem>
    @GET("api/barbeiros") suspend fun barbeiros(): Response<List<BarbeiroDto>>
}

object Api {
    // Emulador: 10.0.2.2 = localhost do PC. Celular real: use o IP do PC na rede (ex: http://192.168.0.10:5151/)
    const val BASE_URL = "http://10.0.2.2:5151/"

    val service: BarbeariaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BarbeariaApi::class.java)
    }
}
