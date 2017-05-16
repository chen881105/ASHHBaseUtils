package com.huahan.hhbaseutils.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huahan.hhbaseutils.imp.HHWindowTypeListImp;

public class HHWindowTypeAdapter extends HHBaseAdapter<HHWindowTypeListImp>
{

	public HHWindowTypeAdapter(Context context, List<HHWindowTypeListImp> list)
	{
		super(context, list);
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		return null;
	}
	private class ViewHolder
	{
		TextView nameTextView;
	}

}
