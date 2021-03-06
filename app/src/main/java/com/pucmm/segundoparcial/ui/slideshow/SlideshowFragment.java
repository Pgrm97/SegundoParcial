package com.pucmm.segundoparcial.ui.slideshow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pucmm.segundoparcial.MainAdapter;
import com.pucmm.segundoparcial.R;
import com.pucmm.segundoparcial.RoomDB;
import com.pucmm.segundoparcial.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    Spinner categorySpinner;
    RoomDB database;
    List<String> dataList = new ArrayList<>();
    Button btnAddCategory;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

        //Initialize database
        database = RoomDB.getInstance(getActivity());
        //Store database value in data list
        dataList = database.mainDao().getAllCategories();

        categorySpinner = root.findViewById(R.id.spinnerModifyProduct);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,  dataList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        btnAddCategory = root.findViewById(R.id.buttonAddModifyProduct);

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

        return root;
    }
}