package com.zhangqie.shoppingcart.widget;

import android.support.v4.view.MotionEventCompat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ListView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

import static com.nineoldandroids.view.ViewHelper.setTranslationX;
import static com.nineoldandroids.view.ViewPropertyAnimator.animate;


public class FrontViewToMove {

	private View frontView;// 所要滑动的视图
	private int downX;// 手指按下时的x坐标
	private boolean hasMoved = false;// 判断视图是否被移动
	private int xToMove = 100;// 视图所要被移动的距离，默认200
	private ListView listView;// 如果所需移动的视图为ListView或其子类的item项，传入视图容器，限制其上下滚动

	/**
	 * @param frontView
	 *            所要滑动的视图
	 */
	public FrontViewToMove(View frontView) {
		this.frontView = frontView;
		moveListener();
	}

	/**
	 * @param frontView
	 *            所要滑动的视图
	 * @param xToMove
	 *            视图所要被移动的距离
	 */
	public FrontViewToMove(View frontView, int xToMove) {
		this.frontView = frontView;
		this.xToMove = xToMove;
		moveListener();
	}

	/**
	 * @param frontView
	 *            所要滑动的视图
	 * @param listView
	 *            所要滑动的视图的容器
	 */
	public FrontViewToMove(View frontView, ListView listView) {
		this.frontView = frontView;
		this.listView = listView;
		moveListener();
	}

	/**
	 * @param frontView
	 *            所要滑动的视图
	 * @param listView
	 *            所要滑动的视图的容器
	 * @param xToMove
	 *            视图所要被移动的距离
	 */
	public FrontViewToMove(View frontView, ListView listView, int xToMove) {
		this.frontView = frontView;
		this.listView = listView;
		this.xToMove = xToMove;
		moveListener();
	}

	/**
	 * 设置frontView的OnTouch监听，使其产生滑动的动画效果
	 */
	public void moveListener() {
		frontView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View view, MotionEvent motionEvent) {

				switch (MotionEventCompat.getActionMasked(motionEvent)) {
				case MotionEvent.ACTION_DOWN: {

					downX = (int) motionEvent.getRawX();
					if (hasMoved) {
						downX = downX + xToMove;
					} else {
						view.onTouchEvent(motionEvent);// 当视图没有被移动，返回事件，使点击事件可用。
					}
					return true;
				}

				case MotionEvent.ACTION_UP: {

					float deltaX = motionEvent.getRawX() - downX;
					boolean swap = false;

					if ((deltaX > -xToMove / 2 && hasMoved)
							|| (deltaX < -xToMove && !hasMoved)) {
						swap = true;
					}

					if (swap) {
						if (!hasMoved) {
							generateRevealAnimate(frontView, -xToMove);
							hasMoved = true;
						} else {
							generateRevealAnimate(frontView, 0);
							hasMoved = false;
						}
					} else {
						if (hasMoved) {
							generateRevealAnimate(frontView, -xToMove);
						} else {
							generateRevealAnimate(frontView, 0);
						}
					}

					break;
				}

				case MotionEvent.ACTION_MOVE: {
					float deltaX = motionEvent.getRawX() - downX;

					MotionEvent cancelEvent = MotionEvent.obtain(motionEvent);

					cancelEvent.setAction(MotionEvent.ACTION_CANCEL
							| (motionEvent.getActionIndex() << MotionEvent.ACTION_POINTER_INDEX_SHIFT));

					if (deltaX < -10) {
						view.onTouchEvent(cancelEvent);// 当滑动时清空该视图的点击事件
						if (null != listView) {// 当视图滑动时限制listView的上下滚动
							listView.requestDisallowInterceptTouchEvent(false);
							listView.onTouchEvent(cancelEvent);
						}
					}

					if (!(deltaX > 0 && !hasMoved)) {
						setTranslationX(frontView, deltaX);
					}
					return true;
				}
				}
				return false;
			}
		});

	}

	/**
	 * @param view
	 *            所要移动的视图
	 * @param deltaX
	 *            最终移动的距离
	 */
	public void generateRevealAnimate(final View view, float deltaX) {
		int moveTo = 0;
		moveTo = (int) deltaX;
		animate(view).translationX(moveTo).setDuration(10)
				.setListener(new AnimatorListenerAdapter() {
					@Override
					public void onAnimationEnd(Animator animation) {

					}
				});
	}

}
