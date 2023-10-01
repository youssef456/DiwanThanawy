package com.brayooteck.youssef.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import id.zelory.compressor.Compressor;

public class NewPostActivity extends Rootfragment {

    private ImageView newPostImage;
    private Uri postImageUri = null;
    private ProgressBar newPostProgress;
    private StorageReference storageReference;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private Bitmap compressedImageFile;
    String downloadUri;
    EmojiconEditText emojiconEditText;
    EmojIconActions emojIconActions;
    View rootview;
    ImageView imageView;
    Menu menu;
    MenuInflater inflater;
    public NewPostActivity() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_new_post, container, false);
        setHasOptionsMenu(true);
        storageReference = FirebaseStorage.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        rootview = view.findViewById(R.id.rootlayout);
        emojiconEditText =(EmojiconEditText) view.findViewById(R.id.new_post_desc);
        imageView = (ImageView) view.findViewById(R.id.imageView);
        emojIconActions = new EmojIconActions(getActivity(),rootview,emojiconEditText,imageView);
        emojIconActions.ShowEmojIcon();
        newPostImage = view.findViewById(R.id.new_post_image);
        newPostProgress = view.findViewById(R.id.new_post_progress);

        newPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(getContext(), NewPostActivity.this);

            }
        });



        return view;
    }

    void post(){


    final String desc = emojiconEditText.getText().toString();

    if(!TextUtils.isEmpty(desc) && postImageUri != null){
        menu.findItem(R.id.post).setTitle("Posting");     // to prevent clicking twice
        menu.findItem(R.id.post).setEnabled(false);
        newPostProgress.setVisibility(View.VISIBLE);

        final String randomName = UUID.randomUUID().toString();

        // PHOTO UPLOAD
///        File newImageFile = new File(postImageUri.getPath());
///        try {
///
///            compressedImageFile = new Compressor(getActivity())
///                    .setQuality(75)
///                    .compressToBitmap(newImageFile);
///
///        } catch (IOException e) {
///            e.printStackTrace();
///        }
///
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
///        compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        // PHOTO UPLOAD

        UploadTask filePath = storageReference.child("post_images").child(randomName + ".jpg").putBytes(imageData);
        filePath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                Task<Uri> newTask = task.getResult().getMetadata().getReference().getDownloadUrl();
                newTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        downloadUri = uri.toString();
                        Log.i("Info","onsucess");

                        Map<String, Object> postMap = new HashMap<>();
                        postMap.put("image_url", downloadUri);
                        postMap.put("desc", desc);
                        postMap.put("user_id", current_user_id);
                        postMap.put("timestamp", FieldValue.serverTimestamp());
                        Log.i("Info","imageurlnn"  + downloadUri);

                        firebaseFirestore.collection("Posts").add(postMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Log.i("Info","taskcompleted");

                                if(task.isSuccessful()){

                                    Toast.makeText(getActivity(), "Post was added", Toast.LENGTH_LONG).show();
                                    HomeFragment homeFragment = new HomeFragment();
                                    homeFragment.setfragment(homeFragment);
                                    hidekeyboard();
                                } else {
                                    Toast.makeText(getActivity(), "failed", Toast.LENGTH_LONG).show();
                                    Log.i("Info","taskfailed");

                                }

                                newPostProgress.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                });
            }
        });
    }else
    {
        Log.i("Info","noimage");
        Log.i("Info","image" +postImageUri);
    }
}
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {

                postImageUri = result.getUri();
                newPostImage.setImageURI(postImageUri);
                Log.i("Info","croped" + postImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();
                Log.i("Info","croperror");


            }
        }

    }
//////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        this.menu = menu;
        this.inflater = inflater;


    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        inflater.inflate(R.menu.postmenu, menu);
        menu.findItem(R.id.post).setVisible(true);
        menu.findItem(R.id.post).setTitle("Post");
        menu.findItem(R.id.post).setEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.post){
          post();
        }
        return super.onOptionsItemSelected(item);

    }
    @Override
    public void onPause() {
        super.onPause();
        Log.i("Info","pauseact");
   //     menu.clear();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(menu != null){
            menu.clear();
            setHasOptionsMenu(false);
            setHasOptionsMenu(true);
            Log.i("Info","pauseacti");

        }else {
            setHasOptionsMenu(true);

        }

    }

    //////////////////////////////////////////////////////////////////////////////////////////hidekeyboard////////////////////////////////////////
    void hidekeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
