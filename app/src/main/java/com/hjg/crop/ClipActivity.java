package com.hjg.crop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

public class ClipActivity extends Activity {
    private static final String TAG = "ClipActivity";
    private ClipImageLayout mClipImageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipimage);
        initGui();
    }


    protected void initGui() {
        mClipImageLayout = (ClipImageLayout) findViewById(R.id.id_clipImageLayout);
    }


    /**
     * 上传头像（）
     */
    private void uploading() {
        //得到裁剪之后的bitmap
        Bitmap bitmap = mClipImageLayout.clip();//得到裁剪后的图片
//        bitmap = BitmapUtil.compressImageLimit(bitmap);//压缩图片
    }


}
