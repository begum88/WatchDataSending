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

public class SelectWeightActivity extends Activity {
    private String[] mData = new String[]{"60","65","70","75","80","85","90","100"};
    private WearableRecyclerView mListView;
    private StringListAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_weight);
        mListView = (WearableRecyclerView) findViewById(R.id.list2);
        mAdapter= new StringListAdapter(SelectWeightActivity.this, mData, (itemText) ->{
            saveWeight(Integer.parseInt(itemText));
            openSelectHeightActivity();
        });
        mListView.setAdapter(mAdapter);

    }
    private void saveWeight(int weight){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("w",weight);
        editor.commit();
    }
    private void openSelectHeightActivity(){
        Intent selectHeight = new Intent(this, SelectHeightActivity.class);
        startActivity(selectHeight);
        finish();
    }


}

