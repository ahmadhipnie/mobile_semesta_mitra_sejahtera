package com.example.semesta_mitra_sejahtera.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semesta_mitra_sejahtera.R;
import com.example.semesta_mitra_sejahtera.model.PerkembanganModel;
import com.example.semesta_mitra_sejahtera.ui.DetailJadwalPanenActivity;
import com.example.semesta_mitra_sejahtera.ui.DetailPerkembanganActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

public class PerkembanganAdapter extends RecyclerView.Adapter<PerkembanganAdapter.ViewHolder> {
    private Context context;
    private List<PerkembanganModel> perkembanganList;
    private static PerkembanganAdapter.OnItemClickListener onItemClickListener;

    public PerkembanganAdapter(Context context, List<PerkembanganModel> perkembanganList) {
        this.context = context;
        this.perkembanganList = perkembanganList;
    }

    public void setOnItemClickListener(PerkembanganAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(PerkembanganModel perkembanganModel);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_perkembangan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PerkembanganModel perkembangan = perkembanganList.get(position);

        // Set data ke dalam item RecyclerView
        holder.txtIdPerkembangan.setText("ID " + perkembangan.getId());
        holder.txtBobotPerkembangan.setText(perkembangan.getBobot() + " kg");
        holder.txtTanggalPerkembangan.setText(formatTanggal(perkembangan.getTanggal()));
        holder.txtMingguKePerkembangan.setText("Minggu ke - " + perkembangan.getMingguKe());
        holder.txtNamaPeternakan.setText(perkembangan.getNamaPeternakan());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailPerkembanganActivity.class);
                intent.putExtra("id", perkembangan.getId());
                intent.putExtra("tanggal", perkembangan.getTanggal());
                intent.putExtra("minggu_ke", perkembangan.getMingguKe());
                intent.putExtra("pakan_pakai", perkembangan.getPakanPakai());
                intent.putExtra("pakan_sisa", perkembangan.getPakanSisa());
                intent.putExtra("bobot", perkembangan.getBobot());
                intent.putExtra("afkir", perkembangan.getAfkir());
                intent.putExtra("kematian", perkembangan.getKematian());
                intent.putExtra("nama_peternakan", perkembangan.getNamaPeternakan());
                intent.putExtra("alamat", perkembangan.getAlamat());
                intent.putExtra("created_at", perkembangan.getCreatedAt());
                intent.putExtra("updated_at", perkembangan.getUpdatedAt());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return perkembanganList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIdPerkembangan, txtBobotPerkembangan, txtTanggalPerkembangan, txtMingguKePerkembangan, txtNamaPeternakan;

        public ViewHolder(View itemView) {
            super(itemView);
            txtIdPerkembangan = itemView.findViewById(R.id.txt_id_perkembangan);
            txtBobotPerkembangan = itemView.findViewById(R.id.txt_bobot_perkembangan);
            txtTanggalPerkembangan = itemView.findViewById(R.id.txt_tanggal_perkembangan);
            txtMingguKePerkembangan = itemView.findViewById(R.id.txt_minggu_ke_perkembangan);
            txtNamaPeternakan = itemView.findViewById(R.id.txt_nama_peternakan);
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

