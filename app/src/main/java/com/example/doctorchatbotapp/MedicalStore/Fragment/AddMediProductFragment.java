package com.example.doctorchatbotapp.MedicalStore.Fragment;

import static android.app.Activity.RESULT_OK;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.doctorchatbotapp.MedicalStore.Adapter.MediViewProductAdapter;
import com.example.doctorchatbotapp.MedicalStore.MediModelClass.AddProdcutDetails;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.databinding.FragmentAddMediProductBinding;
import com.example.doctorchatbotapp.databinding.FragmentUnDoctorToUserBinding;
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

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;


public class AddMediProductFragment extends Fragment {

    private FragmentAddMediProductBinding binding;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 1;


    Uri imageUri;
    String productname, Productprice, productImageUrl;
    ProgressDialog dialog;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    Bitmap bitmap;

    FirebaseStorage storage;
    StorageReference storageReference;

    public AddMediProductFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddMediProductBinding.inflate(getLayoutInflater(),container,false);
        binding.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
        binding.uploadProductInfoButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 productname = binding.username.getText().toString();
                 Productprice = binding.productprice.getText().toString();
                 if (productname.isEmpty() || Productprice.isEmpty()){
                     Toast.makeText(requireActivity(), "Please Enter Details", Toast.LENGTH_SHORT).show();
                 }else{
                     dialog = new ProgressDialog(requireActivity());
                     dialog.setTitle("Please wait");
                     dialog.setMessage("uploading Data");
                     dialog.show();
                     UploadCaptureImage();
                 }

             }
         });
        return binding.getRoot();
    }
    public void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //IMAGE CAPTURE CODE
        startActivityForResult(intent, 0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) { // Use the same request code
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                Toast.makeText(requireContext(), "Camera permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            Bundle extras = data.getExtras();
            if (extras != null && extras.containsKey("data")) {
                // Retrieve the captured image bitmap
                bitmap = (Bitmap) extras.get("data");
                binding.circleImageView.setImageBitmap(bitmap);

                // Convert the bitmap to a URI
                imageUri = getImageUri(requireContext(), bitmap);

                if (imageUri != null) {
                    Log.d("ImageURI", "URI: " + imageUri.toString());

                } else {
                    Log.e("ImageURI", "URI is null");
                }
            } else {
                Log.e("ImageCapture", "No image data found in extras");
            }
        }
    }
    private Uri getImageUri(Context context, Bitmap bitmap) {
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
            return Uri.parse(path);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void UploadCaptureImage(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        storageReference = storageReference.child("images/"
                + UUID.randomUUID().toString());
        storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        productImageUrl = uri.toString();
                        uploadData(productImageUrl);
                    }
                });
            }
        });
    }
    private void uploadData(String url) {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser(); // Initialize user

        if (user != null) {
            String userID = user.getUid().toString();
            reference = database.getReference("Admin Product").child("Product Details");
            String productID = reference.push().getKey().toString();

            AddProdcutDetails obj = new AddProdcutDetails(userID, productID, productname,url,Productprice);
            reference.child(productID).setValue(obj).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "Product Added Successfully", Toast.LENGTH_SHORT).show();
                        dialog.hide();

                        Log.e("DSCheck", "Successful");

                    } else {
                        dialog.hide();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    dialog.hide();
                    Toast.makeText(requireContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}