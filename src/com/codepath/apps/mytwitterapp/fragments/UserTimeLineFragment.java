package com.codepath.apps.mytwitterapp.fragments;

import org.json.JSONArray;

import android.os.Bundle;

import com.codepath.apps.mytwitterapp.MyTwitterApp;
import com.codepath.apps.mytwitterapp.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

public class UserTimeLineFragment extends TweetsListFragment {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		MyTwitterApp.getRestClient().getUserTimeLine(
				new JsonHttpResponseHandler() {
					@Override
					public void onSuccess(JSONArray jsontweets) {
						getAdapter().addAll(Tweet.fromJson(jsontweets));
					}
				});

	}
}
