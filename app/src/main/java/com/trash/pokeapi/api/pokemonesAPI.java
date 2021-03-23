package com.trash.pokeapi.api;

import com.trash.pokeapi.modelos.PokeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface pokemonesAPI {

    @GET("pokemon")
    Call<PokeResponse>obtenerPokemonesLista ();
}
