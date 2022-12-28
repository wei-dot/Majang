package com.majang.ui.home;

import android.app.AlertDialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;
import com.majang.Constants;
import com.majang.R;
import com.majang.databinding.FragmentHomeBinding;
import com.majang.hai.Hai;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        ArrayList<String> views = new ArrayList<>();
        binding.bottomBar.deleteAllButton.setOnClickListener(v -> {
            binding.bottomBar.myLinearLayout.removeAllViews();
            views.clear();
        });
        ArrayList<Hai> myPai = new ArrayList<>();
        binding.bottomBar.nextButton.setOnClickListener(v -> {
            if (views.size() >= 14) {
                for (int i = 0; i < 14; i++) {
                    myPai.add(new Hai(views.get(i)));
                }
                String temp = Hai.ron(new ArrayList<>(myPai.subList(0,myPai.size()-1)), myPai.get(myPai.size()-1), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new Hai[]{new Hai("西風")}, new Hai[]{new Hai("三條")}, true);
                Log.d("ron", temp);
                new AlertDialog.Builder(getContext())
                        .setTitle("和了")
                        .setMessage(temp)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });

        Spinner spinner = binding.optionSpinner;
        String[] items = {"手牌","銃牌","明槓","暗槓","副露","寶牌","裡寶牌","自摸(o/x)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        MaterialButton info = binding.bottomBar.infoButton;
        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(
                        getContext())
                        .setTitle("不計以下役種:")
                        .setMessage("一翻:立直、一發、門清自摸和、嶺上開花、搶槓、海底撈月、河底撈魚、赤寶牌\n" +
                                "兩翻:雙立直\n" +
                                "五翻:流局滿貫\n" +
                                "役滿:天和、地和 \n" +
                                "這些都是和牌局的手牌比較無關的要計算要通靈，所以不計")
                        .setPositiveButton("確定", null)
                        .show();
            }
        });


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
                    LinearLayout linearLayout = binding.bottomBar.myLinearLayout;
                    View majang_item = addItem(majang_types, s1, linearLayout1);


                    final String Majang_types = majang_types;
                    majang_item.setOnClickListener(v -> {
                        try {
                            View majang_item1;
                            int sum = Collections.frequency(views, s1);
                            if (sum < 4 && views.size() < 14) {
                                views.add(s1);
                                majang_item1 = addItem(Majang_types, s1, linearLayout);
                                majang_item1.setOnClickListener(v1 -> {
                                    linearLayout.removeView(majang_item1);
                                    views.remove(s1);
                                    Log.d("test", views.toString());
                                });
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        Log.d("test", views.toString());

                    });
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }

    public View addItem(String majang_types, String s1, LinearLayout linearLayout) throws IOException {
        View majang_item = getLayoutInflater().inflate(R.layout.majang_item, linearLayout, false);
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
        linearLayout.addView(majang_item);
        return majang_item;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}