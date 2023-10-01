package com.brayooteck.youssef.account;

import android.os.Bundle;
import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.brayooteck.youssef.Rootfragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.brayooteck.youssef.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class account extends Rootfragment {
    private FirebaseAuth firebaseAuth;
    private String current_user_id;
    private FirebaseFirestore firebaseFirestore;
    Menu menu;
    MenuInflater inflater;
    public account(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.account, container, false);
        setHasOptionsMenu(true);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
        if (mFirebaseUser != null) {
            current_user_id = mFirebaseUser.getUid();
            firebaseFirestore = FirebaseFirestore.getInstance();
            firebaseFirestore.collection("Users").document(current_user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful()) {

                        String userName = task.getResult().getString("name");
                        String userImage = task.getResult().getString("image");
                        String imageclear = userImage + "/picture?type=large";

                        setUserData(view,userName, imageclear);


                    } else {


                    }

                }
            });
        } else {
            //   goLoginScreen();
        }

       return view;
    }



        public void setUserData(View view,String name, String image){

         final CircleImageView blogUserImage = view.findViewById(R.id.accountimage);
         TextView blogUserName = view.findViewById(R.id.accountname);

         blogUserName.setText(name);

            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.profile_placeholder);

            Glide.with(view.getContext()).applyDefaultRequestOptions(placeholderOption).load(image).into(blogUserImage);


        }
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
    }
    @Override
    public void onResume() {
        super.onResume();
        if(menu != null){
            menu.clear();
            setHasOptionsMenu(true);
        }

    }
}
