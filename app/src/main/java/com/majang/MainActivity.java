package com.majang;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linearLayout = findViewById(R.id.tiao_linear_layout);

        ArrayList<String[]> majang_list = new ArrayList<>();
        //get folder name
        String majang_types;
        String majang_name;
        try {
            for (String s : getAssets().list("majang_icon")) {
                majang_types = s;
                for (String s1 : getAssets().list("majang_icon/" + majang_types)) {
                    majang_name = s1;
                    String[] array = new String[]{majang_types, majang_name};
                    majang_list.add(array);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //sort
        majang_list.sort(new ChineseNameComparator());

        try{
            for(String[] s : majang_list){
                View majang_item = getLayoutInflater().inflate(R.layout.majang_item, linearLayout, false);
                ImageView imageView = majang_item.findViewById(R.id.majang_icon);
                TextView textView = majang_item.findViewById(R.id.majang_name);
                InputStream inputStream = getAssets().open(String.format("majang_icon/%s/%s", s[0], s[1]));
                imageView.setImageDrawable(Drawable.createFromStream(inputStream, null));
                imageView.requestLayout();
                imageView.getLayoutParams().height = 100;
                imageView.getLayoutParams().width = 100;
                textView.setText(s[1]);
                majang_item.setTag("unselected");
                majang_item.setPadding(10, 10, 10, 10);
                majang_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
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
                    }
                });
                if (s[0].equals("tiao")){
                    linearLayout.addView(majang_item);
                }
                if (s[0].equals("wan")){
                    LinearLayout linearLayout1 = findViewById(R.id.wan_linear_layout);
                    linearLayout1.addView(majang_item);
                }
                if (s[0].equals("tong")){
                    LinearLayout linearLayout1 = findViewById(R.id.tong_linear_layout);
                    linearLayout1.addView(majang_item);
                }
                if (s[0].equals("zi")){
                    LinearLayout linearLayout1 = findViewById(R.id.zi_linear_layout);
                    linearLayout1.addView(majang_item);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
class PaiView extends androidx.appcompat.widget.AppCompatImageView {
    public PaiView(Context context) {
        super(context);
    }
}

class ChineseNumberUtil {
    public static int chineseNumber2Int(String chineseNumber){
        int result = 0;
        int temp = 1;
        char[] cnArr = new char[]{'一','二','三','四','五','六','七','八','九'};
        char[] ziArr = new char[]{'北','西','南','東','白','發','中'};
        for(int i = 0; i < chineseNumber.length(); i++){
            char c = chineseNumber.charAt(i);
            boolean isNumber = false;
            for(int j = 0; j < cnArr.length; j++){
                if(c == cnArr[j]){
                    temp = j + 1;
                    isNumber = true;
                    break;
                }
            }
            if(!isNumber){
                for(int j = 0; j < ziArr.length; j++){
                    if(c == ziArr[j]){
                        temp = j + 1;
                        break;
                    }
                }
            }
            result = result * 10 + temp;
        }
        return result;
    }
}

class ChineseNameComparator implements Comparator<String[]> {
    @Override
    public int compare(String[] arg0, String[] arg1) {
        return Integer.compare(ChineseNumberUtil.chineseNumber2Int(arg0[1]),
                ChineseNumberUtil.chineseNumber2Int(arg1[1]));
    }
}