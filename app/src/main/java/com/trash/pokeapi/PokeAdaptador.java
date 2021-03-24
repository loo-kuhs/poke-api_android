package com.trash.pokeapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.trash.pokeapi.modelos.Pokemon;

import java.util.ArrayList;

public class PokeAdaptador extends RecyclerView.Adapter<PokeAdaptador.ViewHolder> {

    private ArrayList<Pokemon> pokeDataset;
    private Context context;

    public PokeAdaptador(Context context){
        this.context = context;
        pokeDataset = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = pokeDataset.get(position);
        holder.pokeName.setText(pokemon.getName());

        Glide.with(context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"+ pokemon.getNumber() +".png")
                .centerCrop()
                .transition(new DrawableTransitionOptions()
                    .crossFade())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.pokePicture);
    }

    @Override
    public int getItemCount() {
        return pokeDataset.size();
    }

    public void agregarListaPokemnos(ArrayList<Pokemon> arrayPokemones) {
        pokeDataset.addAll(arrayPokemones);
        notifyDataSetChanged();
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
