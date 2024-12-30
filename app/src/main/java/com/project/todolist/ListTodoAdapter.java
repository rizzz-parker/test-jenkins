package com.project.todolist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListTodoAdapter extends RecyclerView.Adapter<ListTodoAdapter.ListViewHolder> {

    private final List<ListToDoClass> todoList;
    private final KegiatanListener hapusListener;
    private final KegiatanListener ubahListener;

    public ListTodoAdapter(List<ListToDoClass> todoList, KegiatanListener hapusListener, KegiatanListener ubahListener){
        this.todoList = todoList != null ? todoList : new ArrayList<>(); // Null check to prevent NPE
        this.hapusListener = hapusListener;
        this.ubahListener = ubahListener;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        ListToDoClass todoItem = todoList.get(position);
        holder.tvNamaKegiatan.setText(todoItem.getNameListToDo());
        holder.tvTanggalKegiatan.setText(todoItem.getDate());  // Tampilkan tanggal
        holder.tvWaktuKegiatan.setText(todoItem.getTime());    // Tampilkan waktu
    }


    @Override
    public int getItemCount() {
        return todoList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvNamaKegiatan;
        private final TextView tvTanggalKegiatan;  // Add this for the date
        private final TextView tvWaktuKegiatan;    // Add this for the time

        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaKegiatan = itemView.findViewById(R.id.tvNamaKegiatan);
            tvTanggalKegiatan = itemView.findViewById(R.id.tvTanggalKegiatan);  // Initialize the date TextView
            tvWaktuKegiatan = itemView.findViewById(R.id.tvWaktuKegiatan);      // Initialize the time TextView
            ImageButton btnUbahKegiatan = itemView.findViewById(R.id.btnUbahKegiatan);
            ImageButton btnHapusKegiatan = itemView.findViewById(R.id.btnHapuskegiatan);

            // Set click listeners
            btnUbahKegiatan.setOnClickListener(v -> ubahListener.onUbahKegiatan(getAdapterPosition()));
            btnHapusKegiatan.setOnClickListener(v -> hapusListener.onHapusKegiatan(getAdapterPosition()));
        }
    }


    // Define the listener interface
    public interface KegiatanListener {
        void onHapusKegiatan(int position);
        void onUbahKegiatan(int position);
    }
}
