package com.example.mmustpark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.zxing.WriterException;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class GenerateActivity extends AppCompatActivity {


    private TextView tvYour;
    private ImageView imageView;
    private TextInputEditText tiEd;
    private Button btnGen;
    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate);

        tvYour = findViewById(R.id.tv_your);
        imageView = findViewById(R.id.image_view);
        btnGen = findViewById(R.id.btn_gen);
        tiEd = findViewById(R.id.ti_ed);

        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = tiEd.getText().toString();
                if (data.isEmpty()) {
                    Toast.makeText(GenerateActivity.this, "Please enter some data to generate QR...", Toast.LENGTH_SHORT).show();
                } else {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int dimen = width < height ? width : height;
                    dimen = dimen * 3 / 4;

                    qrgEncoder = new QRGEncoder(tiEd.getText().toString(), null, QRGContents.Type.TEXT, dimen);

                    try {
                        bitmap = qrgEncoder.encodeAsBitmap();
                        tvYour.setVisibility(View.GONE);
                        imageView.setImageBitmap(bitmap);

                    } catch ( WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}