package devops.vishal.newsapp;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import devops.vishal.newsapp.models.NewsFeed;

public class NewsDataAdapter extends ArrayAdapter<NewsFeed> {

    private int layoutResourceId;
    private Context mContext;
    private ArrayList<NewsFeed> dataList = null;

    public NewsDataAdapter(Context context, int layoutResourceId, ArrayList<NewsFeed> feed) {
        super(context, layoutResourceId, feed);

        this.layoutResourceId = layoutResourceId;
        this.mContext = context;
        this.dataList = feed;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            // inflate the layout
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            convertView = inflater.inflate(layoutResourceId, parent, false);
        }

        NewsFeed data = dataList.get(position);


        //Checking if Entry is empty or not
        if (TextUtils.isEmpty(data.getTitle()) && TextUtils.isEmpty(data.getTitle()) && TextUtils.isEmpty(data.getTitle())) {

            Log.e("//ADAPTER//", "Null Data Encountered");

        } else {

            LinearLayout layout = convertView.findViewById(R.id.mainLinearLay);
            TextView title = convertView.findViewById(R.id.title);
            TextView description = convertView.findViewById(R.id.description);
            ImageView imageView = convertView.findViewById(R.id.imageView);


            //Setting some smooth animation to the ListView
            layout.setAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_in_effect));

            //Setting Values in Adapter
            title.setText(data.getTitle());
            description.setText(data.getDescription());


            //for lazy loading I am using Glide Because it also make AsyncRequest and lazy-load the images
            Glide.with(mContext)
                    .load(data.getImageHref())
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .fitCenter())
                    .into(imageView);


        }


        return convertView;
    }
}
