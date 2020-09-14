package com.gkcrop.picturePuzzle;

import android.content.Context;
import android.content.SharedPreferences.Editor;

class EarnCoin {


	public static final String FACEBOOK = "Facebook";
	public static final String TWITTER = "Twitter";
	public static final String YOUTUBE = "Youtube";
	public static final String RATEAPP = "RateApp";
	public static final String SHAREAPP = "ShareApp";
	public static final String MOREAPP = "MoreApp";
	public static final String WEBSITE = "Website";
	

	public static Boolean getIfEarnButtonClicked(Context context, String s)
	{
		return context.getSharedPreferences("earncoin", 0).getBoolean(s, false);
	}

	public static void setIfEarnButtonClicked(Context context, String s, Boolean boolean1)
	{
		Editor editor = context.getSharedPreferences("earncoin", 0).edit();
		editor.putBoolean(s, boolean1);
		editor.apply();
	}

}
