package com.yoursecondworld.secondworld.common.imagePreview;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseAct;
import com.yoursecondworld.secondworld.common.Constant;
import com.yoursecondworld.secondworld.common.FrescoImageResizeUtil;
import com.yoursecondworld.secondworld.common.ImageLoadingDrawable;
import com.yoursecondworld.secondworld.common.ViewPagerViewAdapter;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.os.ScreenUtils;
import xiaojinzi.base.android.os.T;

/**
 * 这个activity提供图片预览的功能,暂时没有图片方法的功能<br/>
 * 双击弹出菜单,单机退出预览
 */
@Injection(R.layout.act_image_preview)
public class ImagePreviewAct extends BaseAct implements View.OnLongClickListener, View.OnClickListener {

    private String savePath;

    private Handler h = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);

            int what = msg.what;

            if (what == 0) {
                T.showLong(context, "保存成功,路径为:" + savePath);
                savePath = null;
            } else {
                tip("保存失败\n请确保您的sd卡正常使用并且给予了应用存储的权限");
            }

        }
    };

    /**
     * 意图中的图片集合的标识
     */
    public static final String IMAGES_FLAG = "IMAGES_FLAG";

    /**
     * 意图中下标的标识
     */
    public static final String POSITION = "position";

    /**
     * 用于提示当前是第几张
     */
    @Injection(R.id.tv_tip)
    private TextView tv_tip = null;

    /**
     * 展示
     */
    @Injection(R.id.vp)
    private ViewPager vp = null;

    /**
     * 展示的图片的集合
     */
    private ArrayList<String> imageUris = new ArrayList<String>();

    private int index;

    /**
     * ViewPager用的试图
     */
    private ArrayList<View> views = new ArrayList<View>();

    @Override
    public void initView() {
        super.initView();

        int statusHeight = ScreenUtils.getStatusHeight(context);
        ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) tv_tip.getLayoutParams();
        lp.topMargin = 2 * statusHeight;


        //拿到药展示的图片的地址
        imageUris = getIntent().getExtras().getStringArrayList(IMAGES_FLAG);
        //拿到点击的图片的下标
        index = getIntent().getIntExtra(POSITION, 0);
        if (index < 0) {
            index = 0;
        }
        if (index > imageUris.size() - 1) {
            index = imageUris.size() - 1;
        }
        if (imageUris != null) {
            int length = imageUris.size();
            for (int i = 0; i < length; i++) {
                String image = imageUris.get(i);
                View itemView = View.inflate(context, R.layout.act_image_preview_item, null);
                SimpleDraweeView sdv = (SimpleDraweeView) itemView.findViewById(R.id.sdv);

                //设置加载的进度条
//                GenericDraweeHierarchyBuilder hierarchyBuilder = new GenericDraweeHierarchyBuilder(context.getResources());
//                hierarchyBuilder
//                        .setProgressBarImage(new ImageLoadingDrawable());
//                sdv.setHierarchy(hierarchyBuilder.build());

//                sdv.setTag(i);
                sdv.setOnLongClickListener(this);
                sdv.setOnClickListener(this);
//                sdv.setImageURI(Uri.parse(image + Constant.MORIGIN_IMAGE));
//                sdv.setImageURI(Uri.parse(image));

                Glide.with(context).load(image).into(sdv);

                views.add(itemView);

            }
        } else {
            L.s(TAG, "是空的");
        }
        vp.setAdapter(new ViewPagerViewAdapter(views) {
        });

        vp.setCurrentItem(index);
        tv_tip.setText((index + 1) + "/" + views.size());

    }

    @Override
    public void setOnlistener() {
        super.setOnlistener();
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //设置提示文本的选中的下标/总的图片数量
                tv_tip.setText((position + 1) + "/" + views.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    @Override
    public boolean onLongClick(View view) {

        final BottomSheetDialog b = new BottomSheetDialog(context);
        View contentView = View.inflate(context, R.layout.act_image_preview_menu, null);

        TextView tv_save = (TextView) contentView.findViewById(R.id.tv_save);
        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            FileBinaryResource resource = (FileBinaryResource) Fresco
                                    .getImagePipelineFactory()
                                    .getMainDiskStorageCache()
                                    .getResource(new SimpleCacheKey(imageUris.get(vp.getCurrentItem()) + Constant.MORIGIN_IMAGE));
                            File file = resource.getFile();
                            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                            File folder = new File(Environment.getExternalStorageDirectory(), Constant.SDCARD_IMAGE_FOLDER);
                            if (!folder.exists()) {
                                folder.mkdirs();
                            }
                            final File f = new File(folder, System.currentTimeMillis() + ".png");
                            bitmap.compress(Bitmap.CompressFormat.PNG, 90, new FileOutputStream(f));
                            bitmap.recycle();
                            savePath = f.getPath();
                            h.sendEmptyMessage(0);
                            bitmap = null;

                            //通知相册
                            MediaScannerConnection.scanFile(context, new String[]{f.getPath()}, new String[]{"image/png"}, new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String s, Uri uri) {
                                }
                            });

                        } catch (Exception e) {
                            //e.printStackTrace();
                            h.sendEmptyMessage(1);
                        }

                    }
                }.start();
                b.dismiss();
            }
        });

        TextView tv_cancel = (TextView) contentView.findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });

        b.setContentView(contentView);
        b.show();
        return true;

    }

    @Override
    public void onClick(View view) {
        finish();
    }

    @Override
    public boolean isRegisterEvent() {
        return true;
    }

    /**
     * 当sessionId失效了
     */
    @Subscribe
    public void onSessionIdInvalid(SessionInvalidEvent event) {
        finish();
    }

}
