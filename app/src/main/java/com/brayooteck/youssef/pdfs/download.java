package com.brayooteck.youssef.pdfs;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.brayooteck.youssef.R;
import com.brayooteck.youssef.Rootfragment;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

//import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
// com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
//import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;


public class download extends Rootfragment implements OnPageChangeListener {
    PDFView pdfViewph;
    File new_folder;
    public static String name;
    public static String url;
    public static int size;
    public static  SharedPreferences.Editor editor;
    public static int lastpage = 1;
    public static boolean ans = false;
    public static int ansp;
    private AdView mAdView;
    TextView edit;
    TextView edit2;
    int fileSize;
    String path;
    View view;
    private Menu menu;
    boolean ansORback = false;
    public  File pdf;
    public static int currentpage = 1;
    int anspage = ansp;
    boolean canbefullscreen = false;
    MenuInflater inflater;
    RetrievePDFBytes download;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.download, container, false);
     //   download = new RetrievePDFBytes();
        setHasOptionsMenu(true);
        edit = view.findViewById(R.id.editText);
        edit2 = view.findViewById(R.id.textView2);
        mAdView = view.findViewById(R.id.adView5);
        pdfViewph = (PDFView) view.findViewById(R.id.pdfViewerr);
        pdfViewph.setVisibility(View.GONE);
        edit2.setVisibility(View.VISIBLE);
        edit.setVisibility(View.VISIBLE);
        mAdView.setVisibility(View.VISIBLE);
        ////////////////////////////
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        editor = pref.edit();
        lastpage =  pref.getInt(String.valueOf(size), 0);
        currentpage = lastpage;
        ////////////////////////////
///        MobileAds.initialize(getContext());
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        ////////////////////////
      //  isStoragePermissionGranted();

        return view;
       }



////////////////////////////////////////making sure i have a permission/////////////////////////////////////
    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (getActivity().checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Info","Permission is granted");
                test();
                return true;
            } else {

                Log.v("Info","Permission is revoked");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
    }
    else { //permission is automatically granted on sdk<23 upon installation
        Log.v("Info","Permission is granted");
        return true;
    }
}
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v("Info","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
            test();
        }else {
            Toast.makeText(getActivity(),"يجب الحصول علي التصريح لحفظ الملف",Toast.LENGTH_LONG).show();

        }
    }
/////////////////////////////////////////////test if file exists comlete////////////////////////////////
    public void test() {
        if (!new File(Environment.getExternalStorageDirectory().toString() + "/.myfolder/" + name).exists()) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            download.execute(url);
        }else {
            gettingpath();
            fileSize = (int) pdf.length();
            if (fileSize != size){
                pdf.delete();
                download.execute(url);
            }else {
                edit2.setVisibility(View.GONE);
                edit.setVisibility(View.GONE);
                mAdView.setVisibility(View.GONE);
                pdfViewph.setVisibility(View.VISIBLE);
                open(currentpage);
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        }
    }
 ///////////////////////////////////////////////create folder//////////////////////////////////////////////
    private void createFolder() {
        new_folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath() , "/.myfolder/");
        if (!new_folder.exists()) {
            if (new_folder.mkdir()) {
                Log.i("Info", "Folder succesfully created");
            } else {
            }
        } else {
            Log.i("Info", "Folder already exists");
        }

    }
////////////////////////////////////////if not exists download////////////////////////////////////////////
     class RetrievePDFBytes extends AsyncTask<String, Integer, String> {

        ProgressBar progressBar;
        RelativeLayout layout = view.findViewById(R.id.layout);
        int progress = 0;

        @Override
        protected void onPreExecute() {
            createFolder();
            progressBar = new ProgressBar(getContext(),null,android.R.attr.progressBarStyleHorizontal);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(600,200);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            layout.addView(progressBar,params);
            progressBar.setVisibility(View.VISIBLE);
            edit2.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            mAdView.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String path = params[0];


            try {
                URL url = new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                File output_file = new File(new_folder, name);

                OutputStream outputStream = new FileOutputStream(output_file);
                InputStream inputStream = new BufferedInputStream(url.openStream(), 8192);
                byte[] data = new byte[1024];
                int total = 0;
                int count;
                while ((count = inputStream.read(data)) != -1) {
                    int y = Math.abs(count);
                    total += y;
                    int x = Math.abs(total);
                    outputStream.write(data, 0, y);
                    progress =  100 * x / size;
                    int z = Math.abs(progress);
                    publishProgress(z);

                    if(isCancelled()){
                        break;
                    }

                }
                outputStream.flush();
                inputStream.close();
                outputStream.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download complete.";
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
            edit.setText(Math.abs(progress) + "%");
        }
        @Override
        protected void onPostExecute(String result) {
           // this.progressBar.setVisibility(View.GONE);
            this.progressBar.setVisibility(View.GONE);
            edit2.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            mAdView.setVisibility(View.GONE);
            pdfViewph.setVisibility(View.VISIBLE);
            gettingpath();
            open(currentpage);

        }
    }
///////////////////////////////////////////////getting pdf path///////////////////////////////////////////
    void gettingpath(){
        File folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/.myfolder/");
        pdf = new File(folder, name);
        path = pdf.toString();
    }
//////////////////////////////////////////////////open pdf/////////////////////////////////////////////////
   public  void  open(int page){
       Log.i("Info", "path " + path);
       pdfViewph.fromFile(new File(path)).onPageChange(this).defaultPage(page).enableSwipe(true).load();
       canbefullscreen = true;

   }
/////////////////////////////////////////on user scroll//////////////////////////////////////////////////
    @Override
    public void onPageChanged(int page, int pageCount) {
        if(ansORback == true){ //we are in answers section
            anspage = page;
        }
        else if (ansORback == false){
            currentpage = page;
        }
        setMenuName(page);
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////Extras/////////////////////////////////////////////////////////////////////
    @Override
    public void onResume() {
        super.onResume();
        canbefullscreen = false;
        if(menu != null){
            menu.clear();
            setHasOptionsMenu(false);
            setHasOptionsMenu(true);
            Log.i("Info","notdownload");

        }else{
            setHasOptionsMenu(false);
            setHasOptionsMenu(true);
        }
       // test();
     //   download = new RetrievePDFBytes();

        if(download != null && download.getStatus() == AsyncTask.Status.RUNNING){

        }else {
            download = new RetrievePDFBytes();
            isStoragePermissionGranted();

        }
        Log.i("Info","downloadresume");
    }

    @Override
    public void onPause() {
        super.onPause();
        if(download.getStatus() == AsyncTask.Status.RUNNING){
           download.cancel(true);
            Log.i("Info","downloadcanceled");

        }
    }
    //////////////////////////////////////////////////////saving pdf current page/////////////////////////////////////////

    public void onDestroy() {
    super.onDestroy();
        Log.i("Info","downloaddestroid");

        editor.putInt(String.valueOf(size), currentpage);
        editor.putInt(String.valueOf("ans " + size), anspage);
        editor.commit();

        download.cancel(true);

}
/////////////////////////////////////////////////////////////new////////////////////////////////////////////////////
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu,inflater);
        menu.clear();
        this.inflater = inflater;
        this.menu = menu;

    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.menu = menu;
        menu.clear();
        inflater.inflate(R.menu.download_menu, menu);
        Log.i("Info", "downloadmenuinflated" + menu.toString())
        ;
        MenuItem actionSearch= menu.findItem(R.id.search_action);
        final SearchView searchViewEditText = (SearchView) actionSearch.getActionView();
        searchViewEditText .setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.matches("\\d+")) {  // Only ints so don't actually need to consider decimals
                    //is a number... do relevant code
                    int search = Integer.parseInt(query);
                    if(canbefullscreen ==true){
                        open(search);
                    }else{
                        Toast.makeText(getContext(),"برجاء الانتظار حتي ينتهي التحميل",Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    //not a number
                    Toast.makeText(getContext(),"برجاء البحث برقم الصفحه فقط",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

    }
    // handle button activities
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.txt) {

        }
        else if(id == R.id.ans && canbefullscreen == true){
//////////////////////////////////////////
               if(ans == true){
                        if(ansORback == false){

                            open(anspage);
                            menu.findItem(R.id.ans).setTitle("back");
                            ansORback = true;

                         }
                         else if(ansORback == true){
                             open(currentpage);
                             menu.findItem(R.id.ans).setTitle("ans");
                             ansORback = false;
                         }
                }else{
                          //   menu.findItem(R.id.ans).setVisible(false);
                          menu.findItem(R.id.ans).setTitle("noans");

                }
        }else if(id == R.id.fullscreen && canbefullscreen == true){

                fullscreen.path = path;
                fullscreen.ans = ans;
                fullscreen.anspage = anspage;
                fullscreen.currentpage = currentpage;
                fullscreen.size = size;
                Intent i = new Intent(getActivity(), fullscreen.class);
                startActivity(i);
        }else if(canbefullscreen == false){

               Toast.makeText(getContext(),"برجاء الانتظار حتي ينتهي التحميل",Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }



//    @Override
//    public void onPause() {
//        super.onPause();
//      //  menu.clear();
//    }

    ///////////////////////////////////////set page number in menu///////////////////////////////////
    public void setMenuName(int pagenumber){
        if(menu.findItem(R.id.txt) != null) {
            String thepage = String.valueOf(pagenumber);
            menu.findItem(R.id.txt).setTitle(thepage);
        }else{
            Log.i("Info", "nomenu");
             }

        }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


}
