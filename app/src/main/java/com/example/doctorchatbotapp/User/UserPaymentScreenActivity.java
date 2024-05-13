package com.example.doctorchatbotapp.User;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.doctorchatbotapp.ModelClass.AddPatientDetailToRealtym;
import com.example.doctorchatbotapp.R;
import com.example.doctorchatbotapp.User.ModelClass.AddItemToCartDetails;
import com.example.doctorchatbotapp.User.ModelClass.ConfrimCartDetails;
import com.example.doctorchatbotapp.User.ModelClass.PaymentModel;
import com.example.doctorchatbotapp.databinding.ActivityUserPaymentScreenBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserPaymentScreenActivity extends AppCompatActivity {
    private ActivityUserPaymentScreenBinding binding;
    FirebaseDatabase database;
    DatabaseReference reference;
    ArrayList<AddItemToCartDetails> datalist;
    String bankName;
    String userID,username,ownverID,fullName,cardNumber,securityCode,address,phoneNumber,totalPrice;
    private static final int PERMISSION_REQUEST_CODE = 100;
    FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUserPaymentScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent = getIntent();
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        userID = auth.getCurrentUser().getUid();
        datalist = (ArrayList<AddItemToCartDetails>) intent.getSerializableExtra("list");

        String[] items = {"HBL", "UBL", "Meezan Bank", "Bank of Punjab", "AlFalah Bank"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, items);
        binding.autoCompleteTextView.setAdapter(adapter);

        binding.autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bankName = (String) parent.getItemAtPosition(position);

                Toast.makeText(UserPaymentScreenActivity.this, "Selected Bank: " + bankName, Toast.LENGTH_SHORT).show();
            }
        });
        binding.payNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fullName = binding.name.getText().toString().trim();
                cardNumber = binding.cardNumber.getText().toString().trim();
                securityCode = binding.securityCode.getText().toString().trim();
                address = binding.address.getText().toString().trim();
                phoneNumber = binding.phone.getText().toString().trim();
                // Check if any of the fields are empty
                if (fullName.isEmpty() || cardNumber.isEmpty() || securityCode.isEmpty() || address.isEmpty() || phoneNumber.isEmpty()) {
                    // Show a toast message indicating that all fields are required
                    Toast.makeText(UserPaymentScreenActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                } else {
                    // Check if the phone number is valid (you can add more validation as needed)
                    if (phoneNumber.length() < 11) {
                        // Show a toast message indicating that the phone number is invalid
                        Toast.makeText(UserPaymentScreenActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    } else {
                        // If all conditions are met, proceed with sending the message and uploading data
                        sendMessage(fullName,cardNumber,securityCode,address,phoneNumber);
                    }
                }
            }
        });

        getUserData();


    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with sending message
                sendMessage(fullName, cardNumber, securityCode, address, phoneNumber);
            } else {
                // Permission denied, show a toast message or handle it accordingly
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendMessage(String fullName, String cardNumber, String securityCode, String address, String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, proceed with sending message
            SmsManager smsManager = SmsManager.getDefault();
            String message = username + " sent " + totalPrice + " to your Account " + " (Dr Chatbot App)";
            try {
                smsManager.sendTextMessage("03081561362", null, message, null, null);
                Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT).show();
                UploadCartData(fullName, bankName, cardNumber, securityCode, address, phoneNumber);
            } catch (Exception e) {
                Log.e("SendMessage", "Error sending message: " + e.getMessage());
                Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void uploadPaymentDetails(String fullName, String bankName, String cardNumber, String securityCode, String address, String phoneNumber) {
        if (userID != null) {
            database = FirebaseDatabase.getInstance();

            reference = database.getReference("Medicine Payment Details").child(ownverID).push();

            PaymentModel model = new PaymentModel(fullName, bankName, cardNumber, securityCode, address, phoneNumber);

            reference.setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(UserPaymentScreenActivity.this, "Receipt Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserPaymentScreenActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    private void UploadCartData(String fullName, String bankName, String cardNumber, String securityCode, String address, String phoneNumber) {
        for (int i = 0; i < datalist.size(); i++) {
            AddItemToCartDetails obj = datalist.get(i);

            String name = obj.getProductname();
            String image = obj.getProductImage();
            String productID = obj.getProductId();
            String quantity = obj.getProductquantity();
            String price = obj.getProductprice();
            ownverID = obj.getOwnerID();
            if (userID != null) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("UserCartDetails");
                ConfrimCartDetails obj2 = new ConfrimCartDetails(userID,productID,ownverID, image, price, quantity,name);
                reference.child(userID).child(productID).setValue(obj2).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            DeleteItems();
                            startActivity(new Intent(getApplicationContext(), PaymentAnimationsActivity.class));
                            uploadPaymentDetails(fullName, bankName, cardNumber, securityCode, address, phoneNumber);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserPaymentScreenActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }
    private void DeleteItems() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("UserCart").child("Details").child(userID);
        reference.removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully deleted
                        datalist.clear();

                        Log.d("DeleteData", "Data deleted successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Failed to delete data
                        Log.w("DeleteData", "Error deleting data", e);
                    }
                });
    }

    private void getUserData() {
        if (userID != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("ProfileUser").child("User").child(userID);
            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        AddPatientDetailToRealtym userDetail = snapshot.getValue(AddPatientDetailToRealtym.class);

                        if (userDetail != null) {
                            username = userDetail.getUsername();
                        } else {
                        }
                    } else {

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    //                 Toast.makeText(getContext(), "Canceled", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}