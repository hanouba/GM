package com.xiaojinzi.ximagelib.imagePresenter;


import com.xiaojinzi.ximagelib.XImage;
import com.xiaojinzi.ximagelib.imageModel.IQueryImageModel;
import com.xiaojinzi.ximagelib.imageModel.QueryImageModel;
import com.xiaojinzi.ximagelib.imageView.IQueryImageView;
import com.xiaojinzi.ximagelib.imageView.popup.bean.FolderBean;
import com.xiaojinzi.ximagelib.listener.IQueryImageListener;

import java.util.List;

/**
 * Created by cxj on 2016/9/27.
 */
public class IQueryImagePresenter implements IQueryImageListener {

    private IQueryImageView view;

    private IQueryImageModel model = new QueryImageModel();

    public IQueryImagePresenter(IQueryImageView view) {
        this.view = view;

    }

    public void getAllImages() {
        view.showDialog("请稍后");
        model.getAllImages(view.getContext(), XImage.getConfig().selectImage, this);

    }

    public void getAllImagesInFolder(String folder) {
        view.showDialog("请稍后");
        model.getImageInFolder(folder, this);
    }

    public void getFolderBeanList() {
        view.showDialog("请稍后");
        model.getFolderBeanList(this);
    }


    @Override
    public void disPlayAllImage(List<String> images) {
        view.closeDialog();
        view.disPlayAllImage(images);
    }

    @Override
    public void disPlayAllFolder(List<String> folders) {
    }

    @Override
    public void disPlayImageInFolder(List<String> images) {
        view.disPlayAllImageInFolder(images);
    }

    @Override
    public void disFolderBeanList(List<FolderBean> folderBeanList) {
        view.disPlayFolderBeanList(folderBeanList);
    }

    /**
     * 释放资源
     */
    public void release() {
        model.release();
    }

}
