package com.example.semesta_mitra_sejahtera.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semesta_mitra_sejahtera.R;
import com.example.semesta_mitra_sejahtera.model.CabangModel;

import java.util.List;

public class CabangAdapter extends RecyclerView.Adapter<CabangAdapter.CabangViewHolder> {
    private Context context;
    private List<CabangModel> cabangList;
    private static OnItemClickListener onItemClickListener;

    public CabangAdapter(Context context, List<CabangModel> cabangList) {
        this.context = context;
        this.cabangList = cabangList;
    }

    @NonNull
    @Override
    public CabangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_cabang, parent, false);
        return new CabangViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    public interface OnItemClickListener {
        void onItemClick(CabangModel cabangModel);
    }

    @Override
    public void onBindViewHolder(@NonNull CabangViewHolder holder, int position) {
        CabangModel cabang = cabangList.get(position);
        holder.txtId.setText("ID : " + cabang.getId());
        holder.txtNama.setText(cabang.getNamaPeternakan());
        holder.txtAlamat.setText(cabang.getAlamat());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int position = holder.getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onItemClickListener.onItemClick(cabangList.get(position)); // Memanggil onItemClick saat item diklik
                    }
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return cabangList.size();
    }

    public class CabangViewHolder extends RecyclerView.ViewHolder {
        TextView txtId, txtNama, txtAlamat;
        CardView cardView;

        public CabangViewHolder(View itemView) {
            super(itemView);
            txtId = itemView.findViewById(R.id.txt_id_peternakan);
            txtNama = itemView.findViewById(R.id.txt_nama_peternakan);
            txtAlamat = itemView.findViewById(R.id.txt_alamat_peternakan);
            cardView = itemView.findViewById(R.id.cvCabang);
        }
    }
}

