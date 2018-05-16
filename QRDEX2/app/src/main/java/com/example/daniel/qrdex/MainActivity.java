package com.example.daniel.qrdex;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnScanQRCode;
    private TextView txtFruit, txtSize, txtColor, lb1, lb2, lb3;
    private ImageView imagen;

    //QR Code Scanner Object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScanQRCode = (Button) findViewById(R.id.btnScanQRCode);
        txtFruit = (TextView) findViewById(R.id.txtFruitName);
        txtSize = (TextView) findViewById(R.id.txtFruitSize);
        txtColor = (TextView) findViewById(R.id.txtFruitColor);
        lb1 = (TextView) findViewById(R.id.lblFruitSize);
        lb2 = (TextView) findViewById(R.id.lblFruitName);
        lb3 = (TextView) findViewById(R.id.lblFruitColor);

        lb1.setVisibility(View.INVISIBLE);
        lb2.setVisibility(View.INVISIBLE);
        lb3.setVisibility(View.INVISIBLE);

        imagen = (ImageView) findViewById(R.id.imageView2);
        //Initialize the Scan Object
        qrScan = new IntentIntegrator(this);
        //OnClickListener Attached to the Button
        btnScanQRCode.setOnClickListener(this);
        String url = "https://orig00.deviantart.net/f60e/f/2017/042/0/7/pokeball_gif_by_termatior0-dayo3ow.gif";
        Glide.with(this)
                .load(url)
                .into(imagen);
    }
    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //Check to see if QR Code has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //QR Code contains some data
                try {
                    //Convert the QR Code Data to JSON
                    JSONObject obj = new JSONObject(result.getContents());
                    //Set up the TextView Values using the data from JSON
                    txtFruit.setText(obj.getString("fruit"));

                    String url =(obj.getString("size"));
                    Glide.with(this)
                            .load(url)
                            .into(imagen);
                    txtColor.setText(obj.getString("color"));
                    lb1.setVisibility(View.VISIBLE);
                    lb2.setVisibility(View.VISIBLE);
                    lb3.setVisibility(View.VISIBLE);

                } catch (JSONException e) {
                    e.printStackTrace();
                    //In case of exception, display whatever data is available on the QR Code
                    //This can be caused due to the format MisMatch of the JSON
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {

        //initiating the qr code scan
        qrScan.initiateScan();

    }
}