package com.ashvinprajapati.qrgeneratorandscanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    private ImageView qrCode;
    private EditText nameEditText;
    private Button qrGenerateBtn, scanQrCodeBtn;
    private TextView scannedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        qrCode = findViewById(R.id.qrCode);
        nameEditText = findViewById(R.id.nameEditText);
        qrGenerateBtn = findViewById(R.id.qrGenerateBtn);
        scanQrCodeBtn = findViewById(R.id.scanQrCodeBtn);
        scannedText = findViewById(R.id.scannedText);

        qrGenerateBtn.setOnClickListener(v -> generateQr());
        scanQrCodeBtn.setOnClickListener(v -> scanQr());


    }

    private void scanQr() {
        IntentIntegrator intentIntegrator = new IntentIntegrator(this);
        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        intentIntegrator.setPrompt("Scan a QR Code");
        intentIntegrator.setCameraId(0);
        intentIntegrator.setOrientationLocked(false);
        intentIntegrator.setBeepEnabled(true);
        intentIntegrator.initiateScan();
    }

    private void generateQr() {
        String data = nameEditText.getText().toString();
        if (data.isEmpty()) {
            nameEditText.setError("Enter Name");
            return;
        }

        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            Bitmap map = barcodeEncoder.encodeBitmap(data, BarcodeFormat.QR_CODE, 400, 400);
            qrCode.setImageBitmap(map);
            nameEditText.setText("");
        }
        catch (WriterException e){
            e.printStackTrace();
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null){
            if (result.getContents() == null){
                Toast.makeText(this, "Scan Cancelled", Toast.LENGTH_SHORT).show();
            }
            else {
                scannedText.setText(result.getContents());
                Toast.makeText(this, "QR Code Scanned Successfully", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}