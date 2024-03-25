package com.example.semesta_mitra_sejahtera.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semesta_mitra_sejahtera.R;
import com.example.semesta_mitra_sejahtera.model.JadwalPanenModel;
import com.example.semesta_mitra_sejahtera.ui.DetailJadwalPanenActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class JadwalPanenAdapter extends RecyclerView.Adapter<JadwalPanenAdapter.ViewHolder> {
    private List<JadwalPanenModel> jadwalPanenList;
    private Context context;
    private OnItemClickListener itemClickListener;
    // Hapus kata kunci 'static' di sini

    public JadwalPanenAdapter(List<JadwalPanenModel> jadwalPanenList, Context context) {
        this.jadwalPanenList = jadwalPanenList;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(JadwalPanenModel jadwalPanenModel);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_jadwal_panen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JadwalPanenModel jadwalPanen = jadwalPanenList.get(position);

        holder.txtTanggal.setText(formatTanggal(jadwalPanen.getTanggal()));
        holder.txtJumlahPanen.setText(jadwalPanen.getJumlahAyam() + " ekor");
        holder.txtId.setText("ID " + jadwalPanen.getId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailJadwalPanenActivity.class);
                intent.putExtra("id", jadwalPanen.getId());
                intent.putExtra("tanggal", jadwalPanen.getTanggal());
                intent.putExtra("jumlah_ayam", jadwalPanen.getJumlahAyam());
                intent.putExtra("bobot_ayam", jadwalPanen.getBobotAyam());
                intent.putExtra("nama_sopir", jadwalPanen.getNamaSopir());
                intent.putExtra("nopol", jadwalPanen.getNopol());
                intent.putExtra("nama_peternakan", jadwalPanen.getNamaPeternakan());
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return jadwalPanenList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTanggal, txtJumlahPanen, txtId;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.txt_tanggal_jadwal_panen);
            txtJumlahPanen = itemView.findViewById(R.id.txt_jumlah_ayam);
            txtId = itemView.findViewById(R.id.txt_id_jadwal_panen);
        }
    }
    private String formatTanggal(String tanggal) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date = sdf.parse(tanggal);
            SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy");
            return output.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return tanggal;
        }
    }

}
