package com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.xiaojinzi.ximagelib.config.XImgSelConfig;
import com.xiaojinzi.ximagelib.imageView.XSelectAct;
import com.xiaojinzi.ximagelib.utils.XImageLoader;
import com.yoursecondworld.secondworld.R;
import com.yoursecondworld.secondworld.common.ColorUtil;
import com.yoursecondworld.secondworld.modular.postDynamics.ImageStore;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage.adapter.SelectImageFragmentAdapter;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage.entity.ImageInfoEntity;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage.event.RequestReFreshEvent;
import com.yoursecondworld.secondworld.modular.postDynamics.fragment.selectImage.itemSpaceDecoration.ImageSpaceItemDecoration;
import com.yoursecondworld.secondworld.modular.postDynamics.view.PostDynamicsAct;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import xiaojinzi.activity.fragment.BaseFragment;
import xiaojinzi.annotation.Injection;
import xiaojinzi.base.android.adapter.recyclerView.CommonRecyclerViewAdapter;
import xiaojinzi.base.android.image.localImage.LocalImageInfo;
import xiaojinzi.base.android.image.localImage.LocalImageManager;
import xiaojinzi.base.android.log.L;
import xiaojinzi.base.android.os.ProgressDialogUtil;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/28.
 * 模仿qq的横向的选择图片的东东
 */
public class SelectImageFragment extends BaseFragment {

    @Injection(R.id.rv)
    private RecyclerView rv = null;

    @Injection(value = R.id.tv_image, click = "clickView")
    private TextView tv_image = null;

    @Injection(value = R.id.tv_send, click = "clickView")
    private Button bt_send = null;

    private ProgressDialog dialog = null;

    /**
     * 展示图片的适配器
     */
    private CommonRecyclerViewAdapter adapter = null;

    /**
     * 要显示的数据
     */
    private List<ImageInfoEntity> imageFiles = new ArrayList<ImageInfoEntity>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.frag_select_image_for_post_dynamics;
    }

    @Override
    protected void initView() {
        super.initView();

        L.s(TAG, "初始化了");

        //创建适配器
        adapter = new SelectImageFragmentAdapter(context, imageFiles);
        //创建布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv.setLayoutManager(layoutManager);

        ImageSpaceItemDecoration itemDecoration = new ImageSpaceItemDecoration();
        rv.addItemDecoration(itemDecoration);

        rv.setAdapter(adapter);

        LocalImageManager.init(context);

    }

    @Override
    protected void initData() {
        super.initData();

        dialog = ProgressDialogUtil.show(context);

        new Thread() {
            @Override
            public void run() {
                LocalImageInfo localImageInfo = LocalImageManager.queryImage(new LocalImageInfo(new String[]{LocalImageManager.JPEG_MIME_TYPE, LocalImageManager.JPG_MIME_TYPE, LocalImageManager.PNG_MIME_TYPE}));

                List<String> images = localImageInfo.getImageFiles();

                for (int i = 0; i < images.size(); i++) {
                    ImageInfoEntity infoEntity = new ImageInfoEntity();
                    infoEntity.setLocalPath(images.get(i));
                    imageFiles.add(infoEntity);
                }

                notifyLoadDataComplete();

            }
        }.start();

    }

    /**
     * 还原状态
     */
    @Override
    public void onResume() {
        super.onResume();
        reStore();
    }

    @Override
    protected void setOnListener() {
        super.setOnListener();

        adapter.setOnRecyclerViewItemClickListener(new CommonRecyclerViewAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (position < 0) {
                    return;
                }
                L.s(TAG, imageFiles.get(position).getLocalPath());
                boolean b = changeState(position);
                if (b) {
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * 点击事件集中处理
     *
     * @param view
     */
    public void clickView(View view) {
        int id = view.getId();
        switch (id) {

            case R.id.tv_send: //点击了发送按钮
                clickSendBt();
                break;
            case R.id.tv_image: //点击相册文本

                //申请打开图片选择器
                XImageLoader imageLoader = new XImageLoader() {
                    @Override
                    public void load(Context context, String localPath, ImageView iv) {
                        Glide.with(context).load(localPath).into(iv);
                    }
                };

                XSelectAct.open(getActivity(), new XImgSelConfig.Builder(imageLoader)
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

                break;
        }
    }

    @Subscribe
    public void onEventReFresh(RequestReFreshEvent requestReFreshEvent) {
        reStore();
    }


    @Override
    protected void freshUI() {
//        adapter.notifyDataSetChanged();
        if (dialog != null) {
            dialog.dismiss();
        }
        reStore();
    }

    /**
     * 最后一个选中的下标,-1没有表示一个都没有选中
     */
    private int lastSelectPotion = -1;

    /**
     * 选中的个数
     */
    private int selectedCount = 0;

    /**
     * 取消选中的效果
     *
     * @param position
     */
    private boolean changeState(int position) {
        //拿到对象
        ImageInfoEntity infoEntity = imageFiles.get(position);

        boolean b = false;

        if (infoEntity.getSelectedPotion() == -1) { //如果点击的这个是没有选中的状态的
            b = select(position);
        } else { //如果点击的这个是选中的
            b = cancelSelect(position);
        }
        bt_send.setText("发送(" + selectedCount + ")");
        return b;
    }

    //postion是图片在集合中的下标
    private boolean select(int position) {
        if (position == -1) {
            return false;
        }

        //如果现在的图片数量已经是最大的数量
        if (selectedCount >= ImageStore.MAX_IMAGE_SIZE) {
            T.showShort(context, "已经达到最大图片数量");
            return false;
        }

        //拿到对象
        ImageInfoEntity infoEntity = imageFiles.get(position);

        selectedCount++;//总共选中的加1
        infoEntity.setSelectedPotion(selectedCount);

        if (lastSelectPotion != -1) { //表示之前有选中过一个了
            imageFiles.get(lastSelectPotion).setNextPotion(position);
            infoEntity.setPrePosition(lastSelectPotion);
        }

        lastSelectPotion = position;

        return true;

    }

    //postion是图片在集合中的下标
    private boolean cancelSelect(int position) {
        if (position == -1) {
            return false;
        }

        //拿到对象
        ImageInfoEntity infoEntity = imageFiles.get(position);

        selectedCount--;//总共选中的减1
        infoEntity.setSelectedPotion(-1); //取消选中的效果


        //首先拿到这个节点的前后的下标
        int nextPotion = infoEntity.getNextPotion();
        int prePosition = infoEntity.getPrePosition();
        infoEntity.setNextPotion(-1);
        infoEntity.setPrePosition(-1);

        if (prePosition != -1) {//如果点击的这个前面还有其他的,就让上一个的和下一个衔接一下
            imageFiles.get(prePosition).setNextPotion(nextPotion);
            lastSelectPotion = prePosition;
        }

        if (nextPotion != -1) { //如果点击的这个后面还有其他的,就让下一个和上一个也衔接一下
            imageFiles.get(nextPotion).setPrePosition(prePosition);
            while (nextPotion != -1) {
                lastSelectPotion = nextPotion;
                infoEntity = imageFiles.get(nextPotion);
                infoEntity.setSelectedPotion(infoEntity.getSelectedPotion() - 1);
                nextPotion = infoEntity.getNextPotion();
            }

        }

        if (selectedCount == 0) {
            lastSelectPotion = -1;
        }

        return true;

    }

    /**
     * 还原所有状态
     */
    public void reStore() {
        lastSelectPotion = -1;
        selectedCount = 0;
        int size = imageFiles.size();
        for (int i = 0; i < size; i++) {
            ImageInfoEntity infoEntity = imageFiles.get(i);
            infoEntity.reStore();
        }

        List<String> images = ImageStore.getInstance().getImages();

        //拿到被选中的图片的个数
        selectedCount = images.size();
        bt_send.setText("发送(" + selectedCount + ")");
        for (int i = 0; i < selectedCount; i++) {
            //被选中的图片路径
            String path = images.get(i);
            for (int j = 0; j < size; j++) {
                ImageInfoEntity infoEntity = imageFiles.get(j);
                if (infoEntity.getLocalPath().equals(path)) {
                    if (lastSelectPotion != -1) { //说明不是第一个
                        //设置当前这个的指向上一个被选中的
                        infoEntity.setPrePosition(lastSelectPotion);
                        //设置上一个被选中的指向这个被选中的
                        imageFiles.get(lastSelectPotion).setNextPotion(j);
                    }
                    lastSelectPotion = j;
                    infoEntity.setSelectedPotion(i + 1);
                    break;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * 点击发送按钮的逻辑
     */
    private void clickSendBt() {
        List<String> images = ImageStore.getInstance().getImages();
        images.clear();

        int p = lastSelectPotion;

        while (p != -1) {
            ImageInfoEntity infoEntity = imageFiles.get(p);
            images.add(0,infoEntity.getLocalPath());
            p = infoEntity.getPrePosition();
        }

        int size = imageFiles.size();
        for (int i = 0; i < size; i++) {
            ImageInfoEntity infoEntity = imageFiles.get(i);
                infoEntity.reStore();
        }

        //还原选中的状态
        lastSelectPotion = -1;
        selectedCount = 0;
        bt_send.setText("发送(0)");

        PostDynamicsAct postDynamicsAct = (PostDynamicsAct) getActivity();
        postDynamicsAct.displayImages();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
