package com.petsup.services

import com.petsup.models.Avaliacao
import com.petsup.models.cliente.ClienteCadastro
import com.petsup.models.cliente.ClienteDetalhes
import com.petsup.models.cliente.ClienteLogin
import com.petsup.models.cliente.ClienteToken
import com.petsup.models.petshop.Petshop
import com.petsup.models.petshop.PetshopAvaliacao
import com.petsup.models.petshop.PetshopMediaPreco
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query
import java.lang.reflect.Type

interface ClienteService {
    @POST("clientes")
    fun postCLiente(@Body signUpRequest: ClienteCadastro): Call<Unit>

    @POST("clientes/login")
    fun login(@Body loginRequest: ClienteLogin): Call<ClienteToken>

    @GET("clientes")
    fun getClientes(@Header("Authorization") header:String): Call<List<ClienteDetalhes>>

    @GET("clientes/{idCliente}")
    fun getClienteById(@Query("idCliente") idCliente: Int,
                       @Header("Authorization") header:String): Call<ClienteDetalhes>

    // obj na query tbm?
    //@RequestParam
    @POST("clientes/adicionar-pfp/{idCliente}/{obj}")
    fun postProfilePicture(@Query("idCliente") idCliente: Int,
                           @Query("obj") obj: String,
                           @Header("Authorization") header:String): Call<Boolean>

    // byte[] == ByteArray ???
    @GET("clientes/retornar-blob/{idCliente}")
    fun getProfilePicture(@Query("idCliente") idCliente: Int,
                          @Header("Authorization") header:String): Call<ByteArray>

    @GET("clientes/retornar-imagem/{idCliente}")
    fun getImage(@Query("idCliente") idCliente: Int,
                 @Header("Authorization") header:String): Call<String>

    @PUT("clientes/atualizar-imagem/{idCliente}")
    fun updateImage(@Query("idCliente") idCliente: Int,
                    @Header("Authorization") header:String): Call<Boolean>

    @DELETE("clientes/deletar-imagem/{idCliente}")
    fun deleteImage(@Query("idCliente") idCliente: Int,
                    @Header("Authorization") header:String): Call<String>

    @GET("clientes/busca-email/{email}")
    fun getUserByEmail(@Query("email") email: String,
                       @Header("Authorization") header:String): Call<ClienteDetalhes>

    @PATCH("clientes/{idCliente}")
    fun updateClienteById(@Query("idCliente") idCliente: Int,
                          @Header("Authorization") header:String): Call<ClienteDetalhes>

    @POST("clientes/avaliar/{idCliente}/{idPetshop}")
    fun postAvaliacao(
        @Query("idCliente") idCliente: Int,
        @Query("idPetshop") idPetshop: Int,
        @Header("Authorization") header:String
    ): Call<Unit>

    @GET("clientes/avaliacao/{idCliente}/{idPetshop}")
    fun getAvaliacaoCliente(
        @Query("idCliente") idCliente: Int,
        @Query("idPetshop") idPetshop: Int,
        @Header("Authorization") header:String
    ): Call<Avaliacao>

    @GET("clientes/ordenar-media-avaliacao")
    fun getPetshopsOrderByMedia(@Header("Authorization") header:String): Call<List<PetshopAvaliacao>>

    @GET("clientes/ordenar-media-preco")
    fun getPetshopsOrderByPrecoAsc(@Header("Authorization") header:String): Call<List<PetshopMediaPreco>>

    @PATCH("clientes/latitude-longitude/{idCliente}/{latitude}/{longitude}")
    fun updateCurrentLocation(@Query("idCliente") idCliente: Int,
                              @Query("latitude") latitude: Double,
                              @Query("longitude") longitude: Double,
                              @Header("Authorization") header:String): Call<Unit>

    @GET("clientes/petshops-proximos/{idCliente}")
    fun getPetshopsByClienteBairro(@Query("idCliente") idCliente: Int,
                                   @Header("Authorization") header:String): Call<List<Petshop>>
}