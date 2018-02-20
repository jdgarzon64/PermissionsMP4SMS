package com.example.juandavid.videomp4app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button btnGrabar;
    Button btnPlay;
    Button btnSms;
    EditText txtNombre;
    VideoView videoView;
    static final int SMS = 1;
    static final int READSD = 4;
    static final int CAMERA = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        btnGrabar = (Button) findViewById(R.id.btnGrabar);
        btnSms = (Button) findViewById(R.id.btnSms);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        videoView = (VideoView) findViewById(R.id.videoView);

    }

    public void grabar(View view) {
        askForPermission(Manifest.permission.CAMERA, CAMERA);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(this, " It not permit", Toast.LENGTH_SHORT).show();

       } else {

            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            File video = new File(getExternalFilesDir(null), txtNombre.getText().toString() + ".mp4");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(video));
            startActivity(intent);
        }
    }

    public void play(View view) {
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READSD);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(this, " It not permit", Toast.LENGTH_SHORT).show();

        } else {
            videoView.setVideoURI(Uri.parse(getExternalFilesDir("") + "/" + txtNombre.getText().toString() + ".mp4"));
            videoView.start();
        }

    }    public void sendSMS(View view) {
        askForPermission(Manifest.permission.SEND_SMS, SMS);
        askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, READSD);
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED ) {
            Toast.makeText(this, " It not permit", Toast.LENGTH_SHORT).show();

        } else {
            PackageManager pm = getApplicationContext().getPackageManager();

            int havePermit = pm.checkPermission(Manifest.permission.SEND_SMS, getApplicationContext().getPackageName());

            if (havePermit == PackageManager.PERMISSION_GRANTED) {
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage("3234901883", null, "Hi!", null, null);
                Toast.makeText(this, "Mensaje enviado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No tienes permisos para esto", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void askForPermission(String permit, int requestCode) {

        if (ContextCompat.checkSelfPermission(MainActivity.this, permit) !=
                PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, permit)) {

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permit}, requestCode);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{permit}, requestCode);

            }
        }
    }
}
