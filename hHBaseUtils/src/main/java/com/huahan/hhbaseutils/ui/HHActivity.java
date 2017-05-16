package com.huahan.hhbaseutils.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.huahan.hhbaseutils.HHActivityUtils;
import com.huahan.hhbaseutils.HHPermissionUtils;
import com.huahan.hhbaseutils.HHViewHelper;
import com.huahan.hhbaseutils.R;
import com.huahan.hhbaseutils.imp.HHPageBaseOper;
import com.huahan.hhbaseutils.imp.HHTopViewManagerImp;
import com.huahan.hhbaseutils.manager.HHDefaultTopViewManager;
import com.huahan.hhbaseutils.manager.HHUiTopManager;
import com.huahan.hhbaseutils.manager.HHUiTopManager.TopMode;
import com.huahan.hhbaseutils.model.HHWeakHandler;

public abstract class HHActivity extends AppCompatActivity implements HHPageBaseOper
{

	/**
	 * 请求所有权限
	 */
	public static final int CODE_REQUIRE_ALL_PERMISSION=1;
	/**
	 * 强制请求所有的权限，如果有一个没有通过，就一直申请
	 */
	public static final int CODE_FORCE_REQUIRE_ALL_PERMISSION=2;
	//定义的是整个页面的根布局
	private RelativeLayout mParentLayout;
	//定义的是头部和底部
	private LinearLayout mTopLayout,mBottomLayout;
	//定义的是中间显示的内容
	private FrameLayout mContainerLayout;
	//整个页面显示的视图
	private View mBaseView;
	//保存当前页面的上下文对象
	private Context mContext;
	//ui头部的管理类
	private HHUiTopManager mTopManager;
	private Bundle mSavedInstanceBundle;
	/**
	 * 表示的是当前的页面是否执行了onDestory方法
	 */
	private boolean mIsDestory=false;
	@Override
	protected void onCreate(Bundle savedInstanceBundle)
	{
		super.onCreate(savedInstanceBundle);
		//切换语言
//		Locale appLanguage = HHSystemUtils.getAppLanguage(this);
//		HHSystemUtils.changeLanguage(this, appLanguage);
		mSavedInstanceBundle=savedInstanceBundle;
		mContext=this;
		HHActivityUtils.getInstance().addActivity(this);
		setContentView(R.layout.hh_activity_main);
		initBaseInfo();
		mTopManager=new HHUiTopManager(this);
		initTopLayout();
		initOther();
		
	}
	/**
	 * 获取Activity保存的bundle信息
	 * @return
	 */
	protected Bundle getSavedInstanceBundle()
	{
		return mSavedInstanceBundle;
	}
	protected void initTopLayout()
	{
		mTopManager.showTopView(TopMode.DEFAULT);
	}
	/**
	 * 在onCreate中调用，用于初始化整个页面的基本结构<br/>
	 */
	protected void initOther()
	{
		
	}
	private void initBaseInfo()
	{
		//获取基本架构的基本控件
		mParentLayout=HHViewHelper.getViewByID(this, R.id.hh_rl_base_parent);
		mBottomLayout=HHViewHelper.getViewByID(this, R.id.hh_ll_base_bottom);
		mTopLayout=HHViewHelper.getViewByID(this, R.id.hh_ll_base_top);
		mContainerLayout=HHViewHelper.getViewByID(this, R.id.hh_fl_base_container);
	}
	/**
	 * 返回头部管理器
	 * @return
	 */
	public final HHUiTopManager getTopManager()
	{
		return mTopManager;
	}
	/**
	 * 表示当前的页面是否执行了ondestory方法
	 * @return
	 */
	protected boolean isActivityDestory()
	{
		return mIsDestory;
	}
	@Override
	protected void onDestroy()
	{
		HHActivityUtils.getInstance().removeActivity(this);
		super.onDestroy();
		mIsDestory=true;
	}
	/**
	 * 把创建的View添加到显示内容的中间容器.<br/>
	 * @param index				插入的位置
	 * @param view				插入的视图
	 */
	protected void addViewToContainer(int index,View view)
	{
		mContainerLayout.addView(view, index,new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
	}
	/**
	 * 返回当前页面的跟布局
	 * @return
	 */
	protected RelativeLayout getBaseParentLayout()
	{
		return mParentLayout;
	}
	@TargetApi(23) 
	@Override
	public void onRequestPermissionsResult(int arg0, @NonNull String[] arg1, @NonNull int[] arg2)
	{
		switch (arg0)
		{
		case CODE_REQUIRE_ALL_PERMISSION:
			
			break;
		case CODE_FORCE_REQUIRE_ALL_PERMISSION:
			HHPermissionUtils.forceRequireAllPermission(this);
			break;
		default:
			super.onRequestPermissionsResult(arg0, arg1, arg2);
			break;
		}
		
	}
	/**
	 * 获取当前页面显示的上边的布局
	 * @return
	 */
	public LinearLayout getBaseTopLayout()
	{
		return mTopLayout;
	}
	/**
	 * 获取当前页面显示的下边的布局
	 * @return
	 */
	protected LinearLayout getBaseBottomLayout()
	{
		return mBottomLayout;
	}
	@Override
	public void setBaseView(View view)
	{
		if (mBaseView!=null)
		{
			mContainerLayout.removeView(mBaseView);
		}
		mBaseView=view;
		addViewToContainer(0,view);
	}
	@Override
	public Context getPageContext()
	{
		return mContext;
	}
	@Override
	public View getBaseView()
	{
		return mBaseView;
	}
	@Override
	public FrameLayout getBaseContainerLayout()
	{
		return mContainerLayout;
	}
	@Override
	public void addViewToContainer(View view)
	{
		addViewToContainer(-1,view);
	}
	
	/**
	 * 返回处理消息的Handler对象
	 * @return
	 */
	protected Handler getHandler()
	{
		return this.mHandler;
	}
	/**
	 * 获取一个Message对象
	 * @return
	 */
	protected Message getNewHandlerMessage()
	{
		return mHandler.obtainMessage();
	}
	/**
	 * 发送消息
	 * @param msg
	 */
	protected void sendHandlerMessage(Message msg)
	{
		mHandler.sendMessage(msg);
	}
	/**
	 * 发送消息
	 * @param what
	 */
	protected void sendHandlerMessage(int what)
	{
		Message msg=getNewHandlerMessage();
		msg.what=what;
		sendHandlerMessage(msg);
	}
	/**
	 * 处理消息的handler
	 */

	private HHWeakHandler<Activity> mHandler=new HHWeakHandler<Activity>(this)
	{
		@Override
		public void processHandlerMessage(Message msg)
		{
			processHandlerMsg(msg);
		}
	};
	/**
	 * 根据View的ID，在parentView中查找ID为viewID的View
	 * @param parentView
	 * @param viewID
	 * @return
	 */
	public <T> T getViewByID(View parentView,int viewID)
	{
		return HHViewHelper.getViewByID(parentView, viewID);
	}
	/**
	 * 设置当前页面显示的标题（只有在当前页面使用的是HHDefaultTopViewManager的时候才有效果）
	 * @param pageTitle
	 */
	public void setPageTitle(String pageTitle)
	{
		HHTopViewManagerImp avalibleManager = mTopManager.getAvalibleManager();
		if (avalibleManager instanceof HHDefaultTopViewManager)
		{
			HHDefaultTopViewManager defaultTopViewManager=(HHDefaultTopViewManager) avalibleManager;
			defaultTopViewManager.getTitleTextView().setText(pageTitle);
		}
	}
	/**
	 * 设置当前页面的标题
	 * @param resID		标题的资源文件的ID
	 */
	public void setPageTitle(int resID)
	{
		setPageTitle(getString(resID));
	}
}
