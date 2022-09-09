package com.example.homework01.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.homework01.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

public class ImageHelper {
    private final static String TAG = ImageHelper.class.getSimpleName();
    public final static int AVATAR_WIDTH = 180;
    public final static int AVATAR_HEIGHT = 180;
    public final static RequestOptions avatarOptions = new RequestOptions()
            .placeholder(R.drawable.gray)
            .error(R.drawable.gray)
            .fallback(R.drawable.gray)
            .override(ImageHelper.AVATAR_WIDTH, ImageHelper.AVATAR_HEIGHT);

    public static Bitmap imageScale(Drawable drawable, int dst_w, int dst_h){
        return imageScale(((BitmapDrawable) drawable).getBitmap(), dst_w,dst_h);
    }

    public static Bitmap imageScale(Bitmap bitmap, int dst_w, int dst_h) {
        int src_w = bitmap.getWidth();
        int src_h = bitmap.getHeight();
        Log.i(TAG, "imageScale: " + String.format("{w: %d, h: %d}", src_w, src_h));
        float scale_w = ((float) dst_w) / src_w;
        float scale_h = ((float) dst_h) / src_h;
        Matrix matrix = new Matrix();
        matrix.postScale(scale_w, scale_h);
        Bitmap dstbmp = Bitmap.createBitmap(bitmap, 0, 0, src_w, src_h, matrix,
                true);
        return dstbmp;
    }

    public static Drawable getImgFromApp(String path){
        return getImgFromParentPath(PathUtils.getExternalAppDcimPath(), path);
    }

    public static Drawable getImgFromLocal(String path){
        return getImgFromParentPath(PathUtils.getExternalDcimPath(), path);
    }

    @Deprecated
    public static Drawable getImgFromParentPath(String parent, String path){
        return null;
    }

    public static boolean saveImgToApp(Context context, Drawable drawable, Bitmap.CompressFormat format, String path) {
        return saveImgToParentPath(context,drawable,format,PathUtils.getExternalAppDcimPath(),path);
    }

    public static boolean saveImgToLocal(Context context, Bitmap bitmap, Bitmap.CompressFormat format, String path) {
        return saveImgToParentPath(context,bitmap,format,PathUtils.getExternalDcimPath(),path);
    }


    public static boolean saveImgToApp(Context context, Bitmap bitmap, Bitmap.CompressFormat format, String path) {
        return saveImgToParentPath(context,bitmap,format,PathUtils.getExternalAppDcimPath(),path);
    }

    public static boolean saveImgToLocal(Context context, Drawable drawable, Bitmap.CompressFormat format, String path) {
        return saveImgToParentPath(context,drawable,format,PathUtils.getExternalDcimPath(),path);
    }


    public static boolean saveImgToParentPath(Context context, Drawable drawable, Bitmap.CompressFormat format, String parent, String path){
        return saveImgToParentPath(context,((BitmapDrawable) drawable).getBitmap(),format,parent,path);
    }

    public static boolean saveImgToParentPath(Context context, Bitmap bitmap, Bitmap.CompressFormat format, String parent, String path) {
        FileOutputStream out = null;

        if (bitmap == null) {
            return false;
        }

        try {
            File file = new File(parent, path);
            Log.i(TAG, "saveImgToParentPath path = " + file.getAbsolutePath());

            /** better than {@link File} */
            FileUtils.createFileByDeleteOldFile(file);

            out = new FileOutputStream(file);
            bitmap.compress(format, 100, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean deleteImgInParentPath(String parent, String path){
        File file = new File(parent, parent);
        if(!file.exists() || file.delete()){
            return true;
        }
        return false;
    }

    public static boolean deleteImgInApp(String path){
        return deleteImgInParentPath(PathUtils.getExternalAppDcimPath(), path);
    }

    public static boolean  deleteImgInLocal(String path){
        return deleteImgInParentPath(PathUtils.getExternalDcimPath(), path);
    }

    public static void saveImgToLocal(Context context, String url, String path) {
        //如果是网络图片，抠图的结果，需要先保存到本地
        Glide.with(context)
                .downloadOnly()
                .load(url)
                .listener(new RequestListener<File>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<File> target, boolean isFirstResource) {
                        Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResourceReady: download fail");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(File resource, Object model, Target<File> target, DataSource dataSource, boolean isFirstResource) {
                        Toast.makeText(context, "下载成功", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResourceReady: download success = " + resource.getAbsolutePath());
                        saveToAlbum(context, resource.getAbsolutePath(), path);
                        return false;
                    }
                })
                .preload();
    }

    private static boolean saveToAlbum(Context context, String srcPath, String path) {
        File file = new File(PathUtils.getExternalDcimPath(), path);
        boolean isCopySuccess = FileUtils.copy(srcPath, file.getAbsolutePath());
        if (isCopySuccess) {
            //发送广播通知
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));
            ToastUtils.showShort("图片保存到相册成功");
            Log.i(TAG, "saveToAlbum: save suceess = " + file.getAbsolutePath());
        } else {
            ToastUtils.showShort("图片保存到相册失败");
            Log.i(TAG, "saveToAlbum: save fail");
        }
        return isCopySuccess;
    }

}
