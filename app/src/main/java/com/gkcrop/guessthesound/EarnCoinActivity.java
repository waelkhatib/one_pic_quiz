package com.gkcrop.guessthesound;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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

	Button btnfb,btntwitter,btnyoutube,btnrateapp,btnmoreapp,btnwebsite,btnshareapp,btnback;
	private String lvl = "0";
	private String coins = "0";
	TextView txtcoin,txttitle;
	private AdView mAdView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.earn_coin);
		
		mAdView = (AdView) findViewById(R.id.adView);
		mAdView.loadAd(new AdRequest.Builder().build());
		btnfb=(Button)findViewById(R.id.button1);
		btntwitter=(Button)findViewById(R.id.button2);
		btnyoutube=(Button)findViewById(R.id.button3);
		btnrateapp=(Button)findViewById(R.id.button4);
		btnmoreapp=(Button)findViewById(R.id.button5);
		btnwebsite=(Button)findViewById(R.id.button6);
		btnshareapp=(Button)findViewById(R.id.button7);
		btnback=(Button)findViewById(R.id.button_back);
		txtcoin=(TextView)findViewById(R.id.textView1);
		txttitle=(TextView)findViewById(R.id.textView2);
		
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
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.FACEBOOK, Boolean.valueOf(true));
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
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.TWITTER, Boolean.valueOf(true));
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
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.YOUTUBE, Boolean.valueOf(true));
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
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.RATEAPP, Boolean.valueOf(true));
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
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.MOREAPP, Boolean.valueOf(true));
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
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.WEBSITE, Boolean.valueOf(true));
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
				EarnCoin.setIfEarnButtonClicked(EarnCoinActivity.this,EarnCoin.SHAREAPP, Boolean.valueOf(true));
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
		
		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.FACEBOOK).booleanValue())
		{
			btnfb.setEnabled(true);
		}
		else
		{
			btnfb.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.TWITTER).booleanValue())
		{
			btntwitter.setEnabled(true);
		}
		else
		{
			btntwitter.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.YOUTUBE).booleanValue())
		{
			btnyoutube.setEnabled(true);
		}
		else
		{
			btnyoutube.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.RATEAPP).booleanValue())
		{
			btnrateapp.setEnabled(true);
		}
		else
		{
			btnrateapp.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.MOREAPP).booleanValue())
		{
			btnmoreapp.setEnabled(true);
		}
		else
		{
			btnmoreapp.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.WEBSITE).booleanValue())
		{
			btnwebsite.setEnabled(true);
		}
		else
		{
			btnwebsite.setEnabled(false);
		}

		if(!EarnCoin.getIfEarnButtonClicked(EarnCoinActivity.this, EarnCoin.SHAREAPP).booleanValue())
		{
			btnshareapp.setEnabled(true);
		}
		else
		{
			btnshareapp.setEnabled(false);
		}
	}
	
	
	public boolean fileExist(){
		File file = new File(getFilesDir()+File.separator+"thewords.dat");
		if(file.exists()){return true;}
		else{return false;}    
	}

	private void writeData(String dataStr){
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("thewords.dat", Context.MODE_PRIVATE));
			outputStreamWriter.write(dataStr);
			outputStreamWriter.close();
		}
		catch (IOException e) {} 

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
					String receiveString = "";
					StringBuilder stringBuilder = new StringBuilder();
					while ((receiveString = bufferedReader.readLine()) != null) {
						stringBuilder.append(receiveString);
					}
					inputStream.close();
					ret = stringBuilder.toString();
				}
			} catch (FileNotFoundException e) {
			} catch (IOException e) {
			}
			return ret;
		}

}
