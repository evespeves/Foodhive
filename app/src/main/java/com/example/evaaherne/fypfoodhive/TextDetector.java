package com.example.evaaherne.fypfoodhive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.evaaherne.fypfoodhive.activities.HowToUse;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.ml.vision.text.RecognizedLanguage;

import java.io.IOException;
import java.util.List;

public class TextDetector extends AppCompatActivity {

    private Button snapBtn;
    private Button detectBtn;
    private Button btnAdd;
    private ImageView imageView;
    private TextView txtView;
    private Bitmap imageBitmap;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_detector);
        snapBtn = findViewById(R.id.snapBtn);
        detectBtn = findViewById(R.id.detectBtn);
        imageView = findViewById(R.id.imageView);
        txtView = findViewById(R.id.txtView);
        btnAdd = findViewById(R.id.btnAddTitle);

        snapBtn.setOnClickListener(view -> dispatchTakePictureIntent());
        detectBtn.setOnClickListener(view -> detectTxt());
        btnAdd.setOnClickListener(view -> carryText());
    }


    /**
     * OCR used to recognized text
     *
     * https://firebase.google.com/docs/ml-kit/android/recognize-text
     */
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String TAG = "MLKIT";
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }


        @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
          //  imageFromBitmap(imageBitmap);
        }


        }


    private void detectTxt() {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        detector.processImage(image).addOnSuccessListener(firebaseVisionText -> processTxt(firebaseVisionText)).addOnFailureListener(e -> {

        });
    }

    private void processTxt(FirebaseVisionText text) {
        List<FirebaseVisionText.TextBlock> blocks = text.getTextBlocks();
        if (blocks.size() == 0) {
            Toast.makeText(TextDetector.this, "No Text detected, please retake the picture", Toast.LENGTH_LONG).show();
            return;
        }
        for (FirebaseVisionText.TextBlock block : text.getTextBlocks()) {
            String txt = block.getText();
            txtView.setTextSize(24);
            txtView.setText(txt);
        }
    }

    /**
     * Take the text noted to the Add food item page
     */
    public void carryText(){
        String result;
        result = txtView.getText().toString();

        Intent intent = new Intent(getApplicationContext(), AddFoodItem.class);
        intent.putExtra("Title", result);
        getApplicationContext().startActivity(intent);
    }

//    private void imageFromBitmap(Bitmap bitmap) {
//        // [START image_from_bitmap]
//        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);
//      recognizeText(image);
//    }
//
//    private void recognizeText(FirebaseVisionImage image) {
//
//        // [START get_detector_default]
//        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
//                .getOnDeviceTextRecognizer();
//
//        // [START run_detector]
//        Task<FirebaseVisionText> result =
//                detector.processImage(image)
//                        .addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
//                            @Override
//                            public void onSuccess(FirebaseVisionText firebaseVisionText) {
//
//                                for (FirebaseVisionText.TextBlock block : firebaseVisionText.getTextBlocks()) {
//                                    Rect boundingBox = block.getBoundingBox();
//                                    Point[] cornerPoints = block.getCornerPoints();
//                                    String text = block.getText();
//                                    processTextBlock(firebaseVisionText);
//                                        }
//
//                                    }
//
//                        })
//                        .addOnFailureListener(
//                                new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        // Task failed with an exception
//                                        // ...
//                                    }
//                                });
//        // [END run_detector]
//    }
//    private void processTextBlock(FirebaseVisionText result) {
//        // [START mlkit_process_text_block]
//        String resultText = result.getText();
//        for (FirebaseVisionText.TextBlock block: result.getTextBlocks()) {
//            String blockText = block.getText();
//            Float blockConfidence = block.getConfidence();
//            List<RecognizedLanguage> blockLanguages = block.getRecognizedLanguages();
//            Point[] blockCornerPoints = block.getCornerPoints();
//            Rect blockFrame = block.getBoundingBox();
//            txtView.setText(resultText);
//            for (FirebaseVisionText.Line line: block.getLines()) {
//                String lineText = line.getText();
//                Float lineConfidence = line.getConfidence();
//                List<RecognizedLanguage> lineLanguages = line.getRecognizedLanguages();
//                Point[] lineCornerPoints = line.getCornerPoints();
//                Rect lineFrame = line.getBoundingBox();
//            }
//        }

//    }
//
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();
    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    /**
     * Get the angle by which an image must be rotated given the device's current
     * orientation.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private int getRotationCompensation(String cameraId, Activity activity, Context context)
            throws CameraAccessException {
        // Get the device's current rotation relative to its "native" orientation.
        // Then, from the ORIENTATIONS table, look up the angle the image must be
        // rotated to compensate for the device's rotation.
        int deviceRotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int rotationCompensation = ORIENTATIONS.get(deviceRotation);

        // On most devices, the sensor orientation is 90 degrees, but for some
        // devices it is 270 degrees. For devices with a sensor orientation of
        // 270, rotate the image an additional 180 ((270 + 270) % 360) degrees.
        CameraManager cameraManager = (CameraManager) context.getSystemService(CAMERA_SERVICE);
        int sensorOrientation = cameraManager
                .getCameraCharacteristics(cameraId)
                .get(CameraCharacteristics.SENSOR_ORIENTATION);
        rotationCompensation = (rotationCompensation + sensorOrientation + 270) % 360;

        // Return the corresponding FirebaseVisionImageMetadata rotation value.
        int result;
        switch (rotationCompensation) {
            case 0:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                break;
            case 90:
                result = FirebaseVisionImageMetadata.ROTATION_90;
                break;
            case 180:
                result = FirebaseVisionImageMetadata.ROTATION_180;
                break;
            case 270:
                result = FirebaseVisionImageMetadata.ROTATION_270;
                break;
            default:
                result = FirebaseVisionImageMetadata.ROTATION_0;
                Log.e(TAG, "Bad rotation value: " + rotationCompensation);
        }
        return result;
    }
    /** How to add a menu bar
     *  https://www.youtube.com/watch?v=62y6Ad2SJEQ&ab_channel=PRABEESHRK**/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.inventory:
            Intent i = new Intent(getApplicationContext(), ProductListings.class);
            startActivity(i);
            setContentView(R.layout.activity_product_listings);
            return(true);
        case R.id.home:
            Intent b = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(b);
            setContentView(R.layout.activity_menu);
            return(true);
        case R.id.add:
            Intent c = new Intent(getApplicationContext(), AddFoodItem.class);
            startActivity(c);
            setContentView(R.layout.activity_add_food_item);
            return(true);
        case R.id.account:
            Intent a = new Intent(getApplicationContext(), UserProfile.class);
            startActivity(a);
            setContentView(R.layout.activity_user_listings);
            return(true);
        case R.id.howToUse:
            Intent d = new Intent(getApplicationContext(), HowToUse.class);
            startActivity(d);
            setContentView(R.layout.activity_how_to_use);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }


}