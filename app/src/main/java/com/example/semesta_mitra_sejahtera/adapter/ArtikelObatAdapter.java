package com.example.semesta_mitra_sejahtera.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.semesta_mitra_sejahtera.R;
import com.example.semesta_mitra_sejahtera.model.ArtikelModel;
import com.example.semesta_mitra_sejahtera.ui.DetailArtikelActivity;

import java.util.List;

public class ArtikelObatAdapter extends RecyclerView.Adapter<ArtikelObatAdapter.ArtikelViewHolder> {

    private List<ArtikelModel> artikelObatList;
    private Context context;

    public ArtikelObatAdapter(Context context, List<ArtikelModel> artikelObatList) {
        this.context = context;
        this.artikelObatList = artikelObatList;
    }

    @NonNull
    @Override
    public ArtikelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_artikel, parent, false);
        return new ArtikelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtikelViewHolder holder, int position) {
        ArtikelModel artikel = artikelObatList.get(position);
        holder.txtJudul.setText(artikel.getJudul());
        holder.txtDeskripsi.setText(artikel.getDeskripsi());

        // Mengatur deskripsi dengan memotong jika terlalu panjang
        String deskripsi = artikel.getDeskripsi();
        if (deskripsi.length() > 30) {
            deskripsi = deskripsi.substring(0, 30) + ". . .";
        }
        holder.txtDeskripsi.setText(deskripsi);

        // Ganti gambar sesuai dengan kategori artikel
        if (artikel.getKategori().equals("obat")) {
            holder.imgArtikel.setImageResource(R.drawable.artikel_kategori_obat);
        } else if (artikel.getKategori().equals("pakan")) {
            holder.imgArtikel.setImageResource(R.drawable.artikel_kategori_pakan);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailArtikelActivity.class);
                intent.putExtra("id", artikel.getId());
                intent.putExtra("judul", artikel.getJudul());
                intent.putExtra("kategori", artikel.getKategori());
                intent.putExtra("deskripsi", artikel.getDeskripsi());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return artikelObatList.size();
    }

    public static class ArtikelViewHolder extends RecyclerView.ViewHolder {
        TextView txtJudul, txtDeskripsi;
        ImageView imgArtikel;
        CardView cardView;

        public ArtikelViewHolder(@NonNull View itemView) {
            super(itemView);
            txtJudul = itemView.findViewById(R.id.txt_judul_artikel);
            txtDeskripsi = itemView.findViewById(R.id.txt_deksripsi_artikel);
            imgArtikel = itemView.findViewById(R.id.img_artikel);
            cardView = itemView.findViewById(R.id.cvArtikel);
            // Add references to other views if needed
        }
    }
}
