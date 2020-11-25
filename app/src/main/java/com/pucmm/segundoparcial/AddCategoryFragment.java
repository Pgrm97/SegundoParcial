package com.pucmm.segundoparcial;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pucmm.segundoparcial.ui.gallery.GalleryFragment;

import java.util.ArrayList;
import java.util.List;


public class AddCategoryFragment extends Fragment {

    RecyclerView recyclerView;

    List<ProductData> productList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    ProductAdapter adapter;

    Button btnAddProduct;

    public AddCategoryFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_category, container, false);

        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_product);

        //Initialize database
        database = RoomDB.getInstance(getActivity());
        //Store database value in data list
        productList = database.productDao().getAll();

        //Initialize Linear Layout Manager
        linearLayoutManager = new LinearLayoutManager(getActivity());
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        adapter = new ProductAdapter(getActivity(), productList);
        //Set adapter
        recyclerView.setAdapter(adapter);

        btnAddProduct = root.findViewById(R.id.btn_add_mainpage);
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GalleryFragment addProductFragment = new GalleryFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, addProductFragment, "Add Product Fragment");
                transaction.addToBackStack("Add Product Fragment");
                transaction.commit();
            }
        });

        return root;
    }
}