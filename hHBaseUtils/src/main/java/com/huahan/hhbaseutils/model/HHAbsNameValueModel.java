package com.huahan.hhbaseutils.model;

import java.util.ArrayList;

import com.huahan.hhbaseutils.imp.HHNameValueList;

public abstract class HHAbsNameValueModel implements HHNameValueList
{
	/**
	 * 保存键值对信息
	 */
	protected ArrayList<HHBasicNameValuePair> nameValueListIgnore;
}

