package com.trash.pokeapi;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.trash.pokeapi.modelos.Pokemon;

import java.util.ArrayList;

public class PokeAdaptador extends RecyclerView.Adapter<PokeAdaptador.ViewHolder> {

    private ArrayList<Pokemon> pokeDataset;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView pokePicture;
        private TextView pokeName;

        public ViewHolder(View itemView){
            super(itemView);

            pokePicture = (ImageView) itemView.findViewById(R.id.pokePicture);
            pokeName = (TextView) itemView.findViewById(R.id.pokeName);
        }
    }
}
