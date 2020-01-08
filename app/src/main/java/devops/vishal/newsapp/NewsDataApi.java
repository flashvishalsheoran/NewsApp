package devops.vishal.newsapp;

import devops.vishal.newsapp.models.NewsData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface NewsDataApi {

         final String BASE_URL = "https://dl.dropboxusercontent.com/s/2iodh4vg0eortkl/";

         @Headers("Content-Type: application/json")
         @GET("facts.json")
         Call<NewsData> getData();

}
