package com.gkcrop.picturePuzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android4tr.quizpic.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.loopj.android.image.SmartImageView;


@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("NewApi")
public class TheGame extends Activity {
	// Variables
	private static final int MY_PERMISSIONS_WRITE =12 ;
	private InterstitialAd interstitial;
	private Button[] word_btn;
	private String lvl = "0";
	private String coins = "0";
	//private int count=0;
	private final String[] chars = { "ا", "أ", "ب", "ت", "ث", "ج", "ح", "خ", "د",
			"ذ", "ر", "ز", "س", "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق",
			"ك", "ل", "م", "ن", "ه", "و", "ي", "ء", "ئ", "ؤ", "ة", "آ", "إ" };
	private String[] word_array;
	private String theWord = "999";
    private Button[] randBtn;
	private int count=0;
//	SoundPool soundPool;
private Context mContext;
	private String ImageFile;
    private String Ribbon;

	private Button btn_first;
    private Button btn_bomb;
    private Button btn_skip;
	private Button btn_ask;
	private boolean isLast=false;
	private MediaPlayer suc;
    private MediaPlayer fal;
	//private int soundID,Count=0;
    private StringBuilder sb;
	public TheGame() {
		// TODO Auto-generated constructor stub

	}
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public void onCreate(Bundle savedInstanceState) {

		try {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
			.permitAll().build();
			StrictMode.setThreadPolicy(policy);
		} catch (Exception ignored) {
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.game_layout);
		mContext=TheGame.this;
		((ProgressBar)findViewById(R.id.progressBar)).getIndeterminateDrawable()
				.setColorFilter(ContextCompat.getColor(this, R.color.main_bg), PorterDuff.Mode.SRC_IN );
       
		sb = new StringBuilder();
		sb.append(Environment.getExternalStorageDirectory().toString()).append(File.separator).append(getString(R.string.app_name));
		//press=MediaPlayer.create(TheGame.this, R.raw.press);
		suc=MediaPlayer.create(TheGame.this, R.raw.suc);
		fal=MediaPlayer.create(TheGame.this, R.raw.fal);
		TextView txt_ribon = findViewById(R.id.txt_ribon);
		btn_first= findViewById(R.id.button5);
		btn_bomb= findViewById(R.id.button4);
		btn_skip= findViewById(R.id.button3);
		Button btn_back = findViewById(R.id.button1);
		btn_ask= findViewById(R.id.button6);

		/*Button button = (Button)findViewById(R.id.button8);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.rippleanimset);
		animation.setFillAfter(false);
		animation.setRepeatCount(0x186a0);
		button.startAnimation(animation);*/

		AdView adView = this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest.Builder().build());


		// 12 orange buttons where appear letters of the word, and other letters
		randBtn = new Button[] {findViewById(R.id.char1),
                findViewById(R.id.char2),
                findViewById(R.id.char3),
                findViewById(R.id.char4),
                findViewById(R.id.char5),
                findViewById(R.id.char6),
                findViewById(R.id.char7),
                findViewById(R.id.char8),
                findViewById(R.id.char9),
                findViewById(R.id.char10),
                findViewById(R.id.char11),
                findViewById(R.id.char12)};
//		int image_id = mContext.getResources().getIdentifier("wbox", "drawable",
//				mContext.getPackageName());
//		randBtn[count].setBackgroundResource(image_id);
//		Timer tm=new Timer();
//		tm.scheduleAtFixedRate(new TimerTask() {
//			
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				runOnUiThread(new Runnable() {
//					public void run() {
//							int image_id = mContext.getResources().getIdentifier("wordbox", "drawable",
//									mContext.getPackageName());
//							randBtn[count].setBackgroundResource(image_id);
//						int countBtn=randBtn.length;
//						int time=0;
//						count=++count%countBtn;
//						while(randBtn[count].getVisibility()!=View.VISIBLE&& time<countBtn){
//							count=++count%countBtn;
//							time++;
//						}
//						if(time<countBtn){
//						image_id = mContext.getResources().getIdentifier("wbox", "drawable",
//								mContext.getPackageName());
//						randBtn[count].setBackgroundResource(image_id);
//						}
//					}
//				});
//				
//				
//			}
//		}, 3000, 3000);

		lvl = readData().split("\\|")[0];
		coins = readData().split("\\|")[1];
		if (Integer.parseInt(coins) < 0) {
			coins = "0";
		}
		parseXML(Integer.parseInt(lvl)-1);
		//soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		if(!isLast)
		{
			ImageView imag = findViewById(R.id.imageView1);
	        //ImageView img1=(ImageView)findViewById(R.id.imageView1);
	        BitmapFactory.Options bitopt=new BitmapFactory.Options();
	        bitopt.inSampleSize=1;
	        // String img=Environment.getExternalStorageDirectory().toString();
	        // String filepath =Environment.getExternalStorageDirectory().toString();
	        
	        InputStream fis = null;
	        try 
	        {
	        fis = getAssets().open(ImageFile+".png");
	        } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        Bitmap bi = BitmapFactory.decodeStream(fis);
	        int w = bi.getWidth();
//	        if(bi!=null){
	            imag.setImageBitmap(getRoundedCornerBitmap(bi,w));
//	        }
	        txt_ribon.setText(Ribbon);
			word_array = getWord(theWord);
			createWord(word_array.length);
			randomChars();
			TextView lvl_txt = findViewById(R.id.textView2);
			lvl_txt.setText(" " + lvl + " ");
			TextView coins_txt = findViewById(R.id.textView1);
			coins_txt.setText(coins);
		}
		else
		{
//			if(MainActivity. mp!=null)
//				MainActivity.mp.stop();
//			MainActivity.mp=MediaPlayer.create(TheGame.this, R.raw.end);
//			MainActivity. mp.setLooping(true);
//			MainActivity.mp.start();
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(getString(R.string.reset_msg_1));
			builder.setMessage(getString(R.string.reset_msg_2));
			builder.setIcon(R.drawable.ic_launcher);
			builder.setPositiveButton(getString(R.string.ok),
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				//	mp.stop();
					TheGame.this.finish();
				}
			});
			builder.setNegativeButton(getString(R.string.reset_title),
					new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					writeData(getString(R.string.point_give));
					dialog.dismiss();
//					MainActivity.mp.stop();
//					MainActivity.mp=MediaPlayer.create(TheGame.this, R.raw.begin);
//					MainActivity. mp.setLooping(true);
//					MainActivity.mp.start();
					
					TheGame.this.finish();
				}
			});
			AlertDialog alert = builder.create();
			alert.setCancelable(false);
			alert.show();
		}


		/*((Button)findViewById(R.id.button7)).setOnClickListener(new android.view.View.OnClickListener() {

			public void onClick(View view)
			{

				Count+=1;
				if (Count %2==1) {
					if(loaded)
					{
						soundPool.play(soundID, 1.0F, 1.0F, 0, 0, 1.0F);

					}
					else
					{
						Toast.makeText(getApplicationContext(), "Wait Sound is Loaded", Toast.LENGTH_SHORT).show();
					}

				}
				if (Count % 2==0) {
					soundPool.stop(soundID);
					soundPool.play(soundID, 1.0F, 1.0F, 0, 0, 1.0F);
				}

			}
		});*/


		btn_first.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							if (Integer.parseInt(coins) >= Integer.parseInt(getString(R.string.how_much_for_first_letter))) {
								btn_first.setVisibility(View.INVISIBLE);
								coins = "" + (Integer.parseInt(coins) - Integer.parseInt(getString(R.string.how_much_for_first_letter)));
								TextView coins_txt = findViewById(R.id.textView1);
								coins_txt.setText(coins);
								writeData("" + (Integer.parseInt(lvl)) + "|"
										+ (Integer.parseInt(coins)));
								if(word_btn[word_btn.length-1].getText()!="")
									clear_wrong_letter(word_btn[word_btn.length-1]);
									charOnClick(word_btn[word_btn.length-1]);
								word_btn[word_btn.length-1].setText(word_array[0].toUpperCase());
								word_btn[word_btn.length-1].setOnClickListener(null);
								for (int i = 0; i < 12; i++) {
									if (randBtn[i].getText().equals(
											word_array[0].toUpperCase())) {
										randBtn[i]
												.setVisibility(View.INVISIBLE);
										count++;
										i = 12;
									}
								}
							}
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							break;
						}
					}
				};

				// Check if sufficient coins
				AlertDialog.Builder builder = new AlertDialog.Builder(
						TheGame.this);
				builder.setTitle(getString(R.string.first_letter_msg_3)).setIcon(
						R.drawable.help);
				if (Integer.parseInt(coins) >= Integer.parseInt(getString(R.string.how_much_for_first_letter))) {
					builder.setMessage(getString(R.string.first_letter_msg_1));
					builder.setNegativeButton(getString(R.string.no), dialogClickListener)
					.setPositiveButton(getString(R.string.yes), dialogClickListener)
					.show();
				} else {
					builder.setMessage(getString(R.string.first_letter_msg_2));
					builder.setNegativeButton(getString(R.string.ok), dialogClickListener)
					.show();
				}

			}
		});

		btn_bomb.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							if (Integer.parseInt(coins) >= Integer.parseInt(getString(R.string.how_much_for_bomb))) {
								btn_bomb.setVisibility(View.INVISIBLE);
								coins = "" + (Integer.parseInt(coins) - Integer.parseInt(getString(R.string.how_much_for_bomb)));
								TextView coins_txt = findViewById(R.id.textView1);
								coins_txt.setText(coins);
								writeData("" + (Integer.parseInt(lvl)) + "|"
										+ (Integer.parseInt(coins)));
								remove3Chars();
							}
							break;

						case DialogInterface.BUTTON_NEGATIVE:
							break;
						}
					}
				};

				// Check if sufficient coins
				AlertDialog.Builder builder = new AlertDialog.Builder(
						TheGame.this);
				builder.setTitle(getString(R.string.bomb_msg_3)).setIcon(R.drawable.help);
				if (Integer.parseInt(coins) >= Integer.parseInt(getString(R.string.how_much_for_bomb))) {
					builder.setMessage(getString(R.string.bomb_msg_1));
					builder.setNegativeButton(getString(R.string.no), dialogClickListener)
					.setPositiveButton(getString(R.string.yes), dialogClickListener)
					.show();
				} else {
					builder.setMessage(getString(R.string.bomb_msg_2));
					builder.setNegativeButton(getString(R.string.ok), dialogClickListener)
					.show();
				}
			}
		});

btn_first.setOnTouchListener(new View.OnTouchListener() {
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if(arg1.getActionMasked()==MotionEvent.ACTION_DOWN){
			int image_id = mContext.getResources().getIdentifier("bo1_h", "drawable",
					mContext.getPackageName());
			btn_first.setBackgroundResource(image_id);
			return false;
		}else
			if(arg1.getActionMasked()==MotionEvent.ACTION_UP){
				int image_id = mContext.getResources().getIdentifier("bo1", "drawable",
						mContext.getPackageName());
				btn_first.setBackgroundResource(image_id);
				return false;
			}	
		return true;
	}
});
btn_bomb.setOnTouchListener(new View.OnTouchListener() {
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if(arg1.getActionMasked()==MotionEvent.ACTION_DOWN){
			int image_id = mContext.getResources().getIdentifier("bo2_h", "drawable",
					mContext.getPackageName());
			btn_bomb.setBackgroundResource(image_id);
			return false;
		}else
			if(arg1.getActionMasked()==MotionEvent.ACTION_UP){
				int image_id = mContext.getResources().getIdentifier("bo2", "drawable",
						mContext.getPackageName());
				btn_bomb.setBackgroundResource(image_id);
				return false;
			}	
		return true;
	}
});
btn_ask.setOnTouchListener(new View.OnTouchListener() {
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if(arg1.getActionMasked()==MotionEvent.ACTION_DOWN){

			int image_id = mContext.getResources().getIdentifier("bo3_h", "drawable",
					mContext.getPackageName());
			btn_ask.setBackgroundResource(image_id);
			return false;
		}else
			if(arg1.getActionMasked()==MotionEvent.ACTION_UP){
				int image_id = mContext.getResources().getIdentifier("bo3", "drawable",
						mContext.getPackageName());
				btn_ask.setBackgroundResource(image_id);
				return false;
			}	
		return true;
	}
});
btn_skip.setOnTouchListener(new View.OnTouchListener() {
	
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		if(arg1.getActionMasked()==MotionEvent.ACTION_DOWN){
			int image_id = mContext.getResources().getIdentifier("bo4_h", "drawable",
					mContext.getPackageName());
			btn_skip.setBackgroundResource(image_id);
			return false;
		}else
			if(arg1.getActionMasked()==MotionEvent.ACTION_UP){
				int image_id = mContext.getResources().getIdentifier("bo4", "drawable",
						mContext.getPackageName());
				btn_skip.setBackgroundResource(image_id);
				return false;
			}	
		return true;
	}
});

		btn_skip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case DialogInterface.BUTTON_POSITIVE:
							if (Integer.parseInt(coins) >= Integer.parseInt(getString(R.string.how_much_for_skip))) {
								btn_skip.setVisibility(View.INVISIBLE);
								coins = "" + (Integer.parseInt(coins) - Integer.parseInt(getString(R.string.how_much_for_skip)));
								TextView coins_txt = findViewById(R.id.textView1);
								coins_txt.setText(coins);
								writeData("" + (Integer.parseInt(lvl) + 1) + "|"
										+ (Integer.parseInt(coins)));
								finish();
								startActivity(getIntent());
							}
							break;
						case DialogInterface.BUTTON_NEGATIVE:
							break;
						}
					}
				};

				// Check if sufficient coins
				AlertDialog.Builder builder = new AlertDialog.Builder(
						TheGame.this);
				builder.setTitle(getString(R.string.skip_msg_3)).setIcon(R.drawable.help);
				if (Integer.parseInt(coins) >= Integer.parseInt(getString(R.string.how_much_for_skip))) {
					builder.setMessage(getString(R.string.skip_msg_1));
					builder.setNegativeButton(getString(R.string.no), dialogClickListener)
					.setPositiveButton(getString(R.string.yes), dialogClickListener)
					.show();
				} else {
					builder.setMessage(getString(R.string.skip_msg_2));
					builder.setNegativeButton(getString(R.string.ok), dialogClickListener)
					.show();
				}
			}
		});


		if (Integer.parseInt(lvl) % Integer.parseInt(getString(R.string.number_of_stage_ad)) == 0) {
			interstitial = new InterstitialAd(this);
			interstitial.setAdUnitId(getString(R.string.admob_intertestial_id));
			interstitial.loadAd(new AdRequest.Builder().build());
//			mp.pause();	
			interstitial.show();
			if (!interstitial.isLoaded()) {
				AdRequest adRequest1 = new AdRequest.Builder()
				.build();
				// Begin loading your interstitial.
				interstitial.loadAd(adRequest1);
			}
			interstitial.setAdListener(new AdListener() {
				@Override
				public void onAdLoaded() {
					super.onAdLoaded();
//					mp.start();
					interstitial.show();
				}
			});
		} 

		btn_back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});

		btn_ask.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					// only for gingerbread and newer versions

					if (ContextCompat.checkSelfPermission(TheGame.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
						// Permission is not granted
						if (!ActivityCompat.shouldShowRequestPermissionRationale(TheGame.this,
								Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
							// No explanation needed; request the permission
							ActivityCompat.requestPermissions(TheGame.this,
									new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
									MY_PERMISSIONS_WRITE);
						}else
							shareLevel();
					}else
						shareLevel();

				}else
					shareLevel();

			}
		});
	}

    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	
	}
	private int dp2px(){
		 DisplayMetrics displayMetrics = getApplicationContext().getResources().getDisplayMetrics();
		return (int)((45 * displayMetrics.density) + 0.5);
	}
	// Function that generate black squares, depending on the number of letters
	// in the word
	private void createWord(int length) {
		LinearLayout world_layout = findViewById(R.id.world_layout);
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(dp2px(),dp2px());

		word_btn = new Button[length];

		for (int i = 0; i < length; i++) {
			word_btn[i] = new Button(getApplicationContext());
			word_btn[i].setText("");
			word_btn[i].setId(i);
			word_btn[i].setTextColor(Color.parseColor("#ffffff"));
			word_btn[i].setTextSize(18);
			word_btn[i].setTypeface(Typeface.DEFAULT_BOLD);
			lp1.setMargins(2, 0, 0, 0);
			
			word_btn[i].setLayoutParams(lp1);
            
		  
		 //   word_btn[i].setPadding(0, 1, 0, 2);
			word_btn[i].setBackgroundResource(R.drawable.bg2);
			world_layout.addView( word_btn[i]);
            	
			word_btn[i].setOnClickListener(charOnClick(word_btn[i]));
		}
	}

	// Function that generate random letters + word's leter on orange buttons
	private void randomChars() {
		for (int i = 0; i < 12; i++) {
			randBtn[i].setOnClickListener(randCharClick(randBtn[i]));
			Random r = new Random();
			int i1 = r.nextInt(25);
			randBtn[i].setText(chars[i1]);
		}

		List<Integer> list = new LinkedList<>();
		for (int i = 0; i < 12; i++) {
			list.add(i);
		}

		Collections.shuffle(list);

        for (String aWord_array : word_array) {
            int value = list.remove(0);
            randBtn[value].setText(aWord_array.toUpperCase());
        }
	}
private void clear_wrong_letter(final Button button) {
	for (int i = 0; i < 12; i++) {
		if (randBtn[i].getVisibility() == View.INVISIBLE
				&& randBtn[i].getText() == button.getText()){
			randBtn[i].setVisibility(View.VISIBLE);
			count--;
			break;
		}
	}

	button.setText("");

}
	// Fuction that clear wrong letter from black squares
	private OnClickListener charOnClick(final Button button) {
		return new View.OnClickListener() {
			public void onClick(View v) {
             clear_wrong_letter(button);				
				
			}
		};
	}

	// Function for orange buttons
	private OnClickListener randCharClick(final Button btn) {
		return new View.OnClickListener() {
			public void onClick(View v) {
			count++;
			
			v.setVisibility(View.INVISIBLE);
			word_btn[word_array.length-count].setText(btn.getText());
			//	press.start();
				
				
				
				if(count==word_array.length)
				createResult();
			}
		};
	}

	// Function that check if the word is correct and showing correct/wrong
	// dialog
	private void createResult() {
        StringBuilder sb=new StringBuilder();
		for (int i = word_array.length-1; i >=0 ; i--) {
			if (word_btn[i].getText() != "") {
				sb.append( word_btn[i].getText());
			}
		}
		String resultWord = sb.toString();
		
			if (resultWord.equalsIgnoreCase(theWord)) {
				showMyDialog(1);
				suc.start();		
					
				
			} else {
				showMyDialog(2);
				fal.start();
		}
	}

	// Function that transform the word to array
	private String[] getWord(String str) {
		String[] chars = str.split("");
		List<String> selected_chars = new ArrayList<>();
        Collections.addAll(selected_chars, chars);
		selected_chars.remove(0);
		return selected_chars.toArray(new String[0]);
	}

	// //Function that showing dialogs: correct, wrong or zooming image
	private void showMyDialog(final int type) {
		final Dialog dialog = new Dialog(TheGame.this, R.style.dialogStyle);
		dialog.setContentView(R.layout.dialog);
		dialog.getWindow().getDecorView()
		.setBackgroundResource(R.drawable.dialog_bg);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setCancelable(false);
		dialog.getWindow().setLayout(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
		String points = ""
				+ ((new Random().nextInt(10 - 3) + 3) + word_array.length);
		SmartImageView image = dialog
				.findViewById(R.id.imageDialog);
		Button dialogBtn = dialog.findViewById(R.id.dialogBtn);
		TextView score = dialog.findViewById(R.id.points);

		if (type == 1) {
			image.setImageResource(R.drawable.corect);
			dialogBtn.setText(" المتابعة "); // Next level button
			score.setText("+" + points);
			writeData("" + (Integer.parseInt(lvl) + 1) + "|"
					+ (Integer.parseInt(coins) + Integer.parseInt(points)));
		} else if (type == 2) {
			image.setImageResource(R.drawable.gresit);
			dialogBtn.setText("  المحاولة مرة اخرى  "); // Try again button, restart
			// current level
			score.setText("-5");
			if (Integer.parseInt(coins) > 0 && Integer.parseInt(coins) <= 5) {
				writeData("" + (Integer.parseInt(lvl)) + "|"
						+ (Integer.parseInt("0")));
			} else {
				writeData("" + (Integer.parseInt(lvl)) + "|"
						+ (Integer.parseInt(coins) - 5));
			}
		} else {
			dialog.getWindow().setBackgroundDrawable(
					new ColorDrawable(android.graphics.Color.TRANSPARENT));
			score.setVisibility(View.GONE);
			dialogBtn.setVisibility(View.GONE);
//			ImageView coinicon = dialog
//					.findViewById(R.id.dialogIcon);
//			coinicon.setVisibility(View.GONE);
			image.setImageUrl(null);
			image.setOnClickListener(new View.OnClickListener() {
				@Override
			
				public void onClick(View v) {
					dialog.dismiss();
				}
			});
		}

		dialog.show();

		dialogBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (type > 0) {
					finish();
					startActivity(getIntent());
				}
				dialog.dismiss();
			}
		});

	}
	//		// Button that open "Share on Facebook" dialog
	//		fb.setOnClickListener(new OnClickListener() {
	//			@Override
	//			public void onClick(View v) {
	//				ByteArrayOutputStream stream = new ByteArrayOutputStream();
	//				getBitmapFromView().compress(Bitmap.CompressFormat.PNG, 100,
	//						stream);
	//				byte[] byteArray = stream.toByteArray();
	////				Intent i = new Intent(TheGame.this, LoginFragment.class);
	////				i.putExtra("image", byteArray);
	////				i.putExtra("lvl", lvl);
	////				startActivity(i);
	//				dialog.dismiss();
	//			}
	//		});


	// Function that save all user data. Current level, coins
	private void writeData(String dataStr) {
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					openFileOutput("thewords.dat", Context.MODE_PRIVATE));
			outputStreamWriter.write(dataStr);
			outputStreamWriter.close();
		} catch (IOException ignored) {
		}
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



	// Function that hide 3 orange buttons (letters)
    private void remove3Chars() {
		Button[] removeBtn = {findViewById(R.id.char1),
                findViewById(R.id.char2),
                findViewById(R.id.char3),
                findViewById(R.id.char4),
                findViewById(R.id.char5),
                findViewById(R.id.char6),
                findViewById(R.id.char7),
                findViewById(R.id.char8),
                findViewById(R.id.char9),
                findViewById(R.id.char10),
                findViewById(R.id.char11),
                findViewById(R.id.char12)};
		int x = 0;
		List<Integer> list = new LinkedList<>();
		for (int i = 0; i < 12; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		
		while (x != 3) {
            
			int value = list.remove(0);
			if (!Arrays.asList(word_array).contains(
					removeBtn[value].getText().toString())) {
				removeBtn[value].setVisibility(View.INVISIBLE);
				x += 1;

			}
		}
	}

	private void parseXML(int i) {
		AssetManager assetManager = getBaseContext().getAssets();
		try {
			InputStream is = assetManager.open("LevelData.xml");
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();

			LevelSAXParserHandler myXMLHandler = new LevelSAXParserHandler();
			xr.setContentHandler(myXMLHandler);
			InputSource inStream = new InputSource(is);
			xr.parse(inStream);

			ArrayList<Level> cartList = myXMLHandler.getCartList();
			if(i>=cartList.size())
			{	
				isLast=true;
			}
			else
			{
				Level level=cartList.get(i); 
				theWord=level.getAnswer();
				ImageFile=level.getMusicId();
				Ribbon=level.getRibbon();

			}
			is.close();
		} catch (Exception e) {
			e.printStackTrace(); 
		}
	}

	private String SaveBackground()
	{
		Bitmap bitmap;
		FutureTask<Bitmap> futureTask=new FutureTask<>(new Callable<Bitmap>() {
			@Override
			public Bitmap call() {
				RelativeLayout panelResult = findViewById(R.id.root);
				panelResult.invalidate();
				panelResult.setDrawingCacheEnabled(true);
				panelResult.buildDrawingCache();
				DisplayMetrics displaymetrics = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
				int i = displaymetrics.heightPixels;
				int j = displaymetrics.widthPixels;
				Bitmap bitmap= Bitmap.createScaledBitmap(Bitmap.createBitmap(panelResult.getDrawingCache()), j, i, true);
				panelResult.setDrawingCacheEnabled(false);
				return bitmap;
			}
		});
		runOnUiThread(futureTask);
		try {
			bitmap=futureTask.get();
		} catch (InterruptedException | ExecutionException e) {
			return null;
		}
		String s;
		File file;

		file = new File(sb.toString());
		 file.isDirectory();
        file.mkdir();
		FileOutputStream fileoutputstream1 = null;
		s = (new StringBuilder(String.valueOf("guess"))).append("_sound_").append(System.currentTimeMillis()).append(".png").toString();
		try {
			fileoutputstream1 = new FileOutputStream(new File(file, s));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileOutputStream fileoutputstream = fileoutputstream1;

		StringBuilder stringbuilder1;
		bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, fileoutputstream);
		stringbuilder1 = new StringBuilder();
		stringbuilder1.append(sb.toString()).append(File.separator).append(s);

		try {
			fileoutputstream.flush();
			fileoutputstream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ""+stringbuilder1;

	}
	private static Bitmap getRoundedCornerBitmap(Bitmap bmp, int radius) {
		 Bitmap sbmp;
		    if(bmp.getWidth() != radius || bmp.getHeight() != radius)
		        sbmp = Bitmap.createScaledBitmap(bmp, radius, radius, false);
		    else
		        sbmp = bmp;
		    Bitmap output = Bitmap.createBitmap(sbmp.getWidth(),
		            sbmp.getHeight(), Config.ARGB_8888);
		    Canvas canvas = new Canvas(output);

		    final Paint paint = new Paint();
		    final Rect rect = new Rect(0, 0, sbmp.getWidth(), sbmp.getHeight());

		    paint.setAntiAlias(true);
		    paint.setFilterBitmap(true);
		    paint.setDither(true);
		    canvas.drawARGB(0, 0, 0, 0);
		    paint.setColor(Color.parseColor("#BAB399"));
		    canvas.drawCircle(sbmp.getWidth() / 2+0.7f, sbmp.getHeight() / 2+0.7f,
		            sbmp.getWidth() / 2+0.1f, paint);
		    paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		    canvas.drawBitmap(sbmp, rect, rect, paint);


		            return output;
	    }
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (requestCode == MY_PERMISSIONS_WRITE && grantResults.length > 0
				&& grantResults[0] == PackageManager.PERMISSION_GRANTED) {
			shareLevel();
		}
	}

	private void shareLevel() {

		new ShareLevelTask(this).execute();
	}
  private static 	class ShareLevelTask extends AsyncTask<Void,Void,String>{
	  private final WeakReference<TheGame> activityReference;

	  // only retain a weak reference to the activity
	  ShareLevelTask(TheGame context) {
		  activityReference = new WeakReference<>(context);
	  }

		@Override
		protected void onPreExecute() {
			activityReference.get().findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
		}

		@Override
		protected String doInBackground(Void... voids) {
			return activityReference.get().SaveBackground();
		}

		@Override
		protected void onPostExecute(String path) {
			activityReference.get().findViewById(R.id.progressBar).setVisibility(View.GONE);
			File imagepath=	new File(path);

			Intent share = new Intent(Intent.ACTION_SEND);
			share.setType("image/png");
			share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imagepath));
			activityReference.get().startActivity(Intent.createChooser(share, "Share Image"));
		}
	}

}