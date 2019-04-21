package com.example.flashlight;

import android.content.DialogInterface;
import android.hardware.Camera;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView imageButton;
    Camera camera;
    Camera.Parameters parameters;
    boolean isflash = false;
    boolean ison = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        imageButton = findViewById ( R.id.on );
        if(getApplicationContext ().getPackageManager ().hasSystemFeature ( getPackageManager ().FEATURE_CAMERA_FLASH )){
            camera = camera.open ();
            parameters = camera.getParameters ();
            isflash = true;
        }

        imageButton.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(isflash){
                        if(!ison){
                            imageButton.setImageResource ( R.drawable.on );
                            parameters.setFlashMode ( Camera.Parameters.FLASH_MODE_TORCH );
                            camera.setParameters ( parameters );
                            camera.startPreview ();
                            ison = true;
                        }
                        else
                        {
                            imageButton.setImageResource ( R.drawable.off );
                            parameters.setFlashMode ( Camera.Parameters.FLASH_MODE_OFF );
                            camera.setParameters ( parameters );
                            camera.stopPreview ();
                            ison = false;
                        }
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder ( MainActivity.this );
                    builder.setTitle ("Error");
                    builder.setMessage ("Your Camera doesn't have a flashlight");
                    builder.setPositiveButton ( "OK", new DialogInterface.OnClickListener () {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss ();
                            finish ();
                        }
                    } );
                    AlertDialog alertDialog = builder.create ();
                    alertDialog.show ();
                }
            }
        } );


    }

    @Override
    protected void onStop() {
        super.onStop ();
        if(camera != null){
            camera.release ();
            camera = null;
        }
    }
}
