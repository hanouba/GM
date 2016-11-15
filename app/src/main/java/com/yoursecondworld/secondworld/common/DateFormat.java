package com.yoursecondworld.secondworld.common;

import java.util.Date;

import xiaojinzi.base.java.util.DateUtil;

/**
 * 
 * @author cxj
 *
 */
public class DateFormat {

	/**
	 * 格式化时间
	 * 
	 * @param currentTime
	 *            要格式化的时间的毫秒值ֵ
	 * @return
	 */
	public static String format(long formatTime) {

		// 系统当前的毫秒值
		long currentTimeMillis = System.currentTimeMillis();
		// 计算出相差的毫秒值
		long millisecond = currentTimeMillis - formatTime;

		if (millisecond > 0) {
			if (millisecond < DateUtil.HOURTIMEMILLIS) { // 如果小于一个小时
				// 获取到分钟数
				long minutes = DateUtil.millisecondToMinutes(millisecond);
				return minutes + "分钟前";
			} else if (millisecond < DateUtil.DAYTIMEMILLIS) { // 如果小于一天
				// 获取到分钟数
				long hours = DateUtil.millisecondToHours(millisecond);
				return hours + "小时前";
			} else {
				// 把要转化的时间转化成日期对象
				Date formatDate = new Date(formatTime);
				// 拿到今天的凌晨的毫秒值
				long dayStartMillisecond = DateUtil.getDayStartMillisecond();
				// 得到要格式化的时间到今天凌晨的毫秒值的差值
				millisecond = dayStartMillisecond - formatTime;
				if (millisecond > DateUtil.DAYTIMEMILLIS) { // 如果是大于一天的,那么就是昨天以前的了
					return DateUtil.getMonth(formatDate) + //
							"-" + //
							DateUtil.getDay(formatDate);
				} else {
					return "昨天" + DateUtil.getHour(formatDate) + //
							":" + //
							DateUtil.getMinute(formatDate);
				}
			}
		}

		return "刚刚";
	}

}
