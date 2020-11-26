package com.pucmm.segundoparcial.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pucmm.segundoparcial.AddCategoryFragment;
import com.pucmm.segundoparcial.MainAdapter;
import com.pucmm.segundoparcial.MainData;
import com.pucmm.segundoparcial.ProductAdapter;
import com.pucmm.segundoparcial.ProductData;
import com.pucmm.segundoparcial.R;
import com.pucmm.segundoparcial.RoomDB;
import com.pucmm.segundoparcial.ui.home.HomeFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    EditText inputNameAddProduct, inputNumberAddProduct;
    Spinner categorySpinner;
    RoomDB database;
    Button btnAddCategory, btnSaveAddProduct;
    List<String> dataList = new ArrayList<>();

    List<ProductData> productList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    ProductAdapter productAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        //Initialize database
        database = RoomDB.getInstance(getActivity());
        //Store database value in data list
        dataList = database.mainDao().getAllCategories();

        categorySpinner = root.findViewById(R.id.spinnerAddProduct);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,  dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        btnAddCategory = root.findViewById(R.id.buttonAddCategoryinAddProduct);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment addCategoryFragment = new HomeFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, addCategoryFragment, "AddCategory");
                transaction.addToBackStack("AddCategory");
                transaction.commit();
            }
        });

        btnSaveAddProduct = root.findViewById(R.id.buttonSaveAddProduct);

        inputNameAddProduct = root.findViewById(R.id.inputNameAddProduct);
        inputNumberAddProduct = root.findViewById(R.id.inputNumberAddProduct);

        categorySpinner = root.findViewById(R.id.spinnerAddProduct);

        //Initialize database
        database = RoomDB.getInstance(getActivity());
        //Store database value in data list
        productList = database.productDao().getAll();

        //Initialize adapter
        productAdapter = new ProductAdapter(getActivity(), productList);

        btnSaveAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get String from edit text
                String sName = inputNameAddProduct.getText().toString().trim();
                String sAmount = inputNumberAddProduct.getText().toString().trim();
                String sCategory = categorySpinner.getSelectedItem().toString();
                //Check condition
                if(!sName.equals("") && !sAmount.equals("") && !sCategory.equals("")){
                    //Initialize main data
                    ProductData data = new ProductData();
                    //Set text on main data
                    data.setName(sName);
                    data.setAmount(Integer.parseInt(sAmount));
                    data.setCategory(sCategory);
                    //Insert text in database
                    database.productDao().insert(data);
                    //Clear edit text
                    inputNameAddProduct.setText("");
                    inputNumberAddProduct.setText("");
                    //Notify when data is inserted
                    productList.clear();
                    productList.addAll(database.productDao().getAll());
                    productAdapter.notifyDataSetChanged();

                    AddCategoryFragment homeFragment = new AddCategoryFragment();
                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.nav_host_fragment, homeFragment, "Home Fragment");
                    //transaction.addToBackStack("AddCategory");
                    transaction.commit();
                }
                if(sName.equals("")){
                    inputNameAddProduct.setError("Please enter a name!");
                }
                if(sAmount.equals("")){
                    inputNumberAddProduct.setError("Please enter a price!");
                }
            }
        });

        return root;
    }
}