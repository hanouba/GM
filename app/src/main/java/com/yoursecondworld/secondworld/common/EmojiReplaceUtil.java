package com.yoursecondworld.secondworld.common;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ImageSpan;

import com.yoursecondworld.secondworld.R;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xiaojinzi.autolayout.utils.AutoUtils;

/**
 * Created by cxj on 2016/9/4.
 * android下面的emoji表情替换的工具类
 */
public class EmojiReplaceUtil {

    /**
     * 表情的unicode码为key,对应的资源文件中的表情地址为Value
     */
    public static Map<String, Integer> textToEmojiMapper = new HashMap<String, Integer>();

//    private static int emojiSize = AutoUtils.getPercentHeightSize(24);

    /**
     * 资源文件中的表情地址为key,对应的表情的unicode码为Value
     */
    public static Map<Integer, String> emojiToTextMapper = new HashMap<Integer, String>();

    static {
        textToEmojiMapper.put("\\uD83D\\uDE12", R.mipmap.emoji_1);
        textToEmojiMapper.put("\\uD83D\\uDE29", R.mipmap.emoji_2);
        textToEmojiMapper.put("\\uD83D\\uDE2A", R.mipmap.emoji_3);
        textToEmojiMapper.put("\\uD83D\\uDE2B", R.mipmap.emoji_4);
        textToEmojiMapper.put("\\uD83D\\uDE13", R.mipmap.emoji_5);
        textToEmojiMapper.put("\\uD83D\\uDE34", R.mipmap.emoji_6);
        textToEmojiMapper.put("\\uD83D\\uDE2D", R.mipmap.emoji_7);
        textToEmojiMapper.put("\\uD83D\\uDE2E", R.mipmap.emoji_8);
        textToEmojiMapper.put("\\uD83D\\uDE32", R.mipmap.emoji_9);
        textToEmojiMapper.put("\\uD83D\\uDE30", R.mipmap.emoji_10);
        textToEmojiMapper.put("\\uD83D\\uDE31", R.mipmap.emoji_11);
        textToEmojiMapper.put("\\uD83D\\uDE1E", R.mipmap.emoji_12);
        textToEmojiMapper.put("\\uD83D\\uDE36", R.mipmap.emoji_13);
        textToEmojiMapper.put("\\uD83D\\uDE33", R.mipmap.emoji_14);
        textToEmojiMapper.put("\\u263a", R.mipmap.emoji_15);
        textToEmojiMapper.put("\\uD83D\\uDE37", R.mipmap.emoji_16);
        textToEmojiMapper.put("\\uD83D\\uDE14", R.mipmap.emoji_17);
        textToEmojiMapper.put("\\uD83D\\uDE35", R.mipmap.emoji_18);
        textToEmojiMapper.put("\\uD83D\\uDE03", R.mipmap.emoji_19);
        textToEmojiMapper.put("\\uD83D\\uDE02", R.mipmap.emoji_20);
        textToEmojiMapper.put("\\uD83D\\uDE00", R.mipmap.emoji_21);
        textToEmojiMapper.put("\\uD83D\\uDE01", R.mipmap.emoji_22);
        textToEmojiMapper.put("\\uD83D\\uDE1F", R.mipmap.emoji_23);
        textToEmojiMapper.put("\\uD83D\\uDE06", R.mipmap.emoji_24);
        textToEmojiMapper.put("\\uD83D\\uDE09", R.mipmap.emoji_25);
        textToEmojiMapper.put("\\uD83D\\uDE05", R.mipmap.emoji_26);
        textToEmojiMapper.put("\\uD83D\\uDE0A", R.mipmap.emoji_27);
        textToEmojiMapper.put("\\uD83D\\uDE07", R.mipmap.emoji_28);
        textToEmojiMapper.put("\\uD83D\\uDE15", R.mipmap.emoji_29);
        textToEmojiMapper.put("\\uD83D\\uDE1A", R.mipmap.emoji_30);
        textToEmojiMapper.put("\\uD83D\\uDE0D", R.mipmap.emoji_31);
        textToEmojiMapper.put("\\uD83D\\uDE0B", R.mipmap.emoji_32);
        textToEmojiMapper.put("\\uD83D\\uDE0C", R.mipmap.emoji_33);
        textToEmojiMapper.put("\\uD83D\\uDE21", R.mipmap.emoji_34);
        textToEmojiMapper.put("\\uD83D\\uDE18", R.mipmap.emoji_35);
        textToEmojiMapper.put("\\uD83D\\uDE0E", R.mipmap.emoji_36);
        textToEmojiMapper.put("\\uD83D\\uDE0F", R.mipmap.emoji_37);
        textToEmojiMapper.put("\\uD83D\\uDE16", R.mipmap.emoji_38);
        textToEmojiMapper.put("\\uD83D\\uDE23", R.mipmap.emoji_39);
        textToEmojiMapper.put("\\uD83D\\uDE1C", R.mipmap.emoji_40);
        textToEmojiMapper.put("\\uD83D\\uDE1D", R.mipmap.emoji_41);
        textToEmojiMapper.put("\\uD83D\\uDE2F", R.mipmap.emoji_42);
        textToEmojiMapper.put("\\uD83D\\uDE22", R.mipmap.emoji_43);
        textToEmojiMapper.put("\\uD83D\\uDE24", R.mipmap.emoji_45);
        textToEmojiMapper.put("\\uD83D\\uDE28", R.mipmap.emoji_46);

//        textToEmojiMapper.put("\\uE2\\u98\\u9D", R.mipmap.emoji_47);
//        textToEmojiMapper.put("\\u270b\\u270c", R.mipmap.emoji_48);
//        textToEmojiMapper.put("\\270B", R.mipmap.emoji_49);
//        textToEmojiMapper.put("\\ud83d\\udc4c", R.mipmap.emoji_50);
//        textToEmojiMapper.put("\\ud83d\\udc4a", R.mipmap.emoji_51);
//        textToEmojiMapper.put("\\ud83d\\udc4c", R.mipmap.emoji_52);
//        textToEmojiMapper.put("\\ud83d\\udc4d", R.mipmap.emoji_53);
//        textToEmojiMapper.put("\\ud83d\\udc4e", R.mipmap.emoji_54);

//        textToEmojiMapper.put("\\ud83d\\udc37", R.mipmap.emoji_61);

//        textToEmojiMapper.put("\\ud83d\\udca9", R.mipmap.emoji_87);
//        textToEmojiMapper.put("\\ud83d\\udca4", R.mipmap.emoji_88);

        //=============================================================================================================================

        emojiToTextMapper.put(R.mipmap.emoji_1, "\uD83D\uDE12");
        emojiToTextMapper.put(R.mipmap.emoji_2, "\uD83D\uDE29");
        emojiToTextMapper.put(R.mipmap.emoji_3, "\uD83D\uDE2A");
        emojiToTextMapper.put(R.mipmap.emoji_4, "\uD83D\uDE2B");
        emojiToTextMapper.put(R.mipmap.emoji_5, "\uD83D\uDE13");
        emojiToTextMapper.put(R.mipmap.emoji_6, "\uD83D\uDE34");
        emojiToTextMapper.put(R.mipmap.emoji_7, "\uD83D\uDE2D");
        emojiToTextMapper.put(R.mipmap.emoji_8, "\uD83D\uDE2E");
        emojiToTextMapper.put(R.mipmap.emoji_9, "\uD83D\uDE32");
        emojiToTextMapper.put(R.mipmap.emoji_10, "\uD83D\uDE30");
        emojiToTextMapper.put(R.mipmap.emoji_11, "\uD83D\uDE31");
        emojiToTextMapper.put(R.mipmap.emoji_12, "\uD83D\uDE1E");
        emojiToTextMapper.put(R.mipmap.emoji_13, "\uD83D\uDE36");
        emojiToTextMapper.put(R.mipmap.emoji_14, "\uD83D\uDE33");
        emojiToTextMapper.put(R.mipmap.emoji_15, "\u263a");
        emojiToTextMapper.put(R.mipmap.emoji_16, "\uD83D\uDE37");
        emojiToTextMapper.put(R.mipmap.emoji_17, "\uD83D\uDE14");
        emojiToTextMapper.put(R.mipmap.emoji_18, "\uD83D\uDE35");
        emojiToTextMapper.put(R.mipmap.emoji_19, "\uD83D\uDE03");
        emojiToTextMapper.put(R.mipmap.emoji_20, "\uD83D\uDE02");
        emojiToTextMapper.put(R.mipmap.emoji_21, "\uD83D\uDE00");
        emojiToTextMapper.put(R.mipmap.emoji_22, "\uD83D\uDE01");
        emojiToTextMapper.put(R.mipmap.emoji_23, "\uD83D\uDE1F");
        emojiToTextMapper.put(R.mipmap.emoji_24, "\uD83D\uDE06");
        emojiToTextMapper.put(R.mipmap.emoji_25, "\uD83D\uDE09");
        emojiToTextMapper.put(R.mipmap.emoji_26, "\uD83D\uDE05");
        emojiToTextMapper.put(R.mipmap.emoji_27, "\uD83D\uDE0A");
        emojiToTextMapper.put(R.mipmap.emoji_28, "\uD83D\uDE07");
        emojiToTextMapper.put(R.mipmap.emoji_29, "\uD83D\uDE15");
        emojiToTextMapper.put(R.mipmap.emoji_30, "\uD83D\uDE1A");
        emojiToTextMapper.put(R.mipmap.emoji_31, "\uD83D\uDE0D");
        emojiToTextMapper.put(R.mipmap.emoji_32, "\uD83D\uDE0B");
        emojiToTextMapper.put(R.mipmap.emoji_33, "\uD83D\uDE0C");
        emojiToTextMapper.put(R.mipmap.emoji_34, "\uD83D\uDE21");
        emojiToTextMapper.put(R.mipmap.emoji_35, "\uD83D\uDE18");
        emojiToTextMapper.put(R.mipmap.emoji_36, "\uD83D\uDE0E");
        emojiToTextMapper.put(R.mipmap.emoji_37, "\uD83D\uDE0F");
        emojiToTextMapper.put(R.mipmap.emoji_38, "\uD83D\uDE16");
        emojiToTextMapper.put(R.mipmap.emoji_39, "\uD83D\uDE23");
        emojiToTextMapper.put(R.mipmap.emoji_40, "\uD83D\uDE1C");
        emojiToTextMapper.put(R.mipmap.emoji_41, "\uD83D\uDE1D");
        emojiToTextMapper.put(R.mipmap.emoji_42, "\uD83D\uDE2F");
        emojiToTextMapper.put(R.mipmap.emoji_43, "\uD83D\uDE22");
        emojiToTextMapper.put(R.mipmap.emoji_45, "\uD83D\uDE24");
        emojiToTextMapper.put(R.mipmap.emoji_46, "\uD83D\uDE28");
        emojiToTextMapper.put(R.mipmap.emoji_47, "OxE2Ox98Ox9D");
//        emojiToTextMapper.put(R.mipmap.emoji_48, "\u270b\u270c");
//        emojiToTextMapper.put(R.mipmap.emoji_49, "\270B");
//        emojiToTextMapper.put(R.mipmap.emoji_50, "\ud83d\udc4c");
//        emojiToTextMapper.put(R.mipmap.emoji_51, "\ud83d\udc4a");
//        emojiToTextMapper.put(R.mipmap.emoji_52, "\ud83d\udc4c");
//        emojiToTextMapper.put(R.mipmap.emoji_53, "\ud83d\udc4d");
//        emojiToTextMapper.put(R.mipmap.emoji_54, "\ud83d\udc4e");
//        emojiToTextMapper.put(R.mipmap.emoji_61, "\ud83d\udc37");
//        emojiToTextMapper.put(R.mipmap.emoji_87, "\ud83d\udca9");
//        emojiToTextMapper.put(R.mipmap.emoji_88, "\ud83d\udca4");

    }

    public static SpannableString converse(Context context, String text) {

        if (TextUtils.isEmpty(text)) {
            return null;
        }

//        text = text + " ";

        SpannableString spanString = new SpannableString(text);

        Set<Map.Entry<String, Integer>> entrySet = textToEmojiMapper.entrySet();
        Iterator<Map.Entry<String, Integer>> it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = it.next();
            String key = entry.getKey();
            int resId = entry.getValue();

            Pattern pattern = Pattern.compile(key);

            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                Drawable d = context.getResources().getDrawable(resId);
                d.setBounds(10, 10, d.getIntrinsicWidth(), d.getIntrinsicHeight());
//                d.setBounds(0, 0, emojiSize, emojiSize);
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                spanString.setSpan(span, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        return spanString;
    }
}



















