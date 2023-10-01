package com.brayooteck.youssef.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import id.zelory.compressor.Compressor;

public class CommentsActivity extends Rootfragment {

    public static CommentsRecyclerAdapter commentsRecyclerAdapter;
    public static String blog_post_id;
    public  static String posterid;
    private ImageView comment_post_btn;
    private ImageView comment_image;
    private Bitmap compressedImageFile;
    private RecyclerView comment_list;
    private List<Comments> commentsList;
    private Uri postImageUri = null;
    private ProgressBar newPostProgress;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private StorageReference storageReference;
    private String downloadUri;
    Map<String, Object> commentsMap = new HashMap<>();
    EmojiconEditText emojiconEditText;
    EmojIconActions emojIconActions;
    View rootview;
    ImageView imageView;
    String comment_message;
    public static String commentid;
    String notificationid;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_comments, container, false);
        rootview = view.findViewById(R.id.commentlayout);
        emojiconEditText =(EmojiconEditText) view.findViewById(R.id.comment_field);
        imageView = (ImageView) view.findViewById(R.id.commentemoji);

        emojIconActions = new EmojIconActions(getActivity(),rootview,emojiconEditText,imageView);
        emojIconActions.ShowEmojIcon();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();

        current_user_id = firebaseAuth.getCurrentUser().getUid();

        comment_post_btn = view.findViewById(R.id.comment_post_btn);
        comment_list = view.findViewById(R.id.comment_list);
        comment_image = view.findViewById(R.id.comment_image);
        newPostProgress = view.findViewById(R.id.new_post_progress);

        commentsList = new ArrayList<>();

        commentsRecyclerAdapter = new CommentsRecyclerAdapter(commentsList);
        comment_list.setHasFixedSize(true);
        comment_list.setLayoutManager(new LinearLayoutManager(getActivity()));
        comment_list.setAdapter(commentsRecyclerAdapter);
        comment_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setMinCropResultSize(512, 512)
                        .setAspectRatio(1, 1)
                        .start(getContext(), CommentsActivity.this);

            }
        });

        firebaseFirestore.collection("Posts/" + blog_post_id + "/Comments")
                .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                        if (doc.getType() == DocumentChange.Type.ADDED) {
                            commentid = doc.getDocument().getId();
                            Log.i("Info",commentid + "commentid");
                            Comments comments = doc.getDocument().toObject(Comments.class);
                            commentsList.add(comments);
                            commentsRecyclerAdapter.notifyDataSetChanged();


                        }
                    }

                }

            }
        });

        comment_post_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                comment_message = emojiconEditText.getText().toString();


                    commentsMap.put("message", comment_message);
                    commentsMap.put("user_id", current_user_id);
                    commentsMap.put("timestamp", FieldValue.serverTimestamp());
                    if(postImageUri != null){
                        newPostProgress.setVisibility(View.VISIBLE);
                        final String randomName = UUID.randomUUID().toString();

                        // PHOTO UPLOAD
///                        File newImageFile = new File(postImageUri.getPath());
///                        try {
///
///                            compressedImageFile = new Compressor(getActivity())
///                                    .setMaxHeight(720)
///                                    .setMaxWidth(720)
///                                    .setQuality(50)
///                                    .compressToBitmap(newImageFile);
///
///                        } catch (IOException e) {
///                            e.printStackTrace();
///                        }
///
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
///                        compressedImageFile.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                        byte[] imageData = baos.toByteArray();

                        // PHOTO UPLOAD

                        UploadTask filePath = storageReference.child("comments_images").child(randomName + ".jpg").putBytes(imageData);
                        filePath.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull final Task<UploadTask.TaskSnapshot> task) {
                                Task<Uri> newTask = task.getResult().getMetadata().getReference().getDownloadUrl();
                                newTask.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        downloadUri = uri.toString();
                                        commentsMap.put("commentimage" , downloadUri);
                                        Log.i("Info", "   fuck  " + downloadUri );
                                        newPostProgress.setVisibility(View.INVISIBLE);
                                  //      commentsRecyclerAdapter.notifyDataSetChanged();


                                    }
                                });
                            }
                        });
                        Log.i("Info", "shit" + downloadUri );



                    }
                    if(!posterid.equals(current_user_id)){
                        Log.i("Info","posterid" + posterid);
                        Log.i("Info","currentid" + current_user_id);
                      addnotifications();
                    }else {
                    }

            }
        });
return view;


    }

    //////////////////////////////////////////habd altnen//////////////////////////////////
    private  void addnotifications(){
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Blogpostid",blog_post_id);
        hashMap.put("user_id", current_user_id);
        hashMap.put("notificationstype",  " commented on your post");
        hashMap.put("comment", comment_message);
        hashMap.put("timestamp", FieldValue.serverTimestamp());

        firebaseFirestore.collection("Users/" + posterid + "/Notifications").add(hashMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                if(task.isSuccessful()){
                    notificationid = task.getResult().getId();
                    Log.i("Info","notid " + notificationid);
                    commentsMap.put("notificationid", notificationid);
                    post();

                    Toast.makeText(getActivity(), "comments was added", Toast.LENGTH_LONG).show();


                } else {

                    Log.i("Info","notificationfialed");


                }

                newPostProgress.setVisibility(View.INVISIBLE);

            }
        });


    }


    ////////////////////////////mywierd///////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == getActivity().RESULT_OK) {

                postImageUri = result.getUri();
                comment_image.setImageURI(postImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }
    public void post(){
        Log.i("Info","blogpostid" + blog_post_id);

        firebaseFirestore.collection("Posts/" + blog_post_id + "/Comments").add(commentsMap).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                if(!task.isSuccessful()){

                    Toast.makeText(getActivity(), "Error Posting Comment : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                } else {

                    emojiconEditText.setText("");
                    commentsRecyclerAdapter.notifyDataSetChanged();


                }

            }
        });
    }
}
