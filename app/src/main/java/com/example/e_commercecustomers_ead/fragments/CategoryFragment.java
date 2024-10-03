package com.example.e_commercecustomers_ead.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_commercecustomers_ead.R;
import com.example.e_commercecustomers_ead.adapters.CategoryAdapter;
import com.example.e_commercecustomers_ead.models.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private ImageView backButton;

    private RecyclerView categoryRecyclerView;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        // Initialize RecyclerView
        categoryRecyclerView = view.findViewById(R.id.category_recycler_view);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        backButton = view.findViewById(R.id.backButton);

        // Initialize category data
        categoryList = new ArrayList<>();
        categoryList.add(new Category("Shirt", R.drawable.shirt_icon));
        categoryList.add(new Category("Dress", R.drawable.dress_icon));
        categoryList.add(new Category("Work Equipment", R.drawable.man_bag_icon));
        categoryList.add(new Category("Man Shoes", R.drawable.man_shoesman_icon));
        categoryList.add(new Category("Woman Bag", R.drawable.woman_bag_icon));
        categoryList.add(new Category("High Heels", R.drawable.woman_shoes_icon));

        // Add more categories as needed

        // Set adapter
        categoryAdapter = new CategoryAdapter(categoryList, getContext());
        categoryRecyclerView.setAdapter(categoryAdapter);


        // Back button listener
        backButton.setOnClickListener(v -> {
            // Navigate back to the previous fragment
            requireActivity().getSupportFragmentManager().popBackStack();
        });

        return view;
    }
}
