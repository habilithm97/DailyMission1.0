package com.example.dailymission10.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailymission10.R;
import com.example.dailymission10.databinding.ItemMainBinding;
import com.example.dailymission10.room.Todo;

public class ItemAdapter extends ListAdapter<Todo, ItemAdapter.ViewHolder> {

    public ItemAdapter() {
        super(DIFF_CALLBACK);
    }

    // 현재 리스트와 교체될 리스트를 비교하여 변경된 데이터만 골라서 빠르게 리스트를 갱신할 수 있음
    private static final DiffUtil.ItemCallback<Todo> DIFF_CALLBACK = new DiffUtil.ItemCallback<Todo>() {
        @Override // 두 아이템이 같은 객체인지
        public boolean areItemsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override // 두 아이템이 같은 데이터를 가지고 있는지
        public boolean areContentsTheSame(@NonNull Todo oldItem, @NonNull Todo newItem) {
            return oldItem.getContent().equals(newItem.getContent());
        }
    };

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemMainBinding itemBinding;

        public ViewHolder(@NonNull ItemMainBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainBinding itemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.item_main, parent, false);
        return new ViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Todo item = getItem(position);
        holder.itemBinding.setItem(item);
    }

    public Todo getPosition(int position) {
        return getItem(position);
    }
}
