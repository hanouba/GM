package xiaojinzi.activity.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import xiaojinzi.annotation.ViewInjectionUtil;
import xiaojinzi.base.android.os.ProgressDialogUtil;
import xiaojinzi.base.android.os.T;

/**
 * Created by cxj on 2016/7/11.
 */
public abstract class BaseFragment extends Fragment {

    public static String TAG = null;

    protected Handler h = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            freshUI();
        }
    };

    /**
     * 上下文
     */
    protected Context context = null;

    /**
     * fragment对应的View
     */
    protected View contentView;

    protected ProgressDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        TAG = this.getClass().getSimpleName();

        dialog = ProgressDialogUtil.create(getActivity(), ProgressDialog.STYLE_SPINNER, false);

        if (contentView == null) {
            //初始化上下文
            context = getContext();

            contentView = null;
            contentView = inflater.inflate(getLayoutId(), null);

            //让注解生效
            ViewInjectionUtil.injectView(this, contentView);

            initView();

            initData();

            setOnListener();
        } else {
            if (isLoadData) {
                freshUI();
            }
        }

        return contentView;

    }

    /**
     * fragment所使用的布局文件的id
     *
     * @return
     */
    public abstract int getLayoutId();


    /**
     * 初始化控件
     */
    protected void initView() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * 设置监听
     */
    protected void setOnListener() {
    }

    /**
     * 刷新ui
     */
    protected void freshUI() {
    }

    private boolean isLoadData;

    /**
     * 通知数据加载完毕
     */
    protected void notifyLoadDataComplete() {
        isLoadData = true;
        h.sendEmptyMessage(0);
    }

    public void showDialog(String content) {
        dialog.setMessage(content);
        dialog.show();
    }

    public void closeDialog() {
        dialog.dismiss();
    }

    public void tip(String content) {
        T.showShort(context,content);
    }

}
