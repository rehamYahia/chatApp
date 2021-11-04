package com.example.chatapplication.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.chatapplication.R;
import com.example.chatapplication.databinding.ActivityMainBinding;
import com.example.chatapplication.utilities.Constants;
import com.example.chatapplication.utilities.PreferanceManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ActivityMainBinding binding;
        private String encodedImage;
    PreferanceManager preferanceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this, R.layout.activity_main);
         preferanceManager = new PreferanceManager(getApplicationContext());
       setListener();

            }

            private void setListener()
            {
                binding.textSignIn.setOnClickListener(v -> onBackPressed());
                binding.buttonSignUp.setOnClickListener(v -> {
                    if (isValidSignUpDetails()) {
                        signUp();
                    }
                });
                binding.layoutImage.setOnClickListener(v -> {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    pickImage.launch(intent);
                });
            }

            private void showToast( String message)
            {
                Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
            }

            private void signUp()
            {
                loading(true);
                FirebaseFirestore database =FirebaseFirestore.getInstance();
                HashMap<String , Object> user = new HashMap<>();
                user.put(Constants.KEY_NAME,binding.regName.getText().toString());
                user.put(Constants.KEY_EMAIL,binding.regEmail.getText().toString());
                user.put(Constants.KEY_PASSWORD,binding.regPassword.getText().toString());
                user.put(Constants.KEY_IMAGE,encodedImage);
                database.collection(Constants.KEY_COLLECTION_USERS)
                        .add(user)
                        .addOnSuccessListener(documentReference -> {
                            loading(false);
                            preferanceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                            preferanceManager.putString(Constants.KEY_USER_ID,documentReference.getId());
                            preferanceManager.putString(Constants.KEY_NAME,binding.regName.getText().toString());
                            preferanceManager.putString(Constants.KEY_IMAGE,encodedImage);
                            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        })
                        .addOnFailureListener(exception ->{
                            loading(false);
                            showToast(exception.getMessage());
                            Log.d(TAG, "reham " + exception.getMessage());

                        });
            }
            //---------------------------------Bitmap--------------------------------------------------//
            private String encodeImage(Bitmap bitmap)
            {
                int previewWidth = 150;
                int previewHeight = bitmap.getHeight() * previewWidth / bitmap.getWidth();
                Bitmap previewBitmap = Bitmap.createScaledBitmap(bitmap,previewWidth,previewHeight,false);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                previewBitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                return Base64.encodeToString(bytes,Base64.DEFAULT);
            }
            private final ActivityResultLauncher<Intent> pickImage = registerForActivityResult(
                    new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if(result.getResultCode() == RESULT_OK){
                            if(result.getData() != null){
                                Uri imageUri = result.getData().getData();
                                try {
                                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                    binding.imageProfile.setImageBitmap(bitmap);
                                    binding.textAddImage.setVisibility(View.GONE);
                                    encodedImage = encodeImage(bitmap);
                                }
                                catch (FileNotFoundException e)
                                {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
            );


            private boolean isValidSignUpDetails()
            {
                if(encodedImage == null)
                {
                    showToast("Selected profile image");
                    return false;
                }
                else if (binding.regName.getText().toString().trim().isEmpty())
                {
                    showToast("please enter your name");
                    return false;
                }
                else if(binding.regEmail.getText().toString().trim().isEmpty())
                {
                    showToast("please enter your email");
                    return false;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(binding.regEmail.getText().toString()).matches() )
                {
                    showToast("please enter valid email");
                    return false;
                }
                else if(binding.regPassword.getText().toString().trim().isEmpty())
                {
                    showToast("please enter password");
                    return false;
                }
                else if(binding.regConfirmPassword.getText().toString().trim().isEmpty())
                {
                    showToast("please enter confirm password");
                    return false;
                }
                else if(!binding.regPassword.getText().toString().equals(binding.regConfirmPassword.getText().toString()))
                {
                    showToast("password & confirm password must be the same");
                    return false;
                }else
                {
                    return true;
                }
            }

            private void loading(boolean isLoading)
            {
                if(isLoading)
                {
                    binding.buttonSignUp.setVisibility(View.INVISIBLE);
                    binding.progressBar.setVisibility(View.VISIBLE);
                }
                else{
                    binding.buttonSignUp.setVisibility(View.VISIBLE);
                    binding.progressBar.setVisibility(View.INVISIBLE);
                }
            }



    }

