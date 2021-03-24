package com.trash.pokeapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private RecyclerView recyclerView;
    private PokeAdaptador pokeAdaptador;

    private int offset;
    private boolean masItems;

    private static  final String TAG = "POKEDEX";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerVw);
        pokeAdaptador = new PokeAdaptador(this);
        recyclerView.setAdapter(pokeAdaptador);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (masItems){
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            Log.i(TAG, " Llegamos al final.");
                            masItems = false;
                            offset += 20;
                            obtenerPokemones(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        masItems = true;
        offset = 0;
        obtenerPokemones(offset);
    }

    private void obtenerPokemones(int offset) {
        pokemonesAPI service = retrofit.create(pokemonesAPI.class);
        Call<PokeResponse> pokeRespuestaCall =  service.obtenerPokemonesLista(20, offset);

        pokeRespuestaCall.enqueue(new Callback<PokeResponse>() {
            @Override
            public void onResponse(Call<PokeResponse> call, Response<PokeResponse> response) {
                masItems = true;
                if (response.isSuccessful()) {

                    PokeResponse pokeResponse = response.body();
                    ArrayList<Pokemon> arrayPokemones = pokeResponse.getResults();

                    pokeAdaptador.agregarListaPokemnos(arrayPokemones);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokeResponse> call, Throwable t) {
                masItems = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}