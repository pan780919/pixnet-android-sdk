package net.pixnet.sdk.test;


import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.TextView;

import net.pixnet.sdk.PIXNET;
import net.pixnet.sdk.utils.Blog;
import net.pixnet.sdk.utils.Helper;
import net.pixnet.sdk.utils.OAuthConnectionTool;
import net.pixnet.sdk.utils.Request;
import net.pixnet.sdk.utils.RequestController;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = (Button) findViewById(android.R.id.button1);
        Button btn2 = (Button) findViewById(android.R.id.button2);
        final TextView txt1 = (TextView) findViewById(android.R.id.text1);
        final TextView txt2 = (TextView) findViewById(android.R.id.text2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("");
                txt2.setText("");
                PIXNET.oAuth1Login(MainActivity.this, new PIXNET.OnAccessTokenGotListener() {
                    @Override
                    public void onAccessTokenGot(String token, String secret) {
                        txt1.setText(token);
                        txt2.setText(secret);
                        Blog blog = new Blog(MainActivity.this);
                        blog.setBlogInfo("Koi'sBlog",null,null,null,new Request.RequestCallback() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                            }
                        });
                        blog.addArticle("安安","快成功囉",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,new Request.RequestCallback() {
                            @Override
                            public void onResponse(String response) {
                                System.out.println(response);
                            }
                        });
                    }

                    @Override
                    public void onError(String msg) {
                        Helper.toast(MainActivity.this, msg);
                    }
                });
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt1.setText("");
                txt2.setText("");
                PIXNET.oAuth2Login(MainActivity.this, new PIXNET.OnAccessTokenGotListener() {
                    @Override
                    public void onAccessTokenGot(String token, String secret) {
                        txt1.setText(token);
                        txt2.setText(secret);
                    }

                    @Override
                    public void onError(String msg) {
                        Helper.toast(MainActivity.this, msg);
                    }
                });
            }
        });

    }

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
