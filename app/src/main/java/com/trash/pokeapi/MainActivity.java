package com.trash.pokeapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.trash.pokeapi.api.pokemonesAPI;
import com.trash.pokeapi.modelos.PokeResponse;
import com.trash.pokeapi.modelos.Pokemon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //Instaciamos la libreria de forma global
    private Retrofit retrofit;

    private static  final String TAG = "POKEDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerPokemones();
    }

    private void obtenerPokemones() {
        pokemonesAPI service = retrofit.create(pokemonesAPI.class);
        Call<PokeResponse> pokeRespuestaCall =  service.obtenerPokemonesLista();

        pokeRespuestaCall.enqueue(new Callback<PokeResponse>() {
            @Override
            public void onResponse(Call<PokeResponse> call, Response<PokeResponse> response) {
                if (response.isSuccessful()) {

                    PokeResponse pokeResponse = response.body();
                    ArrayList<Pokemon> arrayPokemones = pokeResponse.getResults();

                    for (int i = 0; i < arrayPokemones.size(); i++){
                        Pokemon pokemon = arrayPokemones.get(i);
                        Log.i(TAG, " Pokemon: " + pokemon.getName());
                    }
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokeResponse> call, Throwable t) {
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}