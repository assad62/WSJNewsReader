package com.androidtutorialpoint.newsreader;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.assad.newsreader.R;
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;




import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.zip.DataFormatException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import dmax.dialog.SpotsDialog;
import models.DataModel;

@SuppressLint("ValidFragment")
public class Tablayout_in_Android extends Fragment  {

    boolean firstRun=true;

    int mPosition = 0;
    int mpos = 0;
    SwipeRefreshLayout mSwipeRefreshLayout;
    CustomAdapter adapter;
    AlertDialog dialog;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
        ArrayList<DataModel> dataList= new ArrayList<>();
        TextView t;
        ViewPagerAdapter mViewPagerAdapter;
        @SuppressLint("ValidFragment")
        public Tablayout_in_Android(int pos) {
            // Required empty public constructor
            this.mpos = pos;
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            // Inflate the layout for this fragment

            View view = inflater.inflate(R.layout.fragment_tablayout_in__android, container, false);

            return view;
        }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        dialog = new SpotsDialog(getActivity());
        dialog.show();


        recyclerView = view.findViewById(R.id.my_recycler_view_news);

        mSwipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items

                dataList.clear();
                adapter.notifyDataSetChanged();
                displayList(adapter,recyclerView,mPosition);


            }
        });



        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);



        MainActivity main = (MainActivity) Objects.requireNonNull(getActivity());

//        main.setDataPasser(new OnDataPass() {
//            @Override
//            public void onDataPass(Integer position) {
//
//                Log.d("JESS","position in tablayout is "+position);
//                if(position!=null){
//                    dataList.clear();
//                    adapter.notifyDataSetChanged();
//                    mPosition=position;
//                    firstRun =false;
//                    displayList(adapter,recyclerView,position);
//
//                }
//            }
//        });
        adapter = new CustomAdapter(getActivity(),dataList);
        adapter.onItemClickListener(new onItemClickListener() {
            @Override
            public void onItem(int position) {
                String url=dataList.get(position).getUrl();
                Intent i = new Intent(getActivity(), WebViewActivity.class);
                i.putExtra("url", url);
                startActivity(i);
            }
        });
        recyclerView.setAdapter(adapter);



        //if(firstRun)
        displayList(adapter, recyclerView, mPosition);




    }
    private void displayList(final CustomAdapter adapter, final RecyclerView recyclerView, int position) {



        position = mpos;



            String urlString =null;
            if(position==0) {
                urlString = "http://www.wsj.com/xml/rss/3_7041.xml";
            }
            else if (position ==1)
                urlString = "http://www.wsj.com/xml/rss/3_7085.xml";
            else if (position ==2)
                urlString = "http://www.wsj.com/xml/rss/3_7014.xml";
            else if(position == 3)
                urlString = "http://www.wsj.com/xml/rss/3_7031.xml";
            else if(position == 4)
                urlString ="http://www.wsj.com/xml/rss/3_7455.xml";
            else if(position ==5)
                urlString ="http://www.wsj.com/xml/rss/3_7201.xml";






        Parser parser = new Parser();
        parser.execute(urlString);
        parser.onFinish(new Parser.OnTaskCompleted() {

            @Override
            public void onTaskCompleted(ArrayList<Article> list) {


                    for (int a = 0; a < list.size(); a++) {

                        Article article = list.get(a);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        DataModel model = new DataModel();
                        model.setAuthor(article.getAuthor());
                        model.setTitle(article.getTitle());
                        model.setUrl(article.getLink());
                        model.setImageUrl(article.getImage());
                        model.setPublishedAt(article.getPubDate().toString());
                        dataList.add(model);
                    }


                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });


                    dialog.dismiss();

                    mSwipeRefreshLayout.setRefreshing(false);



                //what to do when the parsing is done
                //the Array List contains all article's data. For example you can use it for your adapter.
            }

            @Override
            public void onError() {

                dialog.dismiss();


                //what to do in case of error
            }
        });




    }


    /*@Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            // Refresh your fragment here

            if(adapter!=null) {
                adapter.notifyDataSetChanged();
                firstRun = false;
                displayList(adapter, recyclerView, mPosition);
            }
        }
    }*/

    @Override
    public void onStart() {
        super.onStart();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }





}
