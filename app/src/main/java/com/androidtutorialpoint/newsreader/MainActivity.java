package com.androidtutorialpoint.newsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.assad.newsreader.R;


public class MainActivity extends AppCompatActivity  {


    Boolean first = true;

    public OnDataPass dataPasser;
    public  void setDataPasser(OnDataPass _dataPasser)
    { this.dataPasser=_dataPasser;
    }
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("WSJ News");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        addTabs(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);





        final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener(){


            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {


                viewPager.setCurrentItem(position);
                if(dataPasser!=null) {
                    dataPasser.onDataPass(position);

                }


            }




            @Override
            public void onPageScrollStateChanged(int state) {


            }
        };

        viewPager.addOnPageChangeListener(pageChangeListener);





    }
    private void addTabs(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Tablayout_in_Android(0), "Opinion");
        adapter.addFrag(new Tablayout_in_Android(1), "World News");
        adapter.addFrag(new Tablayout_in_Android(2), "US Business");
        adapter.addFrag(new Tablayout_in_Android(3), "Markets News");
        adapter.addFrag(new Tablayout_in_Android(4), "Technology");
        adapter.addFrag(new Tablayout_in_Android(5), "Lifestyle");
        viewPager.setAdapter(adapter);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }
}
