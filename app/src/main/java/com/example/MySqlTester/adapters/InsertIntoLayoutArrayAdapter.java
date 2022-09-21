package com.example.MySqlTester.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.MySqlTester.R;
import java.util.ArrayList;
import java.util.List;

public class InsertIntoLayoutArrayAdapter extends RecyclerView.Adapter<InsertIntoLayoutArrayAdapter.SelectViewHolder> {

    private ArrayList<SelectLayoutItemModel> itemList;
    private List<String> valuesOfCheckedItems = new ArrayList<>();
    private List<EditTextDataModel> dataList = new ArrayList<>();
    private int firstPos = 3;

    public InsertIntoLayoutArrayAdapter( ArrayList<SelectLayoutItemModel> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public SelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insertnewline, parent, false);
        SelectViewHolder viewHolder = new SelectViewHolder(view);
        dataList.add(new EditTextDataModel(-1, "n/a"));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectViewHolder holder, int position)  {
        holder.textView.setText(itemList.get(position).getColumnName());
        holder.checkBox.setChecked(false);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.checkBox.isChecked()) {
                    setStatesIfCheckboxChecked(holder);
                }
                else {
                    setStatesIfCheckboxUnchecked(holder);
                }
            }
        });

        holder.value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                addData(holder);
            }
        });
    }

    public List<String> getDataListValues() {
        List<String> result = new ArrayList<>();

        for (int i = firstPos; i < dataList.size(); i++) {
            result.add(dataList.get(i).getValue());
        }
        return result;
    }

    public List<String> getValuesOfCheckedItems() {
        return  valuesOfCheckedItems;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    private void addData(SelectViewHolder holder) {

        for (int i = 0; i < dataList.size(); i++) {
            if(dataList.get(i).getAdapterID() == holder.getAdapterPosition()) {
                dataList.remove(i);
            }
        }

        if(!holder.value.getText().toString().trim().isEmpty()) {
            dataList.add(new EditTextDataModel(holder.getAdapterPosition(),  holder.value.getText().toString()));
        }
    }

    private void setStatesIfCheckboxUnchecked(SelectViewHolder holder) {
        holder.value.setEnabled(false);
        holder.value.setText("");
        valuesOfCheckedItems.remove(itemList.get(holder.getAdapterPosition()).getColumnName());
    }

    private void setStatesIfCheckboxChecked(SelectViewHolder holder) {
        holder.value.setEnabled(true);
        holder.value.setText("");
        valuesOfCheckedItems.add(itemList.get(holder.getAdapterPosition()).getColumnName());
    }

    public class SelectViewHolder extends RecyclerView.ViewHolder  {
        private CheckBox checkBox;
        private TextView textView;
        private EditText value;

        public SelectViewHolder(View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.insertrow_checkbox);
            checkBox.setChecked(true);
            value = itemView.findViewById(R.id.insertrow_et_value);
            textView = itemView.findViewById(R.id.insertrow_tv_columnname);
        }
    }

}
