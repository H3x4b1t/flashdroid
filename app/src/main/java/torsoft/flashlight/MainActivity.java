package torsoft.flashlight;

import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnTorch;
    private boolean torchOn;
    private boolean hasFlash;

    private Camera objCamera;
    Camera.Parameters parametersCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prepareFlashlight();
        encenderLinterna();
    }

    private void getObjCamera(){
        if (objCamera == null){
            try {
                objCamera = Camera.open();
                parametersCamera = objCamera.getParameters();
            }catch (RuntimeException e){
                btnTorch.setEnabled(false);
            }
        }
    }

    private void flashOn(){
        if (!torchOn){
            if (objCamera == null || parametersCamera == null){
                return;
            }
            if (parametersCamera == null){
                return;
            }
            parametersCamera.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            btnTorch.setBackgroundResource(R.drawable.boton_on);
            objCamera.setParameters(parametersCamera);
            objCamera.startPreview();
            torchOn = true;
        }
    }

    private void flashOff(){
        if (torchOn){
            if (objCamera == null || parametersCamera == null){
                return;
            }
            if (parametersCamera == null){
                return;
            }
            parametersCamera.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            btnTorch.setBackgroundResource(R.drawable.boton_off);
            objCamera.setParameters(parametersCamera);
            objCamera.stopPreview();
            torchOn = false;
        }
    }

    private void encenderLinterna(){
        btnTorch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (torchOn){
                    flashOff();
                }else{
                    flashOn();
                }
            }
        });
    }

    private void prepareFlashlight(){
        btnTorch = (Button) findViewById(R.id.botonTorch);
        hasFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash){
            btnTorch.setEnabled(false);
            return;
        } else{
            getObjCamera();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        //startService();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        //stopService();
        super.onDestroy();
    }


}
