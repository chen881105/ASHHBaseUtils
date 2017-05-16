package com.huahan.hhbaseutils.model;

import java.lang.ref.WeakReference;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import com.huahan.hhbaseutils.task.HHImageLoadTask;

public class HHBitmapDrawable extends BitmapDrawable
{
	
	private WeakReference<HHImageLoadTask> mReference;
	public HHBitmapDrawable(Resources resources,Bitmap holderBitmap,HHImageLoadTask task)
	{
		super(resources,holderBitmap);
		this.mReference=new WeakReference<HHImageLoadTask>(task);
	}
	public HHImageLoadTask getImageLoadTask()
	{
		return mReference.get();
	}
}
