package com.brayooteck.youssef.pdfs;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.brayooteck.youssef.R;

import java.util.ArrayList;

public class list extends ArrayAdapter<arrayadapter>  {
   private ListView listView;
    Context c;
   // FragmentManager fragmentManager;



    public list(Context c, int adapter, ArrayList<arrayadapter> players) {
        super(c,adapter,0,players);
        this.c = c;
        this.listView = listView;


    }


    static class ViewHolder {
    Button btn;
}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final arrayadapter namee =getItem(position);
        View rowView = convertView;
      //  fragmentManager = ((AppCompatActivity) c).getSupportFragmentManager();
       // mymainactivity.backstack = "sections";
       // mymainactivity.addtobackstack = "list";


        if (rowView == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            rowView = inflater.inflate(R.layout.adapter, parent, false);
            ViewHolder h = new ViewHolder();
            h.btn = rowView.findViewById(R.id.but);
            rowView.setTag(h);
        }

        ViewHolder h = (ViewHolder) rowView.getTag();

        h.btn.setText(namee.getName());
        h.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DO what you want to recieve on btn click there.
                download.size = namee.getSize();
                download.url = namee.getUrl();
                download.name = namee.getName();
                download.ansp = namee.getAnsp();
                download.ans =namee.getValue();

                download downclass = new download();
                MainActivity.setfragment(downclass);

            }
        });

        return rowView;
    }


}