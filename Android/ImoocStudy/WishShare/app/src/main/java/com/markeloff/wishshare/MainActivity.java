package com.markeloff.wishshare;

/**
 *  参照 http://www.imooc.com/learn/592
 *  学习利用截图制作贺卡，并分享到微信朋友圈。
 * */

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ImageView mPhoto;
    private EditText mWord;
    private Button mShare;
    private IWXAPI iwxapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPhoto = (ImageView) findViewById(R.id.photo);
        mPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 100);
            }
        });

        mWord = (EditText) findViewById(R.id.word);
        mWord.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/test.ttf"));

        iwxapi = WXAPIFactory.createWXAPI(this, "wx335b9226947437e8", false);
        iwxapi.registerApp("wx335b9226947437e8");

        mShare = (Button) findViewById(R.id.share);
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechatShare();
                mShare.setVisibility(View.VISIBLE);
            }
        });
    }

    private void wechatShare() {
        int flag = 0;
        WXWebpageObject webpage = new WXWebpageObject();
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = new WXImageObject(generateWishCard());

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = String.valueOf(System.currentTimeMillis());
        req.message = msg;
        req.scene  = flag == 0 ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        iwxapi.sendReq(req);
    }

    private Bitmap generateWishCard() {
        mShare.setVisibility(View.INVISIBLE);

        View view = getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();

        return view.getDrawingCache();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            if (data != null) {
                Log.d(TAG, "onActivityResult, image is: " + data.getDataString());
                mPhoto.setImageURI(data.getData());
            }
        }
    }
}
