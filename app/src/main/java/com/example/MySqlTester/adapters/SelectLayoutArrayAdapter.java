package com.example.MySqlTester.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.MySqlTester.R;
import java.util.ArrayList;
import java.util.List;

public class SelectLayoutArrayAdapter extends RecyclerView.Adapter<SelectLayoutArrayAdapter.SelectViewHolder> {

    private Context context;
    private ArrayList<SelectLayoutItemModel> itemList;
    List<String> valuesOfCheckedItems = new ArrayList<>();

    public SelectLayoutArrayAdapter(Context context, ArrayList<SelectLayoutItemModel> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selectlayoutitem, parent, false);
        return new SelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder holder, int position)  {
            holder.textView.setText(itemList.get(position).getColumnName());
            holder.checkBox.setChecked(false);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    valuesOfCheckedItems.add(itemList.get(holder.getAdapterPosition()).getColumnName());
                }
                else {
                    valuesOfCheckedItems.remove(itemList.get(holder.getAdapterPosition()).getColumnName());
                }
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<String> getValuesOfCheckedItemsFromAdapter() {
        return valuesOfCheckedItems;
    }

    public class SelectViewHolder extends RecyclerView.ViewHolder  {
        CheckBox checkBox;
        TextView textView;

        public SelectViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.selectitem_cb_checkbox);
            textView = itemView.findViewById(R.id.selectitem_tv_type);
        }
    }



}
