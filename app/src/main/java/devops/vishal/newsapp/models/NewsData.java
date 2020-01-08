package devops.vishal.newsapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class NewsData {

    @SerializedName("title")
    @Expose
    private String title;


    @SerializedName("rows")
    @Expose
    private ArrayList<NewsFeed> rows;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<NewsFeed> getRows() {
        return rows;
    }

    public void setRows(ArrayList<NewsFeed> rows) {
        this.rows = rows;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}
