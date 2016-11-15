package xiaojinzi.base.android.adapter.viewPager;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by cxj on 2016/7/13.
 * ViewPager+View的适配器
 */
public class ViewPagerViewAdapter extends PagerAdapter {

    /**
     * 要显示的集合的集合
     */
    private List<View> viewLists;

    private List<String> titles;

    public void setTitles(List<String> titles) {
        this.titles = titles;
    }

    public ViewPagerViewAdapter(List<View> lists) {
        viewLists = lists;
    }

    @Override
    public int getCount() {                                                   //获得size
        return viewLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {           //销毁一个条目
        ((ViewPager) container).removeView(viewLists.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {      //实例化Item
        ((ViewPager) container).addView(viewLists.get(position), 0);
        return viewLists.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (titles != null) {
            if (position != -1 && position < titles.size()) {
                return titles.get(position);
            }
        }
        return super.getPageTitle(position);
    }
}
