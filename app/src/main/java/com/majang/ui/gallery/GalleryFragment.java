package com.majang.ui.gallery;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.json.JSONException;
import org.json.JSONObject;

import com.majang.Constants;
import com.majang.R;
import com.majang.databinding.FragmentGalleryBinding;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.stream.Collectors;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    ArrayList<String> type_list = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        LinearLayout linearLayout = binding.galleryLinearLayout;

        //讀入assets資料夾中的json
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        //將json轉成jsonObject
        try {
            assert json != null;
            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();
            while (keys.hasNext()) {
                String type = keys.next();
                Object value = jsonObject.get(type);
                Log.d("json", type);
                JSONObject item = new JSONObject(value.toString());
                Iterator<String> keys2 = item.keys();
                while (keys2.hasNext()) {
                    String title = keys2.next();
                    Object content = item.get(title);
                    Log.d("json", title + " " + content);
                    View view = getLayoutInflater().inflate(R.layout.gallery_item, null);
                    TextView galleryItemTitle = view.findViewById(R.id.gallery_item_title);
                    galleryItemTitle.setText(title);
                    TextView galleryItemContent = view.findViewById(R.id.gallery_item_content);
                    galleryItemContent.setText(content.toString());
                    view.setTag(type);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0, 0, 0, 50);
                    view.setLayoutParams(layoutParams);
                    view.setVisibility(View.GONE);
                    linearLayout.addView(view);
                    linearLayout.setPadding(0, 50, 0, 0);
                }
                type_list.add(type);
            }
            adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, type_list);
            binding.typeDropdown.setAdapter(adapter);
            binding.typeDropdown.setSelection(0);
            binding.typeDropdown.setOnItemClickListener((parent, view, position, id) -> {
                Log.d("dropdown", "onItemClick: " + parent.getItemAtPosition(position));
                String type = parent.getItemAtPosition(position).toString();
                for (int i = 0; i < linearLayout.getChildCount(); i++) {
                    View child = linearLayout.getChildAt(i);
                    if (child.getTag().equals(type)) {
                        child.setVisibility(View.VISIBLE);
                    } else {
                        child.setVisibility(View.GONE);
                    }
                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new ArrayAdapter<>(getContext(), R.layout.text_row_item, type_list);
        binding.typeDropdown.setSelection(0);
        binding.typeDropdown.setAdapter(adapter);
    }

}
