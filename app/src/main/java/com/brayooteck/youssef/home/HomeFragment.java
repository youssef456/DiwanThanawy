package com.brayooteck.youssef.home;


import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Rootfragment implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView blog_list_view;
    private List<BlogPost> blog_list;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    public static BlogRecyclerAdapter blogRecyclerAdapter;
    private DocumentSnapshot lastVisible;
    private Boolean isFirstPageFirstLoad = true;
    MenuInflater inflater;
    static FragmentManager fragmentManager;
    BlogRecyclerAdapter.ViewHolder holder;
    Menu menu;
    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setHasOptionsMenu(true);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.container);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        blog_list = new ArrayList<>();
        blog_list_view = view.findViewById(R.id.blog_list_view);
        firebaseAuth = FirebaseAuth.getInstance();
        //  current_user_id = firebaseAuth.getCurrentUser().getUid();
        FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
        Log.i("Info", "homefragment");
        firebaseFirestore = FirebaseFirestore.getInstance();
        ////////////removing cache//////////////////////////////
      //  FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
      //          .setPersistenceEnabled(false)
      //          .build();
      //  firebaseFirestore.setFirestoreSettings(settings);
        //////////////////////////////////////////////////////////

        blogRecyclerAdapter = new BlogRecyclerAdapter(blog_list);
        blog_list_view.setLayoutManager(new LinearLayoutManager(container.getContext()));
        blog_list_view.setAdapter(blogRecyclerAdapter);
        blog_list_view.setHasFixedSize(true);

        loadposts();

        return view;

    }
    void loadposts(){
        if(firebaseAuth.getCurrentUser() != null) {

            firebaseFirestore = FirebaseFirestore.getInstance();

            blog_list_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    Boolean reachedBottom = !recyclerView.canScrollVertically(1);

                    if(reachedBottom){

                        loadMorePost();

                    }

                }
            });

            Query firstQuery = firebaseFirestore.collection("Posts").orderBy("timestamp", Query.Direction.DESCENDING).limit(3);
            firstQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {

                        if (isFirstPageFirstLoad) {

                            lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                            blog_list.clear();

                        }

                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String blogPostId = doc.getDocument().getId();
                                BlogPost blogPost = doc.getDocument().toObject(BlogPost.class).withId(blogPostId);

                                if (isFirstPageFirstLoad) {

                                    blog_list.add(blogPost);

                                } else {
                                    fragmentManager = getChildFragmentManager();
                                    blog_list.add(0, blogPost);

                                }


                                blogRecyclerAdapter.notifyDataSetChanged();

                            }
                            /////////////////////
                            // else if(doc.getType() == DocumentChange.Type.REMOVED){

                            //  blogRecyclerAdapter.notifyDataSetChanged();

                            //}
                            /////////////////////
                        }

                        isFirstPageFirstLoad = false;

                    }

                }

            });

        }else{

        }
    }

    public void loadMorePost(){

        if(firebaseAuth.getCurrentUser() != null) {

            Query nextQuery = firebaseFirestore.collection("Posts")
                    .orderBy("timestamp", Query.Direction.DESCENDING)
                    .startAfter(lastVisible)
                    .limit(3);

            nextQuery.addSnapshotListener(getActivity(), new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {

                    if (!documentSnapshots.isEmpty()) {

                        lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);
                        for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {

                            if (doc.getType() == DocumentChange.Type.ADDED) {

                                String blogPostId = doc.getDocument().getId();
                                BlogPost blogPost = doc.getDocument().toObject(BlogPost.class).withId(blogPostId);
                                blog_list.add(blogPost);
//                                fragmentManager = getChildFragmentManager();
                                blogRecyclerAdapter.notifyDataSetChanged();
                            }

                        }
                    }

                }
            });

        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////Extras/////////////////////////////////////////////////////////////////////////////

//////////////////////////////////////////////////////////////////////////////
    public static void setfragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
      //  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.newscontainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
///////////////////////////////////////////////////////////////////
    @Override
    public void onRefresh() {
        Log.i("Info","refreshed");
        blog_list.clear();
        loadposts();
        new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
    }, 1000);

    }
///////////////////////////////////////ON PREPARE OPTIONS MENU EXECUTES AFTER ON CREATE OPTIONS MENU//////////////////////////////////////////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        this.inflater = inflater;
        this.menu = menu;

    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.clear();
        inflater.inflate(R.menu.home_menu, menu);
        menu.findItem(R.id.addpost).setVisible(true);
    }
    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.addpost) {
            NewPostActivity newpost = new NewPostActivity();
            fragmentManager = getChildFragmentManager();
            setfragment(newpost);
        //    item.setVisible(false);
        //    item.setVisible(false);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        fragmentManager = getChildFragmentManager();
        if(menu != null){
            menu.clear();
            setHasOptionsMenu(false);
            setHasOptionsMenu(true);
        }else {
            setHasOptionsMenu(true);

        }


    }

}
