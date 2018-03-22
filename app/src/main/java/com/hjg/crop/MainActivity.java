package com.hjg.crop;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends TakePhotoActivity implements View.OnClickListener {

    private Button xiangji, xiangce;
    ImageView iv_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xiangji = findViewById(R.id.xiangji);
        xiangce = findViewById(R.id.xiangce);
        iv_img = findViewById(R.id.iv_img);
        //拍照
        xiangji.setOnClickListener(this);
        xiangce.setOnClickListener(this);
    }

    /*裁剪相关属性*/
    private CropOptions getCropOptions() {
        int height = 800;
        int width = 800;
        CropOptions.Builder builder = new CropOptions.Builder();
        builder.setAspectX(width).setAspectY(height);//裁剪宽高等比例
        builder.setWithOwnCrop(false);//是否使用takephtoto自帶的裁剪工具
        return builder.create();
    }


    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Glide.with(this).load(new File(result.getImages().get(0).getCompressPath())).into(iv_img);
    }


    /*压缩*/
    private void configCompress(TakePhoto takePhoto) {
        int maxSize = 1024 * 1;//压缩后的最大size
        int width = 800;
        int height = 800;
        boolean showProgressBar = true;//是否展示压缩进度
        boolean enableRawFile = false;//压缩后是否保存原图
        CompressConfig config;
        /*采用原生压缩*/
//        if (rgCompressTool.getCheckedRadioButtonId() == R.id.rbCompressWithOwn) {
        config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();
//        } else {
        /*采用鲁班压缩*/
//        LubanOptions option = new LubanOptions.Builder()
//                .setMaxHeight(height)
//                .setMaxWidth(width)
//                .setMaxSize(maxSize)
//                .create();
//        config = CompressConfig.ofLuban(option);
        config.enableReserveRaw(enableRawFile);
//        }
        takePhoto.onEnableCompress(config, showProgressBar);
    }

    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        /*使用takePhoto自带相册，自带相册可多选图*/
        builder.setWithOwnGallery(false);
        /*是否纠正拍照角度*/
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }

    @Override
    public void onClick(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        TakePhoto takePhoto = getTakePhoto();
        configCompress(takePhoto);
        configTakePhotoOption(takePhoto);
        switch (view.getId()) {
            case R.id.xiangji:
                takePhoto.onPickFromCaptureWithCrop(imageUri, getCropOptions());
                break;
            case R.id.xiangce:
                takePhoto.onPickFromGalleryWithCrop(imageUri, getCropOptions());
        }
    }
}
