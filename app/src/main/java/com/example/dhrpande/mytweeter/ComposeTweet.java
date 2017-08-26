package com.example.dhrpande.mytweeter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import static com.example.dhrpande.mytweeter.R.layout.activity_compose_tweet;

//import io.fabric.sdk.android.Fabric;

public class ComposeTweet extends AppCompatActivity {

    TextView tweetText;
    Button tweetButton;
    ImageView image;
    String FileName = "first";

    //String text = "This \nis \nmultiline";

    final Rect bounds = new Rect();
    TextPaint textPaint = new TextPaint() {
        {
            setColor(Color.WHITE);
            setTextAlign(Paint.Align.LEFT);
            setTextSize(20f);
            setAntiAlias(true);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_compose_tweet);

        tweetText = (TextView) findViewById(R.id.tweetText);
        tweetButton = (Button) findViewById(R.id.tweetButton);
        image = (ImageView) findViewById(R.id.tweetImage);

      //  TwitterAuthConfig authConfig =  new TwitterAuthConfig("consumerKey", "consumerSecret");
       // Fabric.with(this, new TwitterCore(authConfig), new TweetComposer());

       // TweetComposer.Builder builder = new TweetComposer.Builder(this)
       //         .text("Hello there ..")
       //         .image(myImageUri);
        // builder.show();
    }

    public void onClickTweet(View view) {

        String myTweet = tweetText.getText().toString();

        textPaint.getTextBounds(myTweet, 0, myTweet.length(), bounds);
        StaticLayout mTextLayout = new StaticLayout(myTweet, textPaint,
                bounds.width(), Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        int maxWidth = -1;
        for (int i = 0; i < mTextLayout.getLineCount(); i++) {
            if (maxWidth < mTextLayout.getLineWidth(i)) {
                maxWidth = (int) mTextLayout.getLineWidth(i);
            }
        }
        final Bitmap bmp = Bitmap.createBitmap(maxWidth , mTextLayout.getHeight(),
                Bitmap.Config.ARGB_8888);
        bmp.eraseColor(Color.BLACK);// just adding black background
        final Canvas canvas = new Canvas(bmp);
        mTextLayout.draw(canvas);

        image.setImageBitmap(bmp);

       // Uri contentUri = Uri.fromFile(f);

      /*  File myImageFile = Environment.getExternalStorageDirectory();
        File file = new File(myImageFile.getAbsolutePath()+"/DCIM/Camera/img.jpg");

        try
        {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        */
        Context context;
        context = getBaseContext();
        CapturePhotoUtils capturePhotoUtils = new CapturePhotoUtils();
        Uri myImageUri = capturePhotoUtils.insertImage(context.getContentResolver(), bmp, "Tweet", "first");


     //    = Uri.parse( url );

        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text(myTweet)
                .image(myImageUri);
        builder.show();

    }

}
