package devops.vishal.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import devops.vishal.newsapp.models.NewsData;
import devops.vishal.newsapp.models.NewsFeed;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    //Declaring Global Objects
    private final String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";
    private final String TAG = "//MAIN//";
    private TextView heading;
    private ListView listView;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<NewsFeed> mData;
    private NewsDataAdapter adapter;
    private String headingTitle = "";
    private ProgressDialog mDialog;
    private Retrofit retofitInstance;
    private NewsDataApi retrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.mainActivity);
        setContentView(R.layout.activity_main);

        //Loading All Widgets
        heading = findViewById(R.id.toolbar_title);
        listView = findViewById(R.id.listview);
        refreshLayout = findViewById(R.id.pull_to_refresh);

        //Inistilazing Data
        mDialog = new ProgressDialog(this);
        mData = new ArrayList<>();


        mDialog.setMessage("Loading Data");
        mDialog.show();
        mDialog.setCancelable(false);


        //fetching Data from Server
        fetchData();


        //Function to Refresh Data on Refresh
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
                refreshLayout.setRefreshing(false);


            }
        });


    }



    private void fetchData(){

        //Creating Instance of Retrofit Library
        retofitInstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //Creating Instance of NewsDataApi Interface
        retrofitAPI = retofitInstance.create(NewsDataApi.class);

        Call<NewsData> call = retrofitAPI.getData();

        call.enqueue(new Callback<NewsData>() {
            @Override
            public void onResponse(Call<NewsData> call, Response<NewsData> response) {

                mDialog.dismiss();
                mData.clear();

                //Log for debugging Purposes
                Log.e(TAG, "onResponse:: CODE " + response.toString());

                //Getting data in NewsData class
                NewsData data = response.body();

                Log.e(TAG, "Title" + data.getTitle());
                headingTitle = data.getTitle();
                ArrayList<NewsFeed> feeds = data.getRows();

                for(int i=0; i < feeds.size(); i++){
                    NewsFeed feed = feeds.get(i);

                    //Adding Data into Arraylist
                    mData.add(feed);

                }

                //Setting data to the ListView
                if(mData.size() > 0){
                    setDataValues();
                }
                else{

                    //Checking for Refresh is completed or not
                    if(refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(true);
                    }
                    Toast.makeText(MainActivity.this, "Something went wrong... Please refresh Again.", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<NewsData> call, Throwable t) {
                if(refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(true);
                }
                mDialog.dismiss();
                Log.e(TAG, "onFailure: "+t.toString());
            }
        });

    }



    public void setDataValues(){


        //Setting values to ListView
        heading.setText(headingTitle);
        adapter = new NewsDataAdapter(this, R.layout.single_item_layout, mData);
        listView.setAdapter(adapter);

        //Checking for Refresh is completed or not
        if(refreshLayout.isRefreshing()) {
            refreshLayout.setRefreshing(true);
        }
    }




}
