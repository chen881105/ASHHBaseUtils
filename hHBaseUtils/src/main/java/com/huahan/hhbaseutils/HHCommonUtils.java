package com.huahan.hhbaseutils;

import java.net.URLDecoder;

import android.app.Activity;
import android.app.Application;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.text.TextUtils;
import android.view.View;

import com.huahan.hhbaseutils.ui.HHApplication;

public class HHCommonUtils
{
	/**O
	 * 对字符串进行url编码，使用的是utf-8de字符集
	 * @param decode
	 * @return
	 */
	public static String urlDecode(String decode)
	{
		if (!TextUtils.isEmpty(decode))
		{
			try
			{
				decode=URLDecoder.decode(decode, "utf-8");
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return decode;
	}
	/**
	 * 渲染一个View的背景图片为app的主色调
	 * @param activity		
	 * @param view			需要渲染的View
	 * @return				true：已经渲染了View的背景
	 */
	@SuppressWarnings("deprecation")
	public static boolean tintViewBackground(Activity activity,View view)
	{
		Application application = activity.getApplication();
		if(application instanceof HHApplication)
		{
			int[][] colorState=new int[1][];
			colorState[0]=new int[]{};
			int[] color=new int[1];
			color[0]=((HHApplication)application).getHHApplicationInfo().getMainColor();
			ColorStateList colorStateList=new ColorStateList(colorState, color);
			Drawable drawable = view.getBackground();
			if (drawable!=null)
			{
				
				DrawableCompat.setTintList(drawable, colorStateList);
				DrawableCompat.setTintMode(drawable, Mode.SRC_IN);
				view.setBackgroundDrawable(drawable);
				return true;
			}
			
		}
		return false;
	}
}
