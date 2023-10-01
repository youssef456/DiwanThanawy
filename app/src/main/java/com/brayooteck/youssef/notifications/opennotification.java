package com.brayooteck.youssef.notifications;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;
import com.brayooteck.youssef.home.CommentsActivity;
import com.brayooteck.youssef.home.HomeFragment;
import com.brayooteck.youssef.home.imageviewer;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class opennotification extends Rootfragment {
public String blogpostid;
String currentUserId;
private StorageReference storageReference;
private FirebaseFirestore firebaseFirestore;
private FirebaseAuth firebaseAuth;
private ImageView blogLikeBtn;
private TextView blogLikeCount;
private TextView blogcommentCount;
private ImageView blogCommentBtn;
public Date timestamp;
String postimage;
private TextView descView;
private ImageView blogImageView;
private TextView blogDate;
private TextView blogUserName;
LinearLayout nopost;
private CircleImageView blogUserImage;
public static Context c;


    public opennotification() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        Fresco.initialize(getActivity());
        //final View view = inflater.inflate(R.layout.blog_list_item, container, false);
        final View view = inflater.inflate(R.layout.opennotification, container, false);
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        nopost = (LinearLayout) view.findViewById(R.id.nopost);
        nopost.setVisibility(View.GONE);
        blogLikeCount = view.findViewById(R.id.blog_like_count);
        blogLikeBtn = view.findViewById(R.id.blog_like_btn);
        blogcommentCount = view.findViewById(R.id.blog_comment_count);
        blogCommentBtn = view.findViewById(R.id.blog_comment_icon);
        blogDate = view.findViewById(R.id.blog_date);
        descView = view.findViewById(R.id.blog_desc);
        blogImageView = view.findViewById(R.id.blog_image);
        blogUserImage = view.findViewById(R.id.blog_user_image);
        blogUserName = view.findViewById(R.id.blog_user_name);


        TextView deletebutton = view.findViewById(R.id.deletebutton);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletepost();
            }
        });



            firebaseFirestore.collection("Users").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        String userName = task.getResult().getString("name");
                        String userImage = task.getResult().getString("image");
                        String clearimage = userImage + "/picture?type=small";
                        ///////////////////////////////
                        blogUserName.setText(userName);
                        RequestOptions placeholderOption = new RequestOptions();
                        placeholderOption.placeholder(R.drawable.profile_placeholder);
                        //       Glide.with(getContext()).applyDefaultRequestOptions(placeholderOption).load(clearimage).into(blogUserImage);
                        Glide.with(c).applyDefaultRequestOptions(placeholderOption).load(clearimage).into(blogUserImage);
                        Log.i("Info", "cangetinfo");

                    } else {

                        //Firebase Exception
                        Log.i("Info", "cantgetinfo");

                    }

                }
            });


/////////////////////////////////getpost/////////////////////////////////////////
            firebaseFirestore.collection("Posts").document(blogpostid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {
//////////////////////////////////////////////////////////////////////////////////////////
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot != null && documentSnapshot.exists()) {              //check if document exists

                            Log.i("Info", "exists");

                            String desc = task.getResult().getString("desc");
                            descView.setText(desc);
                            Log.i("Info", "desc" + desc);
                            /////////////////////////////////////////////////////////////////////////////
                            postimage = task.getResult().getString("image_url");
                            Log.i("Info", "url" + postimage);
                            Log.i("Info", "bbbb" + blogpostid);
                            if (postimage != null) {
                                Uri imageUri = Uri.parse(postimage);
                                SimpleDraweeView draweeView = (SimpleDraweeView) blogImageView;
                                draweeView.setImageURI(imageUri);
                                blogImageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent mathactivity = new Intent(c, imageviewer.class);
                                        imageviewer.imageurl = postimage;
                                        getActivity().startActivity(mathactivity);
                                    }
                                });
                                Log.i("Info", "imageurl" + postimage);
                            } else {
                                Log.i("Info", "noimageurl");
                            }

                            /////////////////////////////////////////////////////////////////////////////////////////
                            Date millisecond = task.getResult().getDate("timestamp");
                            if (millisecond != null) {
                                long date = millisecond.getTime();
                                String dateString = DateFormat.format("MM/dd/yyyy", new Date(date)).toString();
                                blogDate.setText(dateString);
                            }
                            gettherest();
                            ///////////////////////////////////////////////////////////////////
                            //   String userName = task.getResult().getString("user_id");

                            //Log.i("Info", "cangetinfo");


                        }else{
                            Log.i("Info", "existsnot");
                            nopost.setVisibility(View.VISIBLE);



                        }
/////////////////////////////////////////////////////////////////////////////////

                    } else {

                        //Firebase Exception
                        Log.i("Info", "cantgetinfo");

                    }

                }
            });
            ///////////////////////////////////////////////////////////////////



        return view;
}
    void gettherest(){
        //Get Likes Count
        firebaseFirestore.collection("Posts/" + blogpostid + "/Likes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    int count = documentSnapshots.size();

                    blogLikeCount.setText(count + " Likes");

                } else {

                    blogLikeCount.setText(0 + " Likes");


                }

            }
        });


        //Get Likes
        firebaseFirestore.collection("Posts/" + blogpostid + "/Likes").document(currentUserId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {

                    blogLikeBtn.setImageDrawable(c.getDrawable(R.mipmap.action_like_accent));

                } else {

                    blogLikeBtn.setImageDrawable(c.getDrawable(R.mipmap.action_like_gray));

                }

            }
        });
        firebaseFirestore.collection("Posts/" + blogpostid + "/Comments").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                if (!documentSnapshots.isEmpty()) {

                    int count = documentSnapshots.size();

                    blogcommentCount.setText(count + " comments");

                } else {

                    blogcommentCount.setText(0 + " comments");

                }

            }
        });

        //Likes Feature
        blogLikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseFirestore.collection("Posts/" + blogpostid + "/Likes").document(currentUserId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (!task.getResult().exists()) {

                            Map<String, Object> likesMap = new HashMap<>();
                            likesMap.put("timestamp", FieldValue.serverTimestamp());

                            firebaseFirestore.collection("Posts/" + blogpostid + "/Likes").document(currentUserId).set(likesMap);
                        } else {

                            firebaseFirestore.collection("Posts/" + blogpostid + "/Likes").document(currentUserId).delete();

                        }

                    }
                });
            }


        });

        blogCommentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CommentsActivity commentsActivity = new CommentsActivity();
                commentsActivity.blog_post_id = blogpostid;
                NotificationFragment.setfragment(commentsActivity);

            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////

    }
    public void deletepost(){
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(postimage);
        storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Info","imagedeleted");
                firebaseFirestore.collection("Posts/").document(blogpostid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i("Info","postdeleted");
                        HomeFragment.blogRecyclerAdapter.notifyDataSetChanged();
                    }

                });
            }
        });



    }


}
