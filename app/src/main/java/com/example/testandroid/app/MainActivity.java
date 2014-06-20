package com.example.testandroid.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity {
    TextView text;
    String response;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        sendMessage();
    }

    private void sendMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OAuthHelper oAH = new OAuthHelper("e6a0fa232cc4da68ae21b727b772229e", "fac5c7f719feef5eea37449b1fc6b2ad", "http://oob");
                    ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new NameValuePair() {
                        public String getName() {
                            return "title";
                        }
                        public String getValue() {
                            return "阿就台投阿";
                        }
                    });
                    params.add(new NameValuePair() {
                        public String getName() {
                            return "body";
                        }
                        public String getValue() {
                            return "阿就八抵阿";
                        }
                    });
                    response = oAH.post("9a9132ba351e888896d8f2bcdad3f921",params);
                    Article res = new Article(response);
                    response = res.user.name;
                    System.out.println(response);
                    Message msg = new Message();
                    listup.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("ERROR");
                    System.out.println("ERROR");
                    System.out.println("ERROR");
                    System.out.println("ERROR");
                    System.out.println("ERROR");
                    System.out.println("ERROR");
                    System.out.println("ERROR");
                }
            }
        }).start();
    }
    private Handler listup = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            text.setText(response);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
