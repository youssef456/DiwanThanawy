package com.brayooteck.youssef.home;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.brayooteck.youssef.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class CommentsRecyclerAdapter extends RecyclerView.Adapter<CommentsRecyclerAdapter.ViewHolder> {
    private ImageView userImageView;
    ImageView commentimage;
    public List<Comments> commentsList;
    public Context context;
    private StorageReference storageReference;
    String imageurl;
    FirebaseFirestore firebaseFirestore;
    String notificationid;
    int pos;
    public CommentsRecyclerAdapter(List<Comments> commentsList){

        this.commentsList = commentsList;

    }

    @Override
    public CommentsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_list_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new CommentsRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CommentsRecyclerAdapter.ViewHolder holder, int position) {
        pos = position;
        holder.setIsRecyclable(false);

        String commentMessage = commentsList.get(position).getMessage();
        holder.setComment_message(commentMessage);
        imageurl = commentsList.get(position).getcommentimage();
        String user_id = commentsList.get(position).getUser_id();
        notificationid = commentsList.get(position).getnotificationid();
        Log.i("Info", "notid2" + notificationid);

/////////////////////////////////////////////////////////////////////////////////////////////////////////getiing user data/////////////////////////////////////////////////////////////

        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    String userName = task.getResult().getString("name");
                    String userImage = task.getResult().getString("image");
                    String imageclear = userImage + "/picture?type=small";


                    holder.setuserimage(imageclear);
                    holder.setusername(userName);
                    Log.i("Info", "cangetinfo");
                    //              Log.i("Info", userName);


                } else {

                    //Firebase Exception
                    Log.i("Info", "cantgetinfo");

                }

            }
        });
// ////////////////////////////////////////////////////////////////////////////////////////////////
        if (imageurl != null) {
            Log.i("Info","notnull");

            holder.setcommentimage(imageurl);
        }else {
            Log.i("Info","fuckingnull");

        }
        if(BlogRecyclerAdapter.currentUserId.equals(user_id)){
            Log.i("Info" ,"useridcurrent" + BlogRecyclerAdapter.currentUserId);
            Log.i("Info" ,"useridcommenter" + user_id);
            holder.adddeletebutton();
        }

    }


    @Override
    public int getItemCount() {

        if(commentsList != null) {

            return commentsList.size();

        } else {

            return 0;

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private TextView comment_message;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setComment_message(String message){

            comment_message = mView.findViewById(R.id.comment_message);
            comment_message.setText(message);

        }


    public void setuserimage(String userimage) {

        userImageView = mView.findViewById(R.id.comment_image);

        RequestOptions placeholderOption = new RequestOptions();
        placeholderOption.placeholder(R.drawable.profile_placeholder);

        Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(userimage).into(userImageView);
    }
        public void setusername(String username){

            comment_message = mView.findViewById(R.id.comment_username);
            comment_message.setText(username);

        }
        public void setcommentimage(String imageurl) {

            commentimage = mView.findViewById(R.id.commented_image);
            commentimage.setVisibility(View.VISIBLE);
            RequestOptions placeholderOption = new RequestOptions();
            placeholderOption.placeholder(R.drawable.profile_placeholder);

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(imageurl).into(commentimage);
        }
        public void adddeletebutton(){
            TextView deletebutton = mView.findViewById(R.id.deletebutton);
            deletebutton.setVisibility(View.VISIBLE);
            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deletecomment();
                }
            });

        }
    }
    public void deletecomment(){
        if(imageurl != null) {
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(imageurl);
            storageReference.delete();
            Log.i("Info","imagedeleted");

        }
        firebaseFirestore.collection("Posts/" + CommentsActivity.blog_post_id + "/Comments").document(CommentsActivity.commentid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Info","commentdeleted");
                CommentsActivity.commentsRecyclerAdapter.notifyDataSetChanged();
                Toast.makeText(context, "comments was deleted", Toast.LENGTH_LONG).show();


            }});

        firebaseFirestore.collection("Users/" + CommentsActivity.posterid + "/Notifications").document(notificationid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.i("Info","notificationdeleted");

            }
        });

            }


}
