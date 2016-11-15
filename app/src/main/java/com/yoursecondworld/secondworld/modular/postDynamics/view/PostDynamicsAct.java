package com.yoursecondworld.secondworld.modular.postDynamics.view;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.xiaojinzi.ximagelib.config.XImgSelConfig;
import com.xiaojinzi.ximagelib.imageView.XSelectAct;
import com.xiaojinzi.ximagelib.utils.XImageLoader;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.BaseFragmentAct;
import com.yoursecondworld.secondworld.common.ColorUtil;
import com.yoursecondworld.secondworld.common.EmojiReplaceUtil;
import com.yoursecondworld.secondworld.common.FrescoImageResizeUtil;
import com.yoursecondworld.secondworld.common.event.SessionInvalidEvent;
import com.yoursecondworld.secondworld.modular.db.dynamicsDraft.DynamicsDraftDao;
import com.yoursecondworld.secondworld.modular.db.dynamicsDraft.DynamicsDraftDb;
import com.yoursecondworld.secondworld.modular.dynamics.entity.NewDynamics;
import com.yoursecondworld.secondworld.modular.postDynamics.ImageStore;
import com.yoursecondworld.secondworld.modular.postDynamics.UriUtils;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectEmoji.SelectEmojiFragment;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectEmoji.event.SelectEmojiEvent;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage.SelectImageFragment;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage.event.RequestReFreshEvent;
import com.yoursecondworld.secondworld.modular.postDynamics.presenter.DynamicsDraftPresenter;
import com.yoursecondworld.secondworld.modular.postDynamics.widget.ShowImagesViewForPostDynamics;
import com.yoursecondworld.secondworld.service.PostDynamicsService;
import com.yoursecondworld.secondworld.service.event.AppendDynamicsTaskEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import xiaojinzi.annotation.Injection;
import xiaojinzi.autolayout.utils.AutoUtils;
import xiaojinzi.base.android.image.ImageUtil;
import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.os.KeyBoardUtils;
import xiaojinzi.base.android.os.ScreenUtils;
import xiaojinzi.base.android.os.T;


/**
 * 发表动态的界面
 */
@Injection(R.layout.act_post_dynamics)
public class PostDynamicsAct extends BaseFragmentAct implements View.OnClickListener, IDynamicsDraftView {

    /**
     * 选择视频的请求码
     */
    public static final int SELECT_VIDEO_REQUESTCODE = 567;

    public static final int INPUT_MONEY_REQUESTCODE = 57;

    public static final int REQUEST_SELECT_IMAGE_CODE = 345;

    public static final String GAME_TAG = "gameTag";
    public static final String TYPE_TAG = "typeTag";
    public static final String CONTENT_TAG = "contentTag";
    public static final String IMAGES_TAG = "imagesTag";
    public static final String VIDEO_TAG = "videoTag";
    public static final String MONEY_TAG = "moneyTag";


    /**
     * 草稿箱的id,如果有这个值传进来,表示这次是更新草稿箱的
     */
    public static final String DRAFT_ID_TAG = "draftId";

    @Injection(R.id.rl_root)
    private RelativeLayout rl_root = null;

    @Injection(R.id.rl_content)
    private RelativeLayout rl_content = null;

    /**
     * 标题栏
     */
    @Injection(R.id.rl_act_titlebar)
    private RelativeLayout rl_titlebar = null;

    /**
     * 标题栏中左边的文本
     */
    @Injection(value = R.id.tv_back, click = "clickView")
    private TextView tv_cancel = null;

    /**
     * 标题栏中的标题
     */
    @Injection(R.id.tv_title_name)
    private TextView tv_titleName = null;

    /**
     * 标题栏中右边的文本菜单
     */
    @Injection(value = R.id.tv_menu, click = "clickView")
    private TextView tv_menu = null;

    //==============================================================================================

    //最底部的五个图标============================

    @Injection(value = R.id.rl_content_foot_two_image, click = "clickView")
    private RelativeLayout rl_image = null;

    @Injection(value = R.id.rl_content_foot_two_video, click = "clickView")
    private RelativeLayout rl_video = null;

    @Injection(value = R.id.rl_content_foot_two_emoji, click = "clickView")
    private RelativeLayout rl_emoji = null;

//    @Injection(value = R.id.rl_content_foot_two_longtext, click = "clickView")
//    private RelativeLayout rl_longtext = null;

    //==================================内容编辑区域=====================================

    @Injection(R.id.et_content)
    private EditText et_content = null;

    @Injection(R.id.rl_money_container)
    private RelativeLayout rl_moneyContainer = null;

    //金额的编辑框
    @Injection(value = R.id.et_content_foot_money, click = "clickView")
    private EditText et_money;

    @Injection(R.id.view_images_container)
    private ShowImagesViewForPostDynamics imagesContainer = null;

    @Injection(R.id.rl_show_video_info)
    private RelativeLayout rl_showVideoInfo = null;

    @Injection(R.id.iv_show_video_first_frame)
    private ImageView iv_showVideoFirstFrame = null;

    @Injection(R.id.tv_filesize)
    private TextView tv_fileSize = null;

    @Injection(R.id.tv_video_duration)
    private TextView tv_video_duration = null;

    //==================================两个游戏标签=========================================

    @Injection(R.id.tv_content_foot_one_label_one)
    private TextView tv_labelOne = null;

    @Injection(R.id.tv_content_foot_one_label_two)
    private TextView tv_labelTwo = null;

    //==================================底部的FrameLayout

    @Injection(R.id.fl)
    private FrameLayout fl = null;

    /**
     * 屏幕高度
     */
    private int screenHeight;

    /**
     * 状态栏的高度
     */
    private int stateHeight;

    /**
     * 标题栏高度
     */
    private int titlebarHeight;

    //=====================================传递过来的数据==============================
    private String gameTag;
    private String topicTag;

    //=============================草稿的id=============================================
    /**
     * 如果是-1表示不是从草稿中点击过来的
     */
    private int draftId;

    /**
     * 由于显示视频的第一针是异步的
     * 所以这里是处理异步处理视频第一针返回的bitmap的
     */
    private Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            int what = msg.what;
            if (what == 1) {
                doShowImageSelect();
                return;
            }

            //如果如片不为空,就设置到imageView上
            if (bitmap != null) {
                iv_showVideoFirstFrame.setImageBitmap(bitmap);
                //释放图片的资源
                bitmap = null;
            }
        }
    };

    /**
     * 发布的主持人
     */
//    private PostDynamicsPresenter presenter = new PostDynamicsPresenter(this);

    private DynamicsDraftPresenter dynamicsDraftPresenter = new DynamicsDraftPresenter(this);

    /**
     * 要获取的视频的第一针
     */
    private Bitmap bitmap = null;

    /**
     * 所选的视频的地址
     */
//    private String videoPath = "http://2449.vod.myqcloud.com/2449_43b6f696980311e59ed467f22794e792.f20.mp4";
    private String videoPath = null;

    @Override
    public void initView() {
        super.initView();
        initBase();
        //启动发布的服务
        Intent service = new Intent(this, PostDynamicsService.class);
        context.startService(service);
    }

    /**
     * 初始化基本的信息
     */
    private void initBase() {
        //沉浸式状态栏生效
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) rl_titlebar.getLayoutParams();
        lp.topMargin = statusHeight;

        //获取屏幕和状态栏和标题栏的高度
        screenHeight = ScreenUtils.getScreenHeightPixels(context);
        stateHeight = ScreenUtils.getStatusHeight(context);
        titlebarHeight = (int) getResources().getDimension(R.dimen.titlebar_height);
        titlebarHeight = AutoUtils.getPercentHeightSize(titlebarHeight);

        tv_titleName.setText("发表动态");
        tv_menu.setText("发布");
        tv_menu.setVisibility(View.VISIBLE);

        //设置显示图片的控件的图片之间的间距
        imagesContainer.setHorizontalIntervalDistance(AutoUtils.getPercentWidthSize(4));
        imagesContainer.setVerticalIntervalDistance(AutoUtils.getPercentHeightSize(4));

        //解决键盘弹出的时候调整布局的问题
        controlKeyboardLayout(rl_root, rl_content);

        //===================================以下是拿到可能是草稿中传递过来的信息=========================================

        draftId = getIntent().getIntExtra(DRAFT_ID_TAG, -1);

        //拿到用户选择的游戏标签
        gameTag = getIntent().getStringExtra(GAME_TAG);
        topicTag = getIntent().getStringExtra(TYPE_TAG);

        if (!"剁手时刻".equals(topicTag)) { //如果不是剁手金额
            rl_moneyContainer.setVisibility(View.GONE);
        }else{
            //剁手时刻专有
            et_content.setHint("说说你对这次氪金的感受");
        }

        //检查参数的正确性
        if (TextUtils.isEmpty(gameTag) || TextUtils.isEmpty(topicTag)) {
            T.showShort(context, "游戏标签或者话题的参数不对");
            finish();
            return;
        }

        String gameName = gameTag;
        if (gameName.length() < 4) {
            gameName = "    " + gameName + "    ";
        }

        //两个标签
        tv_labelOne.setText(gameName);
        tv_labelTwo.setText(topicTag);

        //内容
        String content = getIntent().getStringExtra(CONTENT_TAG);
        if (!TextUtils.isEmpty(content)) {
            et_content.setText(content);
            et_content.setSelection(content.length());
        }

        //剁手金额
        int money = getIntent().getIntExtra(MONEY_TAG, 0);
        et_money.setText(money + "");

        //拿到图片的地址
        ArrayList<String> picture_list = getIntent().getStringArrayListExtra(IMAGES_TAG);
        if (picture_list != null) {
            ImageStore.getInstance().append(picture_list);
            displayImages();
        }

    }

    /**
     * 点击事件的集中处理
     *
     * @param v
     */
    public void clickView(View v) {

        int id = v.getId();
        switch (id) {

            case R.id.tv_back:
                if (ImageStore.getInstance().getImages().size() > 0 ||
                        videoPath != null ||
                        !TextUtils.isEmpty(getDynamicsContent())) {
                    popupSaveTip();
                } else {
                    finish();
                }
                break;
            case R.id.et_content_foot_money: //点击 moneyEditTExt

//                PopupMoneyInput popupMoneyInput = new PopupMoneyInput(context);
//
//                popupMoneyInput.setHeight(ScreenUtils.getScreenHeightPixels(context));
//                popupMoneyInput.showAtLocation(rl_root, Gravity.BOTTOM, 0, 0);

                startActivityForResult(new Intent(context, InputMoneyActivity.class), INPUT_MONEY_REQUESTCODE);

                break;
            case R.id.tv_menu: //发布的按钮

                if (TextUtils.isEmpty(getDynamicsContent())) {
                    tip("内容不能为空");
                    return;
                }

                //让主持人开始发布动态的协调工作
//                presenter.postDynamics();
                if (getDynamicsContent().trim().length() > 200) {
                    tip("内容过长");
                    return;
                }

                NewDynamics newDynamics = packDynamicsTask();

                //添加发布任务
                AppendDynamicsTaskEvent appendDynamicsTaskEvent = new AppendDynamicsTaskEvent();
                appendDynamicsTaskEvent.newDynamics = newDynamics;
                //投递任务
                EventBus.getDefault().post(appendDynamicsTaskEvent);
                finish();
                break;
            case R.id.rl_content_foot_two_image: //如果点击的是底部的图片按钮
                //展示选择图片的fragment
                showImageSelect();
                break;
            case R.id.rl_content_foot_two_video: //如果点击的是底部的视频按钮

                hideFragment();

                //发送选择视频的意图
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("video/*");
                startActivityForResult(Intent.createChooser(intent, "选择视频"), SELECT_VIDEO_REQUESTCODE);

                break;
            case R.id.rl_content_foot_two_emoji: //如果点击的是底部的表情按钮
                fl.setVisibility(View.VISIBLE);
                replaceFragment(new SelectEmojiFragment());
                break;
//            case R.id.rl_content_foot_two_longtext: //如果点击的是底部的长文按钮
//                break;
        }
    }

    /**
     * 跳转去获取一些数据的时候,回掉的方法
     * 1：目前此方法用于处理选择了视频的路径回掉的逻辑
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_SELECT_IMAGE_CODE && resultCode == RESULT_OK && data != null) {

            ArrayList<String> mImages = data.getStringArrayListExtra("data");

            List<String> images = ImageStore.getInstance().getImages();
            images.clear();
            images.addAll(mImages);

            displayImages();

        } else if (requestCode == SELECT_VIDEO_REQUESTCODE && resultCode == RESULT_OK) {
            //选择视频
            final Uri uri = data.getData();

            final MediaMetadataRetriever retr = new MediaMetadataRetriever();
            retr.setDataSource(context, uri);

            //获取时长
            String duration = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);

            //设置时常
            int realDuration = 0;
            try {
                realDuration = Integer.parseInt(duration) / 1000;
            } catch (Exception e) {
            }
            tv_video_duration.setText((realDuration - realDuration % 60) / 60 + ":" + realDuration % 60);

            videoPath = UriUtils.getPath(context, uri);

            //设置视频大小信息content://
            File file = new File(videoPath);
            long fileSize = 0;
            if (file.isFile()) {
                fileSize = file.length();
            }
            if (fileSize > 50 * 1024 * 1024) {
                tip("视频大小不能超过50兆");
                rl_showVideoInfo.setVisibility(View.GONE);
                return;
            }
            //调用文件大小的格式化的代码来显示大小
            tv_fileSize.setText(xiaojinzi.base.java.common.StringUtil.fileSizeFormat(fileSize));

            rl_showVideoInfo.setVisibility(View.VISIBLE);
            //让显示图片的九宫格控件的孩子全部移除,然后调用重新布局的方法
            imagesContainer.removeAllViewsInLayout();
            imagesContainer.requestLayout();

            //拿到第一针的画面
            new Thread() {
                @Override
                public void run() {
                    //获取第一针的画面
                    bitmap = retr.getFrameAtTime(1000, MediaMetadataRetriever.OPTION_CLOSEST);
                    L.s(TAG, "获取成功了");
                    h.sendEmptyMessage(0);
                }
            }.start();

        } else if (requestCode == INPUT_MONEY_REQUESTCODE && resultCode == InputMoneyActivity.RESPONSECODE) {
            String money = data.getStringExtra(InputMoneyActivity.MONEY_FLAG);
            et_money.setText(money);
        }
    }

    /**
     * 在弹出输入法的时候
     * 会动态的调整布局的大小
     * 不会导致我们的试图因为挤压而变形
     *
     * @param root             最外层布局
     * @param needToScrollView 要滚动的布局,就是说在键盘弹出的时候,你需要试图滚动上去的View,在键盘隐藏的时候,他又会滚动到原来的位置的布局
     */
    private void controlKeyboardLayout(final View root, final View needToScrollView) {
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private Rect r = new Rect();

            @Override
            public void onGlobalLayout() {
                //获取当前界面可视部分
                PostDynamicsAct.this.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //获取屏幕的高度
                int screenHeight = PostDynamicsAct.this.getWindow().getDecorView().getRootView().getHeight();
                //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
                int heightDifference = screenHeight - r.bottom;
                ViewGroup.LayoutParams layoutParams = needToScrollView.getLayoutParams();
                int height = screenHeight - titlebarHeight - heightDifference - stateHeight;
                if (height != layoutParams.height) {
                    layoutParams.height = height;
                    rl_root.requestLayout();
                    if (heightDifference != 0) { //如果此次是要弹出输入法的,那么隐藏fragment
                        hideFragment();
                    }
                }
            }
        });
    }

    /**
     * 选择图片的fragment
     */
    private SelectImageFragment selectImageFragment = null;

    /**
     * 展开选择图片的,选择图片的那个是一个fragment,需要切换
     */
    public void showImageSelect() {
        h.sendEmptyMessage(1);
    }

    private void doShowImageSelect() {
        fl.setVisibility(View.VISIBLE);
        if (selectImageFragment == null) {
            selectImageFragment = new SelectImageFragment();
        } else {
            selectImageFragment.reStore();
        }
        replaceFragment(selectImageFragment);
    }

    /**
     * 隐藏显示的图片或者显示的表情
     */
    public void hideFragment() {
        fl.setVisibility(View.GONE);
    }

    /**
     * 切换fragment,如果当前有输入法显示会先隐藏输入法
     *
     * @param f
     */
    private void replaceFragment(Fragment f) {
        //先隐藏输入法
        KeyBoardUtils.closeKeybord(et_content, context);
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fl, f);
//        fragmentTransaction.commit();
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 显示图片
     */
    public void displayImages() {

        //隐藏选择图片的fragment
        hideFragment();
        //让显示视频的区域不显示
        rl_showVideoInfo.setVisibility(View.GONE);
        //移除原本图片显示容器里面的所有图片
        imagesContainer.removeAllViewsInLayout();

        //拿到图片信息的集合
        List<String> localImages = ImageStore.getInstance().getImages();

        int size = localImages.size();
        //显示在界面的图片容器上
        for (int i = 0; i < size; i++) {
            View v = View.inflate(context, R.layout.act_post_dynamics_image_item, null);
            //创建图片显示的控件
            SimpleDraweeView sdv = (SimpleDraweeView) v.findViewById(R.id.sdv);
            //拿到右上角的小叉叉
            ImageView iv = (ImageView) v.findViewById(R.id.iv_delete);
            //设置点击事件
            iv.setOnClickListener(new MyDeleteImageListener(i));
            //创建文件对象
            File f = new File(localImages.get(i));
            //设置显示图片的控件的图片路径
            FrescoImageResizeUtil.setResizeControl(sdv, Uri.fromFile(f));
            //添加这个控件到九宫格中
            imagesContainer.addClildView(v);
        }

        if (size > 0 && size < 6) { //如果小于九张图片,添加一个添加更多图片的图标进去
            ImageView iv = new ImageView(this);
            //设置点击事件
            iv.setOnClickListener(this);
            iv.setPadding(10, 10, 10, 10);
            iv.setImageResource(R.mipmap.add2);
            imagesContainer.addClildView(iv);
        }
    }

    /**
     * 在返回的时候,应该先判断一下是否显示了选择图片的fragment或者选择表情的fragment
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //如果返回的时候表情面板和图片选择的面板还显示,就先关闭,返回动作作废
        if (keyCode == KeyEvent.KEYCODE_BACK && fl.getVisibility() == View.VISIBLE) {
            hideFragment();
            rl_root.requestLayout();
            return true;
        }

        //如果用户选了一部分的图片或者选择了视频文件或者有写了之后的游戏长文
        //提示用户是否需要保存
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (ImageStore.getInstance().getImages().size() > 0 ||
                    videoPath != null ||
                    !TextUtils.isEmpty(getDynamicsContent())) {
                popupSaveTip();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 弹出是否保存的提示
     */
    private void popupSaveTip() {

        //构建一个对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = View.inflate(context, R.layout.act_post_dynamics_popup_save_dynamics_window, null);

        TextView tv_tip = (TextView) view.findViewById(R.id.tv_tip);
        if (draftId == -1) {
            tv_tip.setText("是否保存草稿?");
        } else {
            tv_tip.setText("是否更新草稿?");
        }

        builder.setView(view);

        final AlertDialog dialog = builder.show();

        TextView tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        TextView tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);

        //确认按钮
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (draftId == -1) {
                    dynamicsDraftPresenter.saveDynamicsToDraft();
                } else {
                    dynamicsDraftPresenter.updateDynamicsToDraft();
                }
                finish();
            }
        });

        //取消按钮
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImageStore.getInstance().clear();
    }

    /**
     * 此点击事件用于弹出选择图片的框架
     * 是显示图片的时候,如果小于六张,会有一个加号
     * 就是这个加号的点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {

        openThirdImageSelect();

    }

    /**
     * 打开第三方图片选择
     */
    private void openThirdImageSelect() {
        XImageLoader imageLoader = new XImageLoader() {
            @Override
            public void load(Context context, String localPath, ImageView iv) {
                Glide.with(context).load(localPath).into(iv);
            }
        };

        XSelectAct.open(this, new XImgSelConfig.Builder(imageLoader)
                .btnConfirmText("完成")
                .selectImage((ArrayList<String>) ImageStore.getInstance().getImages())
                .title("图片")
                .isPreview(true)
                .statusBarColor(ColorUtil.getColor(context, R.color.common_app_color))
                .titlebarBgColor(ColorUtil.getColor(context, R.color.common_app_color))
                .maxNum(6)
                .isPreview(true)
                .cropSize(1, 1, 800, 800)
                .isCamera(true)
                .isCrop(true)
                .build(), PostDynamicsAct.REQUEST_SELECT_IMAGE_CODE);
    }

    @Override
    public ProgressDialog getDialog() {
        return dialog;
    }

    @Override
    public List<String> getSelectImages() {
        List<String> images = new ArrayList<String>();
        images.addAll(ImageStore.getInstance().getImages());
        return images;
    }

    @Override
    public String getVideoPath() {
        return videoPath;
    }

    @Override
    public String getDynamicsContent() {
        return et_content.getText().toString();
    }

    @Override
    public String getTopic() {
        return topicTag;
    }

    @Override
    public Integer getMoney() {
        int money = 0;
        try {
            money = Integer.parseInt(et_money.getText().toString());
        } catch (Exception e) {
        }
        return money;
    }

    @Override
    public String getGameLabel() {
        return gameTag;
    }

    /**
     * 当一张图片的时候需要返回图片的宽高比的信息
     * 返回:width*height
     *
     * @return
     */
    @Override
    public String getExtraInfo() {
        List<String> images = ImageStore.getInstance().getImages();
        if (images != null && images.size() == 1) {
            //获取唯一一张图片的路径
            String path = images.get(0);
            //获取到图片的信息
            BitmapFactory.Options options = ImageUtil.getBitMapOptions(path);
            return options.outWidth + "*" + options.outHeight;
        }
        return null;
    }

    @Override
    public int getDraftId() {
        return draftId;
    }

    /**
     * 动态草稿箱的数据库操作对象
     */
    private DynamicsDraftDao draftDao;

    @Override
    public DynamicsDraftDao getDynamicsDraftDao() {
        if (draftDao == null) {
            draftDao = new DynamicsDraftDao(new DynamicsDraftDb(context));
        }
        return draftDao;
    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void tip(String content) {
        T.showShort(context, content);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Subscribe
    public void onEventSelectedEmoji(SelectEmojiEvent selectEmojiEvent) {
        et_content.append(EmojiReplaceUtil.converse(context, selectEmojiEvent.emoji));
        et_content.setSelection(et_content.getText().length());
    }

    /**
     * 把要发布的动态打包成一个任务给后台用于发布
     *
     * @return
     */
    private NewDynamics packDynamicsTask() {
        NewDynamics newDynamics = new NewDynamics();
        newDynamics.setMoney(getMoney());
        newDynamics.setGame_tag(getGameLabel());
        newDynamics.setType_tag(getTopic());
        newDynamics.setContent(getDynamicsContent());
        newDynamics.setInfor_type(getExtraInfo());
        newDynamics.setPicture_list(getSelectImages());
        if (!TextUtils.isEmpty(getVideoPath())) {
            ArrayList<String> videos = new ArrayList<>();
            videos.add(getVideoPath());
            newDynamics.setVideo_list(videos);
        }
        return newDynamics;
    }

    /**
     * 点击图片右上角的叉叉的点击事件的实现类
     */
    private class MyDeleteImageListener implements View.OnClickListener {

        private int position;

        public MyDeleteImageListener(int position) {
            this.position = position;
        }

        /**
         * 点击了之后需要删除查查所在的图片
         *
         * @param view
         */
        @Override
        public void onClick(View view) {

            //拿到当前显示的图片的个数
            int size = ImageStore.getInstance().getImages().size();

            //移除图片
            imagesContainer.removeViewAt(position);

            ImageStore.getInstance().getImages().remove(position);

            if (size == 6) { //如果是六个的情况
                ImageView iv = new ImageView(PostDynamicsAct.this);
                //设置点击事件
                iv.setOnClickListener(PostDynamicsAct.this);
                iv.setPadding(10, 10, 10, 10);
                iv.setImageResource(R.mipmap.add2);
                imagesContainer.addClildView(iv);
            }

            if (size == 1) { //如果是一张图片的,那么删掉唯一一张的同时也要删掉添加更多那个图标
                imagesContainer.removeAllViewsInLayout();
            }

            //拿到的孩子的个数
            int childCount = imagesContainer.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                imagesContainer.getChildAt(i).findViewById(R.id.iv_delete).setOnClickListener(new MyDeleteImageListener(i));
            }

            imagesContainer.requestLayout();

            EventBus.getDefault().post(new RequestReFreshEvent());

        }
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
