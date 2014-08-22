package net.pixnet.sdk.test;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Show_Article extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedInstanceState = getIntent().getBundleExtra("bundle");
        setContentView(R.layout.activity_show__article);
        if (savedInstanceState.containsKey("title")) {
            ((TextView) findViewById(R.id.title)).setText("Title : "+savedInstanceState.getString("title"));
            ((TextView)findViewById(R.id.body)).setText("Body : "+savedInstanceState.getString("body"));
            ((TextView)findViewById(R.id.author)).setText("Author : "+savedInstanceState.getString("user"));
        }
        ((Button)findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
