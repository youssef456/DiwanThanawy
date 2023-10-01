package com.brayooteck.youssef.notifications;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.mymainactivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {
    ImageView commentimage;
    public List<notifications> notificationsList;
    public Context context;
    private FirebaseFirestore firebaseFirestore;
    String blogpostid;
    String userName;
    String notificationtype;
    View view;
    Context c;
    public NotificationsAdapter(List<notifications> notificationsList,Context context){

        this.notificationsList = notificationsList;
        this.c =context;
    }

    @Override
    public NotificationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        return new NotificationsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final NotificationsAdapter.ViewHolder holder, int position) {

        holder.setIsRecyclable(false);
        final String user_id = notificationsList.get(position).getUser_id();
        notificationtype = notificationsList.get(position).getnotificationstype();
        String comment = notificationsList.get(position).getcomment();
        blogpostid = notificationsList.get(position).getBlogpostid();
        holder.setComment_message(comment);
        long millisecond = notificationsList.get(position).getTimestamp().getTime();
        String dateString = DateFormat.format("MM/dd/yyyy", new Date(millisecond)).toString();
        holder.setTime(dateString);
        Log.i("Info", "notificationis" + notificationtype);
        Log.i("Info", "notificationis" + notificationtype);
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        firebaseFirestore.collection("Users").document(user_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    userName = task.getResult().getString("name");
                    String userImage = task.getResult().getString("image");
                    String imageclear = userImage + "/picture?type=small";

                    holder.setusername(userName,notificationtype);
                    holder.setcommenterimage(imageclear);
                //    shownotification();
                    notificationmanager(NotificationFragment.notificationid);

                    Log.i("Info", "cangetinfo");

                } else {

                    //Firebase Exception
                    Log.i("Info", "cantgetinfo");

                }

            }
        });

            view.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            opennotification open = new opennotification();
            open.blogpostid = blogpostid;
            open.c = c;
            Log.i("Info", "openingnotification");
            //     open.currentUserId = user_id;
            NotificationFragment.setfragment(open);

        }
    });
    }


    @Override
    public int getItemCount() {

        if(notificationsList != null) {

            return notificationsList.size();

        } else {

            return 0;

        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        private TextView comment_user;
        private TextView comment_message;
        private TextView blogDate;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }


        public void setusername(String username, String notiype){

            comment_user = mView.findViewById(R.id.username);
            comment_user.setText(username + notiype);

        }

        public void setTime(String date) {

            blogDate = mView.findViewById(R.id.blog_date);
            blogDate.setText(date);

        }
        public void setComment_message(String comment){

            comment_message = mView.findViewById(R.id.comment);
            if (comment == null){
                comment_message.setVisibility(View.GONE);
            }else {
                comment_message.setVisibility(View.VISIBLE);
                comment_message.setText(comment);

            }

        }
        public void setcommenterimage(String imageurl) {

            commentimage = mView.findViewById(R.id.image_profile);
            commentimage.setVisibility(View.VISIBLE);
            RequestOptions placeholderOption = new RequestOptions();

            Glide.with(context).applyDefaultRequestOptions(placeholderOption).load(imageurl).into(commentimage);
        }
    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void notificationmanager(String notificationid){
        Log.i("Info","notmanager" + notificationid);
        ArrayList<String> lst = getArrayList("notifications");
        Log.i("Info","listis" + lst);
        if (!lst.contains(notificationid)){
            lst.add(lst.size(),notificationid);
            saveArrayList(lst,"notifications");
            mymainactivity.notificationdot.setVisibility(View.VISIBLE);
            shownotification();
            Log.i("Info","listissaved" + lst);
        }

    }

    void shownotification(){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(c, mymainactivity.class);
        intent.putExtra("From", "notifyFrag");//// to  open fragment
        intent.putExtra("blogpostid", blogpostid);//to pass blogpostid
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(c, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(c, "channelid")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("My Thanawy")
                //    .setContentText("Hello World!")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(userName + notificationtype))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(c);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    public void saveArrayList(ArrayList<String> list, String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(list);
        editor.putString(key, json);
        editor.apply();     // This line is IMPORTANT !!!
    }

    public ArrayList<String> getArrayList(String key){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(c);
        Gson gson = new Gson();
        String json = prefs.getString(key, "[notification]");
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(json, type);
    }
}
