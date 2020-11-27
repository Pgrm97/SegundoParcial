package com.pucmm.segundoparcial;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.pucmm.segundoparcial.ui.gallery.GalleryFragment;
import com.pucmm.segundoparcial.ui.slideshow.SlideshowFragment;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    //Initialize variable
    private List<ProductData> dataList;
    private Activity context;
    private RoomDB database;

    Spinner categorySpinner;
    private List<String> dataListSpinner;

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

                //Initialize main data
                ProductData d = dataList.get(holder.getAdapterPosition());
                //Get id
                final int sID = d.getID();
                // Get Text
                String sName = d.getName();
                // Get Amount
                String sAmount = d.getAmountString();
                // Get Text
                String sCategory = d.getCategory();


                //Create dialog
                final Dialog dialog = new Dialog(context);
                //Set content view
                dialog.setContentView(R.layout.dialog_update_product);
                //Initialize width
                int width = WindowManager.LayoutParams.MATCH_PARENT;
                //Initialize height
                int height = WindowManager.LayoutParams.WRAP_CONTENT;
                //Set layout
                dialog.getWindow().setLayout(width,height);
                //Show dialog
                dialog.show();

                //Store database value in data list
                dataListSpinner = database.mainDao().getAllCategories();

                categorySpinner = dialog.findViewById(R.id.spinnerUpdateProduct);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,  dataListSpinner);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySpinner.setAdapter(adapter);

                //Initialize and assign variable
                final EditText editName = dialog.findViewById(R.id.edit_text_productname);
                final EditText editAmount = dialog.findViewById(R.id.edit_text_productprice);

                Button btUpdate = dialog.findViewById(R.id.bt_update_product);

                //Set text on edit text
                editName.setText(sName);
                editAmount.setText(sAmount);

                btUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Dismiss dialog
                        dialog.dismiss();

                        //Get updated text from edit text
                        String uName = editName.getText().toString().trim();
                        //Get updated text from edit text
                        int uAmount = Integer.parseInt(editAmount.getText().toString().trim());
                        //Get updated text from edit text
                        String uCategory = categorySpinner.getSelectedItem().toString();
                        //Update text in database
                        database.productDao().update(sID,uName,uAmount,uCategory);
                        //Notify when data is updated
                        dataList.clear();
                        dataList.addAll(database.productDao().getAll());
                        notifyDataSetChanged();
                    }
                });
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
