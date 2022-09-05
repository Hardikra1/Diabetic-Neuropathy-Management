package Menu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.diabetesmanagement.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class DiabetesDetectionFragment extends Fragment {

    private ImageView Fig1,Fig2,Fig3,Fig4,Fig5,Fig6;

    private FirebaseStorage firebaseStorage;
    private static int PICK_IMAGE1 = 123;
    private static int PICK_IMAGE2 = 345;
    private static int PICK_IMAGE3 = 567;
    private static int PICK_IMAGE4 = 789;
    private static int PICK_IMAGE5 = 910;
    private static int PICK_IMAGE6 = 011;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    Uri path1;
    Uri path2;
    Uri path3;
    Uri path4;
    Uri path5;
    Uri path6;

    private StorageReference storageReference;

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == PICK_IMAGE1 ){
            path1 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path1);
                Fig1.setImageBitmap(bitmap);
                //Updating data
                StorageReference imageRef = storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig1");
                UploadTask uploadTask = imageRef.putFile(path1);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Image Upoading Failed.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Image Uploaded.",Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == PICK_IMAGE2 ){
            path2 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path2);
                Fig2.setImageBitmap(bitmap);
                //Updating data

                StorageReference imageRef = storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig2");
                UploadTask uploadTask = imageRef.putFile(path2);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Image Upoading Failed.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Image Uploaded.",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == PICK_IMAGE3 ){
            path3 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path3);
                Fig3.setImageBitmap(bitmap);
                //updating data
                StorageReference imageRef = storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig3");
                UploadTask uploadTask = imageRef.putFile(path3);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Image Upoading Failed.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Image Uploaded.",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == PICK_IMAGE4 ){
            path4 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path4);
                Fig4.setImageBitmap(bitmap);
                //Updating data
                StorageReference imageRef = storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig4");
                UploadTask uploadTask = imageRef.putFile(path4);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Image Upoading Failed.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Image Uploaded.",Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == PICK_IMAGE5 ){
            path5 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path5);
                Fig5.setImageBitmap(bitmap);
                //updating data
                StorageReference imageRef = storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig5");
                UploadTask uploadTask = imageRef.putFile(path5);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Image Upoading Failed.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Image Uploaded.",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(requestCode == PICK_IMAGE6 ){
            path6 = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path6);
                Fig6.setImageBitmap(bitmap);
                //Updating Data
                StorageReference imageRef = storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig6");
                UploadTask uploadTask = imageRef.putFile(path6);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Image Upoading Failed.",Toast.LENGTH_SHORT).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getActivity(), "Image Uploaded.",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_diabetesdetection,container,false);
        Fig1 =(ImageView)view.findViewById(R.id.fig1);
        Fig2 =(ImageView)view.findViewById(R.id.fig2);
        Fig3 =(ImageView)view.findViewById(R.id.fig3);
        Fig4 =(ImageView)view.findViewById(R.id.fig4);
        Fig5 =(ImageView)view.findViewById(R.id.fig5);
        Fig6 =(ImageView)view.findViewById(R.id.fig6);


        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        Fig1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");//application/* audio/*
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE1);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE2);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE3);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE4);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE5);
                startActivityForResult(Intent.createChooser(intent,"Select image"), PICK_IMAGE6);
            }
        });

        final StorageReference storageReference = firebaseStorage.getReference();
        //Load the image
        storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig6").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(Fig6);

            }
        });

        storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig1").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(Fig1);
            }
        });
        storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig2").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(Fig2);
            }
        });

        storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig3").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(Fig3);
            }
        });

        storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig4").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(Fig4);
            }
        });

        storageReference.child(firebaseAuth.getUid()).child("Images/Diabetes/Fig5").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().into(Fig5);
            }
        });

        return view;
    }
}
