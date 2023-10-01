package com.brayooteck.youssef.notifications;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NotificationFragment extends Rootfragment {

    private RecyclerView notification_list_view;
    private List<notifications> Notification_list;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private NotificationsAdapter notificationsAdapter;
    private String current_user_id;
    public static String notificationid;
    public static FragmentManager fragmentManager;
    Menu menu;
    MenuInflater inflater;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        setHasOptionsMenu(true);
        Notification_list = new ArrayList<>();
        notification_list_view = view.findViewById(R.id.notification_list_view);
        Log.i("Info", "notificationsfragment ");
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
        if(mFirebaseUser != null) {
            current_user_id = mFirebaseUser.getUid();
        }else{
         //   goLoginScreen();
        }
        firebaseFirestore = FirebaseFirestore.getInstance();
        notificationsAdapter = new NotificationsAdapter(Notification_list,getActivity());
        notification_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        notification_list_view.setAdapter(notificationsAdapter);
        notification_list_view.setHasFixedSize(true);

        firebaseFirestore.collection("Users/" + current_user_id +"/Notifications").orderBy("timestamp", Query.Direction.DESCENDING)
                .addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        if (documentSnapshots != null){
                        if (!documentSnapshots.isEmpty()) {

                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                                if (doc.getType() == DocumentChange.Type.ADDED) {

                                    notifications notification = doc.getDocument().toObject(notifications.class);
                                    notificationid = doc.getDocument().getId();
                                    Notification_list.add(notification);
                                    notificationsAdapter.notifyDataSetChanged();

                                  //  notificationmaneger(Notification_list);


                                }
                            }

                        }else {
                            Log.i("Info", "notificationsnotdownloaded ");
                        }
                        }
                    }
                });


        return view;

    }
    public static void setfragment(Fragment fragment){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.notificationcontainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
        fragmentManager = getChildFragmentManager();
        if(menu != null){
            menu.clear();
            setHasOptionsMenu(true);
        }

    }
}
