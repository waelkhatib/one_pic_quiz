package com.gkcrop.picturePuzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import com.android4tr.quizpic.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EarnCoinActivity extends Activity {

	private Button btnfb;
	private Button btntwitter;
	private Button btnyoutube;
	private Button btnrateapp;
	private Button btnmoreapp;
	private Button btnwebsite;
	private Button btnshareapp;
	private String lvl = "0";
	private String coins = "0";
	private TextView txtcoin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.earn_coin);

		AdView mAdView = findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());
		btnfb= findViewById(R.id.button1);
		btntwitter= findViewById(R.id.button2);
		btnyoutube= findViewById(R.id.button3);
		btnrateapp= findViewById(R.id.button4);
		btnmoreapp= findViewById(R.id.button5);
		btnwebsite= findViewById(R.id.button6);
		btnshareapp= findViewById(R.id.button7);
		Button btnback = findViewById(R.id.button_back);
		txtcoin= findViewById(R.id.textView1);
		TextView txttitle = findViewById(R.id.textView2);
		
		txttitle.setText("‰ﬁÊœ „Ã«‰Ì…");
		
		if(!fileExist()){writeData("1|10");}
		
		lvl = readData().split("\\|")[0];
		coins = readData().split("\\|")[1];
		if (Integer.parseInt(coins) < 0) {
			coins = "0";
		}
		
		btnback.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		btnfb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.FACEBOOK, Boolean.TRUE);
				btnfb.setEnabled(false);
				coins = "" + (Integer.parseInt(coins)+Integer.parseInt(getString(R.string.how_much_for_facebook_coins)));
				txtcoin.setText(coins);
				writeData("" + (Integer.parseInt(lvl)) + "|"
						+ (Integer.parseInt(coins)));
				
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.facebook_link))));
				
				
				
			}
		});

		btntwitter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.TWITTER, Boolean.TRUE);
				btntwitter.setEnabled(false);
				coins = "" + (Integer.parseInt(coins)+Integer.parseInt(getString(R.string.how_much_for_twitter_coins)));
				txtcoin.setText(coins);
				writeData("" + (Integer.parseInt(lvl)) + "|"
						+ (Integer.parseInt(coins)));
				
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.twitter_link))));
			}
		});
		
		btnyoutube.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.YOUTUBE, Boolean.TRUE);
				btnyoutube.setEnabled(false);
				coins = "" + (Integer.parseInt(coins)+Integer.parseInt(getString(R.string.how_much_for_youtube_coins)));
				txtcoin.setText(coins);
				writeData("" + (Integer.parseInt(lvl)) + "|"
						+ (Integer.parseInt(coins)));
				
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.youtube_link))));
			}
		});


		btnrateapp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.RATEAPP, Boolean.TRUE);
				btnrateapp.setEnabled(false);
				coins = "" + (Integer.parseInt(coins)+Integer.parseInt(getString(R.string.how_much_for_rateapp_coins)));
				txtcoin.setText(coins);
				writeData("" + (Integer.parseInt(lvl)) + "|"
						+ (Integer.parseInt(coins)));
				
				final String appName = getPackageName();//your application package name i.e play store application url
				try {
					startActivity(new Intent(Intent.ACTION_VIEW,
							Uri.parse("market://details?id="
									+ appName)));
				} catch (android.content.ActivityNotFoundException anfe) {
					startActivity(new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://play.google.com/store/apps/details?id="
									+ appName)));
				}
			}
		});
		
		btnmoreapp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.MOREAPP, Boolean.TRUE);
				btnmoreapp.setEnabled(false);
				coins = "" + (Integer.parseInt(coins)+Integer.parseInt(getString(R.string.how_much_for_moreapp_coins)));
				txtcoin.setText(coins);
				writeData("" + (Integer.parseInt(lvl)) + "|"
						+ (Integer.parseInt(coins)));
				
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.youtube_link))));
			}
		});
		
		
		btnwebsite.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.WEBSITE, Boolean.TRUE);
				btnwebsite.setEnabled(false);
				coins = "" + (Integer.parseInt(coins)+Integer.parseInt(getString(R.string.how_much_for_website_coins)));
				txtcoin.setText(coins);
				writeData("" + (Integer.parseInt(lvl)) + "|"
						+ (Integer.parseInt(coins)));
				
				startActivity(new Intent(
						Intent.ACTION_VIEW,
						Uri.parse(getString(R.string.website_link))));
			}
		});
		
		btnshareapp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.SHAREAPP, Boolean.TRUE);
				btnshareapp.setEnabled(false);
				coins = "" + (Integer.parseInt(coins)+Integer.parseInt(getString(R.string.how_much_for_shareapp_coins)));
				txtcoin.setText(coins);
				writeData("" + (Integer.parseInt(lvl)) + "|"
						+ (Integer.parseInt(coins)));
				
				Intent sendIntent = new Intent();
				sendIntent.setAction(Intent.ACTION_SEND);
				sendIntent.putExtra(Intent.EXTRA_TEXT, "Hi I would like to share application "+getString(R.string.app_name)+" on Google Play Store, \n You can Download it from Here https://play.google.com/store/apps/details?id="+getPackageName());
				sendIntent.setType("text/plain");
				startActivity(sendIntent);
			}
		});


	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		txtcoin.setText(coins);
		
		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.FACEBOOK))
		{
			btnfb.setEnabled(true);
		}
		else
		{
			btnfb.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.TWITTER))
		{
			btntwitter.setEnabled(true);
		}
		else
		{
			btntwitter.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.YOUTUBE))
		{
			btnyoutube.setEnabled(true);
		}
		else
		{
			btnyoutube.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.RATEAPP))
		{
			btnrateapp.setEnabled(true);
		}
		else
		{
			btnrateapp.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.MOREAPP))
		{
			btnmoreapp.setEnabled(true);
		}
		else
		{
			btnmoreapp.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.WEBSITE))
		{
			btnwebsite.setEnabled(true);
		}
		else
		{
			btnwebsite.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.SHAREAPP))
		{
			btnshareapp.setEnabled(true);
		}
		else
		{
			btnshareapp.setEnabled(false);
		}
	}
	
	
	private boolean fileExist(){
		File file = new File(getFilesDir()+File.separator+"thewords.dat");
		return file.exists();
	}

	private void writeData(String dataStr){
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("thewords.dat", Context.MODE_PRIVATE));
			outputStreamWriter.write(dataStr);
			outputStreamWriter.close();
		}
		catch (IOException ignored) {}

	}
	
	// Function that read user data
		private String readData() {
			String ret = "";
			try {
				InputStream inputStream = openFileInput("thewords.dat");
				if (inputStream != null) {
					InputStreamReader inputStreamReader = new InputStreamReader(
							inputStream);
					BufferedReader bufferedReader = new BufferedReader(
							inputStreamReader);
					String receiveString;
					StringBuilder stringBuilder = new StringBuilder();
					while ((receiveString = bufferedReader.readLine()) != null) {
						stringBuilder.append(receiveString);
					}
					inputStream.close();
					ret = stringBuilder.toString();
				}
			} catch (IOException ignored) {
			}
			return ret;
		}

}
