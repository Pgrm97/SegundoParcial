package com.pucmm.segundoparcial.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pucmm.segundoparcial.MainActivity;
import com.pucmm.segundoparcial.MainAdapter;
import com.pucmm.segundoparcial.MainData;
import com.pucmm.segundoparcial.R;
import com.pucmm.segundoparcial.RoomDB;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    //Initialize variable
    EditText editText;
    Button btAdd, btReset;
    RecyclerView recyclerView;

    private AppBarConfiguration mAppBarConfiguration;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //Assign variable
        editText = root.findViewById(R.id.edit_text);
        btAdd = root.findViewById(R.id.bt_add);
        btReset = root.findViewById(R.id.bt_reset);
        recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);

        //Initialize database
        database = RoomDB.getInstance(getActivity());
        //Store database value in data list
        dataList = database.mainDao().getAll();

        //Initialize Linear Layout Manager
        linearLayoutManager = new LinearLayoutManager(getActivity());
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        adapter = new MainAdapter(getActivity(), dataList);
        //Set adapter
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get String from edit text
                String sText = editText.getText().toString().trim();
                //Check condition
                if(!sText.equals("")){
                    //Initialize main data
                    MainData data = new MainData();
                    //Set text on main data
                    data.setText(sText);
                    //Insert text in database
                    database.mainDao().insert(data);
                    //Clear edit text
                    editText.setText("");
                    //Notify when data is inserted
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Delete all from database
                database.mainDao().reset(dataList);
                //Notify when data is cleared
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });

        return root;
    }
}