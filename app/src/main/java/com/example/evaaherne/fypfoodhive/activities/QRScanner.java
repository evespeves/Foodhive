package com.example.evaaherne.fypfoodhive.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.evaaherne.fypfoodhive.R;

/**
 *
 * https://www.youtube.com/watch?v=A2PqYkLb_-A&ab_channel=ARSLTech
 */
public class QRScanner extends AppCompatActivity {

    public static TextView resultTextView;
    Button scan_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);

        resultTextView = findViewById(R.id.result_text);
        scan_btn = findViewById(R.id.btn_scan);
        scan_btn.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), ScanCodeAct.class)));
    }
}
