package com.example.doctorchatbotapp.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class UserProfileActivity extends AppCompatActivity {
    private final int PICK_IMAGE_REQUEST = 22;
    private Uri filePath;
    ImageView imagepick;
    String currentUser;
    ProgressDialog dialog;
    EditText edname;
    FirebaseStorage storage;
    Button btncreateprofile;
    private FirebaseAuth mAuth;
    StorageReference storageReference;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        dialog = new ProgressDialog(UserProfileActivity.this);
        dialog.setMessage("please wait...");
        Ids();
        Clicklistener();
    }

    private void Ids() {
        imagepick = findViewById(R.id.imagepickID);
        edname = findViewById(R.id.name);
        btncreateprofile = findViewById(R.id.btncreateID);
    }

    private void Clicklistener() {
        imagepick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickImage();
            }
        });
        btncreateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = edname.getText().toString().trim();
                if (username.isEmpty()
                ) {
                    Toast.makeText(getApplicationContext(), "Please Fill Details", Toast.LENGTH_SHORT).show();
                } else {
                    UploadImageNdDetails();
                }
            }
        });
    }

    private void PickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,
                resultCode,
                data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),
                        filePath);
                imagepick.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void UploadImageNdDetails() {
        dialog.show();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ProfileUser").child("User");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        currentUser = user.getUid().toString();
        if (filePath != null) {
            StorageReference ref = storageReference.child("images/"
                    + UUID.randomUUID().toString());
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            String imageurl = uri.toString();
                                            AddPatientDetailToRealtym obj = new AddPatientDetailToRealtym(currentUser, username, imageurl,"","notprove","notbook");
                                            databaseReference.child(currentUser).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(UserProfileActivity.this, UserDashboardActivity.class);
                                                        startActivity(intent);
                                                        Toast.makeText(getApplicationContext(), "Uploaded", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });
        } else {
            Toast.makeText(getApplicationContext(), "please Select the Image", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUsers = user.getUid().toString();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference profilesRef = database.getReference("ProfileUser").child("User");
        profilesRef.child(currentUsers).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    startActivity(new Intent(UserProfileActivity.this, UserDashboardActivity.class));
                } else {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // An error occurred while querying the database
                Toast.makeText(UserProfileActivity.this, "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}