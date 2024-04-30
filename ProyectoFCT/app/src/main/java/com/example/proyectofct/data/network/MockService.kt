package com.example.proyectofct.data.network

import android.util.Log
import co.infinum.retromock.Retromock
import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.proyectofct.data.model.Details
import com.example.proyectofct.data.model.FacturaModel
import com.example.proyectofct.data.model.Results
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class MockService @Inject constructor(private val api: RetroMockService) {
     suspend fun getFacturasMock(): List<FacturaModel> {
         Log.i("FRAN", "estoy en MockService.getFacturasMock()")
         var lista: List<FacturaModel> = emptyList()
         kotlin.runCatching { api.getFacturasMock() }
             .onSuccess {
                 val response2 = it.execute()
                 val response3 = response2.body()?.facturas
                 if (response3 != null) {
                     lista = response3
                     Log.i("FRAN", " del mock $lista")

                 }
                 return lista
             }.onFailure { Log.e("Fran", "Se ha jodio esta wea") }
         return emptyList()

     }


    suspend fun getDetails(): Details?{
        var response2: Details? = null
        kotlin.runCatching { api.getDetails() }.onSuccess {
            val response= it.execute()
             response2= response.body()!!
        }.onFailure { Log.e("mock","error") }
        return response2
    }









    /*  try {
          val response = api.getFacturasMock()
          if (!response.isCanceled) {
              val response2 = response.execute()
              val response3 = response2.body()?.facturas
              if (response3 != null) {
                  lista = response3
                  Log.i("FRAN"," del mock $lista")

              }
          } else {
              lista = emptyList()
          }
          return lista
      } catch (e: Exception) {
          Log.e("FRAN", "Exception during Mock call: ${e.message}", e)
          lista = emptyList()
      }
  }
*/

}