package com.majang.ui.home;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.majang.Constants;
import com.majang.R;
import com.majang.databinding.FragmentHomeBinding;

import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        String majang_types;
        try {
            for (String s : requireActivity().getAssets().list("majang_icon")) {
                majang_types = s;
                TreeMap<Integer, String> test = new TreeMap<>();
                LinearLayout linearLayout1;
                for (String s1 : requireActivity().getAssets().list("majang_icon/" + majang_types)) {
                    test.put(Constants.category.indexOf(s1.substring(0, 1)), s1);
                }
                switch (majang_types) {
                    case "tiao":
                        linearLayout1 = binding.tiaoLinearLayout;
                        break;
                    case "wan":
                        linearLayout1 = binding.wanLinearLayout;
                        break;
                    case "tong":
                        linearLayout1 = binding.tongLinearLayout;
                        break;
                    default:
                        linearLayout1 = binding.ziLinearLayout;
                        break;

                }
                for (String s1 : test.values()) {
                    View majang_item = getLayoutInflater().inflate(R.layout.majang_item, linearLayout1, false);
                    ImageView imageView = majang_item.findViewById(R.id.majang_icon);
                    TextView textView = majang_item.findViewById(R.id.majang_name);
                    InputStream inputStream = requireActivity().getAssets().open(String.format("majang_icon/%s/%s", majang_types, s1));
                    imageView.setImageDrawable(Drawable.createFromStream(inputStream, null));
                    imageView.requestLayout();
                    imageView.getLayoutParams().height = 100;
                    imageView.getLayoutParams().width = 100;
                    textView.setText(s1);
                    majang_item.setTag("unselected");
                    majang_item.setPadding(10, 10, 10, 10);
                    majang_item.setOnClickListener(v -> {
                        //點擊後圖片向上移動
                        if (majang_item.getTag().equals("selected")) {
                            //remove stroke
                            majang_item.setBackgroundResource(0);
                            majang_item.setTag("unselected");
                        } else {
                            //set out stroke
                            majang_item.setBackgroundResource(R.drawable.majang_item_stroke);
                            majang_item.setTag("selected");
                        }
                    });
                    linearLayout1.addView(majang_item);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}