package com.example.evaaherne.fypfoodhive.activities;

import androidx.appcompat.app.AppCompatActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import android.os.Bundle;

import com.example.evaaherne.fypfoodhive.AddFoodItem;
import com.example.evaaherne.fypfoodhive.R;
import com.google.zxing.Result;


public class ScanCodeAct extends AppCompatActivity implements ZXingScannerView.ResultHandler {
 ZXingScannerView ScannerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result) {
        AddFoodItem.QR_info.setText(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause(){
        super.onPause();
        ScannerView.stopCamera();
    }
    @Override
    protected void onResume(){
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
