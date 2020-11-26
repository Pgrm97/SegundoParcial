package com.pucmm.segundoparcial;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    //Initialize variable
    private List<ProductData> dataList;
    private Activity context;
    private RoomDB database;

    //Create constructor
    public ProductAdapter(Activity context, List<ProductData> dataList){
        this.context = context;
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Initialize view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_row_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        //Initialize product data
        ProductData data = dataList.get(position);
        //Initialize database
        database = RoomDB.getInstance(context);
        //Set text on text view
        holder.nameView.setText(data.getName());
        //Set amount on amount view
        holder.amountView.setText(data.getAmountString());
        //Set category on category view
        holder.categoryView.setText(data.getCategory());

        holder.btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                //Initialize main data
//                MainData d = dataList.get(holder.getAdapterPosition());
//                //Get id
//                final int sID = d.getID();
//                // Get Text
//                String sText = d.getText();
//
//                //Create dialog
//                final Dialog dialog = new Dialog(context);
//                //Set content view
//                dialog.setContentView(R.layout.dialog_update);
//                //Initialize width
//                int width = WindowManager.LayoutParams.MATCH_PARENT;
//                //Initialize height
//                int height = WindowManager.LayoutParams.WRAP_CONTENT;
//                //Set layout
//                dialog.getWindow().setLayout(width,height);
//                //Show dialog
//                dialog.show();
//
//                //Initialize and assign variable
//                final EditText editText = dialog.findViewById(R.id.edit_text);
//                Button btUpdate = dialog.findViewById(R.id.bt_update);
//
//                //Set text on edit text
//                editText.setText(sText);
//
//                btUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        //Dismiss dialog
//                        dialog.dismiss();
//
//                        //Get updated text from edit text
//                        String uText = editText.getText().toString().trim();
//                        //Update text in database
//                        database.mainDao().update(sID,uText);
//                        //Notify when data is updated
//                        dataList.clear();
//                        dataList.addAll(database.mainDao().getAll());
//                        notifyDataSetChanged();
//                    }
//                });
            }
        });

        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());
                alert.setTitle("Delete entry");
                alert.setMessage("Are you sure you want to delete?");
                alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Initialize main data
                        ProductData d = dataList.get(holder.getAdapterPosition());
                        //Delete text from database
                        database.productDao().delete(d);

                        //Notify when data is deleted
                        int position = holder.getAdapterPosition();
                        dataList.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position,  dataList.size());
                    }
                });
                alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        TextView nameView, amountView, categoryView;
        ImageView btEdit, btDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Assign variable
            nameView = itemView.findViewById(R.id.name_view);
            amountView = itemView.findViewById(R.id.amount_view);
            categoryView = itemView.findViewById(R.id.category_view);
            btEdit = itemView.findViewById(R.id.bt_edit_product);
            btDelete = itemView.findViewById(R.id.bt_delete_product);
        }
    }
}
