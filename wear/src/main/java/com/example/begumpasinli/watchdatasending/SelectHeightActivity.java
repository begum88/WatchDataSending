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

public class SelectHeightActivity extends Activity {
    private String[] mData = new String[]{"160","165","170","175","180","185","190","195"};
    private WearableRecyclerView mListView;
    private StringListAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_height);
        mListView = (WearableRecyclerView) findViewById(R.id.list3);
        mAdapter= new StringListAdapter(SelectHeightActivity.this, mData, (itemText) ->{
            saveWeight(Integer.parseInt(itemText));
            openSelectHeightActivity();
        });
        mListView.setAdapter(mAdapter);

    }
    private void saveWeight(int height){
        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("h",height);
        editor.commit();
    }
    private void openSelectHeightActivity(){
        Intent mainActivity = new Intent(this, MainActivityWear.class);
        startActivity(mainActivity);
        finish();
    }
}
