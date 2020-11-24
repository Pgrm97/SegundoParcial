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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pucmm.segundoparcial.MainAdapter;
import com.pucmm.segundoparcial.MainData;
import com.pucmm.segundoparcial.R;
import com.pucmm.segundoparcial.RoomDB;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    EditText inputNameAddProduct, inputNumberAddProduct;
    Spinner categorySpinner;
    RoomDB database;
    MainAdapter adapter;
    List<String> dataList = new ArrayList<>();

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

        Button saveProduct = root.findViewById(R.id.buttonSaveAddProduct);
        saveProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return root;
    }
}