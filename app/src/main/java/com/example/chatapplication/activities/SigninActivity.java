package com.example.chatapplication.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.chatapplication.R;
import com.example.chatapplication.databinding.ActivitySigninBinding;
import com.example.chatapplication.utilities.Constants;
import com.example.chatapplication.utilities.PreferanceManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class SigninActivity extends AppCompatActivity {
        public ActivitySigninBinding binding;
        PreferanceManager preferanceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         binding = DataBindingUtil.setContentView(this, R.layout.activity_signin);
         preferanceManager=new PreferanceManager(getApplicationContext());
         if(preferanceManager.getBoolean(Constants.KEY_IS_SIGNED_IN))
         {
             Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
             startActivity(intent);
             finish();
         }
         setListener();


    }

    private void setListener()
    {
        binding.textCreateNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SigninActivity.this, MainActivity.class));
            }
        });

        binding.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValidSignUpDetails())
                {
                    signIn();
                }
            }
        });
    }

    private void signIn()
    {
        loading(true);
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection(Constants.KEY_COLLECTION_USERS)
                .whereEqualTo(Constants.KEY_EMAIL,binding.inputEmail.getText().toString())
                .whereEqualTo(Constants.KEY_PASSWORD,binding.inputPassword.getText().toString())
                .get()
                .addOnCompleteListener( task ->
                {
                    if(task.isSuccessful() && task.getResult() != null && task.getResult().getDocuments().size() >0)
                    {
                        DocumentSnapshot documentSnapshot = task.getResult().getDocuments().get(0);
                        preferanceManager.putBoolean(Constants.KEY_IS_SIGNED_IN,true);
                        preferanceManager.putString(Constants.KEY_USER_ID,documentSnapshot.getId());
                        preferanceManager.putString(Constants.KEY_NAME,documentSnapshot.getString(Constants.KEY_NAME));
                        preferanceManager.putString(Constants.KEY_IMAGE,documentSnapshot.getString(Constants.KEY_IMAGE));
                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                    else {
                        loading(false);
                        showToast("unable to sign in");
                    }
                });
    }

    private void loading(boolean isLoading)
    {
        if(isLoading)
        {
            binding.buttonSignIn.setVisibility(View.INVISIBLE);
            binding.progressBar.setVisibility(View.VISIBLE);
        }
        else{
            binding.buttonSignIn.setVisibility(View.VISIBLE);
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }


    private void showToast( String message)
    {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
    }


    private boolean isValidSignUpDetails()
    {
        if(binding.inputEmail.getText().toString().trim().isEmpty())
        {
            showToast("please enter your email");
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(binding.inputEmail.getText().toString()).matches() )
        {
            showToast("please enter valid email");
            return false;
        }
        else if(binding.inputPassword.getText().toString().trim().isEmpty())
        {
            showToast("please enter password");
            return false;
        }
        else{
            return true;
        }
    }
}
