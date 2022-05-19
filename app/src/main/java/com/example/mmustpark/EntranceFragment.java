package com.example.mmustpark;

import androidx.fragment.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.WriterException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class EntranceFragment extends Fragment {

    Button btnGen, btnTimeout;
    Spinner sOccupation;
    Spinner sPArea;
    Spinner sPlateno;
    TextView tvTimein, tvTimeout;
    ImageView imageView;

    private QRGEncoder qrgEncoder;
    private Bitmap bitmap;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date;

    FirebaseAuth mAuth;
    FirebaseUser mUser;

    DatabaseReference userDbRef;

    ArrayList<String> List;
    ArrayAdapter<String> adapter2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_entrance, container, false);

        String [] values =
                {"--Occupation--","Staff", "Student", "Visitor"};
        Spinner occupation = (Spinner) v.findViewById(R.id.s_occupation);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        occupation.setAdapter(adapter);

        String [] values1 =
                {"--Parking Area--","SPD", "LBB", "ECA", "LIBRARY"};
        Spinner pArea = (Spinner) v.findViewById(R.id.s_pArea);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, values1);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        pArea.setAdapter(adapter1);

        btnGen = v.findViewById(R.id.btn_gen);
        btnTimeout = v.findViewById(R.id.btn_timeout);
        tvTimein = v.findViewById(R.id.tv_timein);
        tvTimeout = v.findViewById(R.id.tv_timeout);
        sOccupation = v.findViewById(R.id.s_occupation);
        sPArea = v.findViewById(R.id.s_pArea);
        sPlateno = v.findViewById(R.id.s_plateno);
        imageView = v.findViewById(R.id.image_view);

        /*GetDateTime getDateTime = new GetDateTime(this.getActivity());
        getDateTime.getTimein(new GetDateTime.VolleyCallBack() {
            @Override
            public void onGetTimein(String date, String time) {

                tvTimein.setText(date + "   " + time);
            }
        });*/

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat(" MMM d, yyyy h:mm:ss a");
        Date = simpleDateFormat.format(calendar.getTime());

        tvTimein.setText(Date);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        userDbRef = FirebaseDatabase.getInstance().getReference().child("Users");

        List  = new ArrayList<>();
        adapter2 = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, List);
        sPlateno.setAdapter(adapter2);
        showData();

        btnTimeout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                tvTimeout.setText(mydate);
            }
        });

        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        String occupation = sOccupation.getSelectedItem().toString();
                        String pArea = sPArea.getSelectedItem().toString();
                        String plateno = sPlateno.getSelectedItem().toString();
                        String timein = tvTimein.getText().toString();
                        String timeout = tvTimeout.getText().toString();

                        if (occupation.isEmpty() || pArea.isEmpty() || plateno.isEmpty() || timein.isEmpty() && timeout.isEmpty()) {

                            Toast.makeText(getActivity(), "Please enter some data to generate QR...", Toast.LENGTH_SHORT).show();

                        } else {
                            /*WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                            Display display = manager.getDefaultDisplay();
                            Point point = new Point();
                            display.getSize(point);
                            int width = point.x;
                            int height = point.y;
                            int dimen = width < height ? width : height;
                            dimen = dimen * 3 / 4;*/

                            qrgEncoder = new QRGEncoder("Occupation:  " + "   " + occupation + "\n" + "Parking Area:  " + "   " + pArea + "\n" + "Vehicle Plateno:  " + "   "+ plateno + "\n" + "Time In:  " + "   "+ timein + "\n" + "Time Out:  " + "   " + timeout, null, QRGContents.Type.TEXT, 500);

                            try {

                                Toast.makeText(getActivity(), "Parking Space Booked Successfully", Toast.LENGTH_SHORT).show();
                                bitmap = qrgEncoder.encodeAsBitmap();
                                //tvYour.setVisibility(View.GONE);
                                imageView.setImageBitmap(bitmap);

                            } catch ( WriterException e) {
                                e.printStackTrace();
                            }
                        }
                      }
                });

        return v;
        //return inflater.inflate(R.layout.fragment_entrance, container, false);
    }

    private void showData() {

        userDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot item:snapshot.getChildren()){
                    List.add(item.child("plateno").getValue(String.class));
                }
                adapter2.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}