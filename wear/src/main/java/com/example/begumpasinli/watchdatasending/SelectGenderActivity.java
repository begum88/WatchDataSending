package com.example.begumpasinli.watchdatasending;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WearableRecyclerView;

import com.example.begumpasinli.watchdatasending.adapters.StringListAdapter;

/**
 * Created by begumpasinli on 2.04.2017.
 */
public class SelectGenderActivity extends Activity {

    private String[] mData = new String[]{"\u2640 female","\u2642 male"};
    private WearableRecyclerView mListView;
    private StringListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_gender);

        mListView = (WearableRecyclerView) findViewById(R.id.list);
        mAdapter= new StringListAdapter(SelectGenderActivity.this, mData, (itemText) ->{
            if(itemText.equalsIgnoreCase("female")){
                saveGender("female");
            }else{
                saveGender("male");
            }
            openSelectWeightActivity();
        });
        mListView.setAdapter(mAdapter);

    }

    private void saveGender(String gender){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("gender",gender);
        editor.commit();
    }
    private void openSelectWeightActivity(){
        Intent selectWeight = new Intent(this, SelectWeightActivity.class);
        startActivity(selectWeight);
        finish();
    }
}
