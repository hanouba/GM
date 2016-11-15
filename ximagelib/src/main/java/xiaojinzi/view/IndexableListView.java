package xiaojinzi.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 实现思路：
 * 1.这个View不参与测量的工作,所以包裹和填充的效果是一致的
 * 2.在给定的高度,计算每个字母可以占用的高,然后循环绘制
 * <p/>
 * 字母栏引索,用于显示26个字母
 * 支持配置一些显示的颜色
 * 支持触摸的时候字母的回掉
 * 支持设置选中某一个字母
 */
public class IndexableListView extends View {

    /**
     * 默认构造函数
     *
     * @param context
     */
    public IndexableListView(Context context) {
        super(context);
    }

    public IndexableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public IndexableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * 画笔
     */
    private Paint paint = null;

    /**
     * 初始化块,初始化画笔
     */ {
        paint = new Paint();
        paint.setColor(Color.parseColor("#696969"));
        //设置画笔一个最小的大小,后续会根据高度调整
        paint.setTextSize(12);
    }

    /**
     * 表示没有进行计算过当前每个字母的高度可以用多大的字体
     */
    private boolean flag = false;

    /**
     * 要显示的字母
     */
    public static final String[] letters = {"#", "A", "B", "C", "D",
            "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
            "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    //view视图的高和宽
    private float myWidth = 0;
    private float myHeight = 0;

    /**
     * 当前要特别显示的字母
     * -1表示没有要特别显示的字幕
     */
    private int index = -1;

    /**
     * 被选中的字母
     */
    private int selectIndex = 0;


    /**
     * 测量的方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //拿到宽和高的大小和计算模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        //计算出每一个字母可以占用的高度
        eachHeigth = heightSize / letters.length;
        myHeight = heightSize;
        myWidth = widthSize;
        paint.setTextSize(12); //还原为最小的字体,然后去计算最合适的大小
        //调整为最大的画笔大小
        getMaxTextSize(paint);
        paint.setTextSize(paint.getTextSize() / 2);

        System.out.println("paint.size" + paint.getTextSize());

        letterHegith = getHeightOfLetter(paint);

        setMeasuredDimension((int) eachHeigth, heightSize);
    }

    /**
     * 每个字母的高度
     */
    private float eachHeigth = 0;

    /**
     * 字幕的高度
     */
    private int letterHegith;

    /**
     * 获取选中的字符
     *
     * @return
     */
    public char getSelectChar() {
        return letters[selectIndex].toUpperCase().charAt(0);
    }

    /**
     * 设置某一个字母被选中
     *
     * @param c
     */
    public void setSelectLetter(char c) {
        //先转化成大写的
        String tmp = ("" + c).toUpperCase();
        for (int i = 0; i < letters.length; i++) {
            if (letters[i].equals(tmp)) {
                selectIndex = i;
                this.postInvalidate();
                return;
            }
        }
    }


    /**
     * 获取指定画笔绘制文本的高度
     *
     * @param p 画笔对象
     * @return
     */
    private int getHeightOfLetter(Paint p) {
        Rect rect = new Rect();
        p.getTextBounds("M", 0, 1, rect);
        return rect.height();
    }


    /**
     * 获取最大显示的字体大小
     *
     * @param paint 画笔
     */
    private void getMaxTextSize(Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds("M", 0, 1, rect);
        int rect_height = rect.height();
        int rect_width = rect.width();
        while (rect_height < eachHeigth && rect_width < myWidth) {
            float v = paint.getTextSize() + 1;
            paint.setTextSize(v);
            paint.getTextBounds("M", 0, 1, rect);
            rect_height = rect.height();
            rect_width = rect.width();
        }
        float v = paint.getTextSize();
        paint.setTextSize(v);
    }


    @Override
    protected void onDraw(Canvas c) {
        super.onDraw(c);

        //获取这个view的宽和高
        myWidth = getWidth();
        myHeight = getHeight();

        float tmpX = 0;
        float tmpY = 0;

        tmpY -= ((eachHeigth - letterHegith) / 2);

        for (int i = 0; i < letters.length; i++) {
            tmpX = (myWidth - paint.measureText(letters[i])) / 2;
            tmpY += eachHeigth;
            if (i == selectIndex) {
                if (isTouch) {
                    paint.setColor(down_selectLetterColor);
                } else {
                    paint.setColor(up_selectLetterColor);
                }
            } else {
                if (isTouch) {
                    paint.setColor(down_unSelectLetterColor);
                } else {
                    paint.setColor(up_unSelectLetterColor);
                }
            }
            c.drawText(letters[i], tmpX, tmpY, paint);
        }


    }

    /**
     * 手指抬起的时候被选中的字母的颜色
     */
    private int up_selectLetterColor = Color.RED;

    /**
     * 手指抬起的时候没被选中的字母的颜色
     */
    private int up_unSelectLetterColor = Color.parseColor("#696969");

    /**
     * 手指按下的时候被选中的字母的颜色
     */
    private int down_selectLetterColor = Color.parseColor("#696969");

    /**
     * 手指按下的时候没被选中的字母的颜色
     */
    private int down_unSelectLetterColor = Color.WHITE;


    /**
     * 手指按下的时候的背景颜色
     */
    private Drawable downDrawable = new ColorDrawable(Color.GRAY);

    /**
     * 手指抬起的时候的背景颜色
     */
    private Drawable upDrawable = new ColorDrawable(Color.parseColor("#00FFFFFF"));

    /**
     * 手指是否触摸到屏幕
     */
    private boolean isTouch = false;

    /**
     * 处理触摸事件的,在手指按下的时候背景要改变
     *
     * @param e
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent e) {

        int action = e.getAction();
        switch (action) {

            case MotionEvent.ACTION_DOWN: //如果是按下的情况
                isTouch = true;
                setBackground(downDrawable);
                this.postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE: //如果是移动的情况:

                float y = e.getY();
                int selectIndex = 0;
                if (y < 0) {
                    selectIndex = 0;
                } else if (y > myHeight) {
                    selectIndex = letters.length - 1;
                } else {
                    selectIndex = (int) (y / eachHeigth);
                    if (selectIndex >= letters.length) {
                        selectIndex = letters.length - 1;
                    }
                }
                this.postInvalidate();

                if (onLetterChange != null && this.selectIndex != selectIndex) {
                    onLetterChange.letterChange(letters[selectIndex]);
                }

                this.selectIndex = selectIndex;

                break;
            case MotionEvent.ACTION_UP: //如果是抬起的时候
                isTouch = false;
                setBackground(upDrawable);
                this.postInvalidate();
                break;
        }


        return true;
    }

    /**
     * 字幕变化的回掉接口
     */
    private OnLetterChange onLetterChange = null;

    /**
     * 设置回掉监听
     *
     * @param onLetterChange
     */
    public void setOnLetterChange(OnLetterChange onLetterChange) {
        this.onLetterChange = onLetterChange;
    }

    /**
     * 回掉的接口
     */
    public interface OnLetterChange {
        public void letterChange(String c);
    }

    public int getUp_selectLetterColor() {
        return up_selectLetterColor;
    }

    public void setUp_selectLetterColor(int up_selectLetterColor) {
        this.up_selectLetterColor = up_selectLetterColor;
    }

    public int getUp_unSelectLetterColor() {
        return up_unSelectLetterColor;
    }

    public void setUp_unSelectLetterColor(int up_unSelectLetterColor) {
        this.up_unSelectLetterColor = up_unSelectLetterColor;
    }

    public int getDown_selectLetterColor() {
        return down_selectLetterColor;
    }

    public void setDown_selectLetterColor(int down_selectLetterColor) {
        this.down_selectLetterColor = down_selectLetterColor;
    }

    public int getDown_unSelectLetterColor() {
        return down_unSelectLetterColor;
    }

    public void setDown_unSelectLetterColor(int down_unSelectLetterColor) {
        this.down_unSelectLetterColor = down_unSelectLetterColor;
    }

    public Drawable getDownDrawable() {
        return downDrawable;
    }

    public void setDownDrawable(Drawable downDrawable) {
        this.downDrawable = downDrawable;
    }

    public Drawable getUpDrawable() {
        return upDrawable;
    }

    public void setUpDrawable(Drawable upDrawable) {
        this.upDrawable = upDrawable;
    }
}
