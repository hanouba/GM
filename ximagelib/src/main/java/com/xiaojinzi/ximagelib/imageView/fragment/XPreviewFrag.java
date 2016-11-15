package com.xiaojinzi.ximagelib.imageView.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiaojinzi.ximagelib.R;
import com.xiaojinzi.ximagelib.XImage;
import com.xiaojinzi.ximagelib.imageView.XPreviewAct;
import com.xiaojinzi.ximagelib.widget.TouchImageView;

/**
 * Created by cxj on 2016/10/9.
 * 显示一个图片的fragment
 */
public class XPreviewFrag extends Fragment {

    private TouchImageView iv;

    private String localPath;

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.frag_xpreview, null);

        iv = (TouchImageView) v.findViewById(R.id.iv);

        iv.setOnClickListener((View.OnClickListener) getActivity());

        XImage.getConfig().loader.load(getActivity(), localPath, iv);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XPreviewAct act = (XPreviewAct) getActivity();
                act.toggle();
            }
        });

        return v;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        iv = null;
    }
}
