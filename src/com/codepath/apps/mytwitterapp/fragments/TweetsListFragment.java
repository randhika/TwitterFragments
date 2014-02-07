package com.codepath.apps.mytwitterapp.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.mytwitterapp.EndlessScrollListener;
import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.R;
import com.codepath.apps.mytwitterapp.TweetsAdapter;
import com.codepath.apps.mytwitterapp.UserTimeline;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class TweetsListFragment extends Fragment {

	ListView lvTweets;
	TextView tv;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	int count = 25;
	TweetsAdapter adapter;

	// must be created for every fragment.
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup parent,
			Bundle savedInstanceState) {
		// Defines the xml file for the fragment
		return inflater.inflate(R.layout.fragment_tweets_list, parent, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		tweets = new ArrayList<Tweet>();
		lvTweets = (ListView) getActivity().findViewById(R.id.lvtweets);
		adapter = new TweetsAdapter(getActivity(), tweets);
		lvTweets.setAdapter(adapter);

		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				count = count + 10;
				loadMoreTweets(count);
			}
		});

		lvTweets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Tweet t = (Tweet) parent.getAdapter().getItem(position);
				FragmentActivity fa = getActivity();
				Intent i = new Intent(fa,UserTimeline.class);
				i.putExtra("uname", t.getUser().getScreenName());
				startActivity(i);
			}

		});

		super.onCreate(savedInstanceState);
	}

	public void loadMoreTweets(int count) {
		MyTwitterApp.getRestClient().getHomeTimeLine(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsontweets) {
						tweets = Tweet.fromJson(jsontweets);
						lvTweets = (ListView) getActivity().findViewById(
								R.id.lvtweets);
						TweetsAdapter adapter = new TweetsAdapter(
								getActivity(), tweets);
						lvTweets.setAdapter(adapter);
					}
				}, count);
	}

	public TweetsAdapter getAdapter() {
		return adapter;
	}

}
