package com.codepath.apps.mytwitterapp;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "cMwzoNrfU75lqTaQdj78fw";       // Change this
    public static final String REST_CONSUMER_SECRET = "UxdZSx9u21woVtGYqlUbCbNfBwJQsmQEwdRdzbhQVk"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://mytwitterapp"; // Change this (here and in manifest)
    
    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }
    
    public void getHomeTimeLine(AsyncHttpResponseHandler handler,int count)
    {
    	String url = getApiUrl("statuses/home_timeline.json?count="+count);
    	client.get(url, null,handler);
    }
    
    public void getMentions(AsyncHttpResponseHandler handler)
    {
    	String url = getApiUrl("statuses/mentions_timeline.json");
    	client.get(url, null,handler);
    }
    
    public void getMyInfo(AsyncHttpResponseHandler handler)
    {
    	String url = getApiUrl("account/verify_credentials.json");
    	client.get(url, null,handler);
    }
    
    public void getUserInfo(AsyncHttpResponseHandler handler,String screenname)
    {
    	String url = getApiUrl("users/show.json?screen_name="+screenname);
    	client.get(url, null,handler);
    }
    
    public void getUserTimeLineInfo(AsyncHttpResponseHandler handler,String screenname)
    {
    	String url = getApiUrl("statuses/user_timeline.json?screen_name="+screenname);
    	client.get(url, null,handler);
    }
    
    public void postTweet(AsyncHttpResponseHandler handler, String body) {
        String url = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams("status", body);
        client.post(url, params, handler);
    }
    
    public void getUserTimeLine(AsyncHttpResponseHandler handler)
    {
    	String url = getApiUrl("statuses/user_timeline.json");
    	client.get(url, null,handler);
    }
}