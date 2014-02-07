package com.codepath.apps.mytwitterapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.mytwitterapp.models.Tweet;
import com.codepath.apps.mytwitterapp.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserTimeline extends Activity {

	ListView lvTweets;
	ArrayList<Tweet> tweets = new ArrayList<Tweet>();
	int count=25;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_timeline);
		Intent i = getIntent();
		String screenName = i.getStringExtra("uname");
	/*	TextView uname=(TextView)findViewById(R.id.uname);
		uname.setText(screenName);*/
		MyTwitterApp.getRestClient().getUserInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONObject json)
			{
				User u = User.fromJson(json);
				getActionBar().setTitle("@"+u.getScreenName());
				populateProfileHeader(u);
			}
		},screenName);
		loadProfileInfo(screenName);
		
	}

	private void populateProfileHeader(User u) {
		// TODO Auto-generated method stub
		TextView tvName = (TextView)findViewById(R.id.usertvName);
		TextView tvTagline = (TextView)findViewById(R.id.usertvTagline);
		TextView tvFollowers = (TextView)findViewById(R.id.usertvFollowers);
		TextView tvFollowing = (TextView)findViewById(R.id.usertvFollowing);
		ImageView ivProfileImage = (ImageView)findViewById(R.id.userivProfileImage);
		tvName.setText(u.getName());
		tvTagline.setText(u.getTagline());
		tvFollowers.setText("Followers : "+u.getFollowersCount());
		tvFollowing.setText("Following : "+u.getFriendsCount());
		ImageLoader.getInstance().displayImage(u.getProfileImageUrl(), ivProfileImage);
	}
	
	public void loadProfileInfo(final String screenName) {

		MyTwitterApp.getRestClient().getUserTimeLineInfo(new JsonHttpResponseHandler(){
			@Override
			public void onSuccess(JSONArray jsontweets)
			{
				
				tweets = Tweet.fromJson(jsontweets);
				lvTweets = (ListView)findViewById(R.id.userlvtweets);
				TweetsAdapter adapter = new TweetsAdapter(getBaseContext(), tweets);
				lvTweets.setAdapter(adapter);
			}

		},screenName);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_timeline, menu);
		return true;
	}

}
