package com.lcodecore.twinklingrefreshlayout;

import android.content.Context;
import android.util.AttributeSet;  
import android.view.GestureDetector;  
import android.view.MotionEvent;  
import android.widget.LinearLayout;  
import android.widget.Scroller;  
/**
 * 总体思路:
 * 处理View的Touch事件,即重写onTouchEvent()方法:
 * 当手指抬起时将其回到原点,其余情况交给GestureDetector处理.
 * 
 * 在GestureDetector中重点是覆写onScroll()方法.在该方法中得到
 * Y方向滑动的距离,从而设置 mScroller.startScroll()方法,准备滑动.
 * 随之刷新界面invalidate()从而执行方法computeScroll().
 * 在computeScroll()方法中调用 scrollTo()方法实现真正的滑动.
 * 
 * 注意事项:
 * 1 scrollTo()方法的参数:
 *   scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
 *   并且在此之后也要调用postInvalidate()进行刷新 
 * 
 * 
 * 方法说明:
 * 1 mScroller.getFinalX(Y)()
 *   The final X(Y) offset as an absolute distance from the origin.
 *   返回滚动结束位置(得到当前X(Y)距离原始位置的值).仅针对"fling"滚动有效.
 *   也就是说该方法是针对滚动结束而言的.
 *   
 *   坐标方向:
 *   X方向的距离,正数向左,负数向右
 *   Y方向的距离,正数向上,负数向下
 *   
 * 2 mScroller.getCurrX(Y)()
 *   The new X offset as an absolute distance from the origin.
 *   The new Y offset as an absolute distance from the origin.
 *   返回当前滚动 X(Y)方向的偏移
 *   也就是说该方法是针对滚动中而言的.
 *   
 *   坐标方向:
 *   X方向的距离,正数向左,负数向右
 *   Y方向的距离,正数向上,负数向下
 *   
 * 3 invalidate()与postInvalidate()的区别
 *   invalidate()在UI线程自身中使用;postInvalidate()在非UI线程中使用.
 *   这是目前网络资料的普遍说法,还待进一步研究.
 *   
 * 4 startScroll(int startX, int startY, int dx, int dy, int duration)
 *   第一,二个参数起始位置;第三,四个滚动的偏移量;第五个参数持续时间
 *   
 *
 */
public class BounceableLinearLayout extends LinearLayout {  
    private Scroller mScroller;  
    private GestureDetector mGestureDetector;  
      
    public BounceableLinearLayout(Context context) {  
        this(context, null);  
    }  
      
    public BounceableLinearLayout(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        setClickable(true);  
        setLongClickable(true);  
        mScroller = new Scroller(context);  
        mGestureDetector = new GestureDetector(context, new GestureListenerImpl());  
    }  
  
      
    @Override  
    public void computeScroll() {  
        if (mScroller.computeScrollOffset()) {
            System.out.println("computeScroll()---> "+
        	                   "mScroller.getCurrX()="+mScroller.getCurrX()+","+
        			           "mScroller.getCurrY()="+mScroller.getCurrY());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());  
            //必须执行postInvalidate()从而调用computeScroll()
            //其实,在此调用invalidate();亦可
            postInvalidate(); 
        }  
        super.computeScroll();  
    }  
      
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        switch (event.getAction()) {  
       case MotionEvent.ACTION_UP :  
    	   //手指抬起时回到最初位置
           prepareScroll(0, 0);  
            break;  
        default:  
        	//其余情况交给GestureDetector手势处理
            return mGestureDetector.onTouchEvent(event);  
      }  
       return super.onTouchEvent(event);  
   }  
    
  
	class GestureListenerImpl implements GestureDetector.OnGestureListener {
		@Override
		public boolean onDown(MotionEvent e) {
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			return false;
		}

		//控制拉动幅度:
		//int disY=(int)((distanceY - 0.5)/2);
		//亦可直接调用:
		//smoothScrollBy(0, (int)distanceY);
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,float distanceX, float distanceY) {
			int disY = (int) ((distanceY - 0.5) / 2);
			beginScroll(0, disY);
			return false;
		}

		public void onLongPress(MotionEvent e) {

		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
			return false;
		}

	}  
	
	  
    //滚动到目标位置 
    protected void prepareScroll(int fx, int fy) {  
        int dx = fx - mScroller.getFinalX();  
        int dy = fy - mScroller.getFinalY();  
        beginScroll(dx, dy);  
    }  
  
    
     //设置滚动的相对偏移 
    protected void beginScroll(int dx, int dy) {  
    	System.out.println("smoothScrollBy()---> dx="+dx+",dy="+dy);
    	//第一,二个参数起始位置;第三,四个滚动的偏移量
        mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), dx, dy);  
        System.out.println("smoothScrollBy()---> " +
        		           "mScroller.getFinalX()="+mScroller.getFinalX()+","+
        		           "mScroller.getFinalY()="+mScroller.getFinalY());
        
        //必须执行invalidate()从而调用computeScroll()
        invalidate();
    } 
    
      
	
	
}