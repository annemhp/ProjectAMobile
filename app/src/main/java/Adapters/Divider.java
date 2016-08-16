package Adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.development.mymla.R;


/**
 * Created by Development on 19/6/2016.
 */

public class Divider extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int mOrientation;

    public Divider(Context context, int orientation){

        mDivider = ContextCompat.getDrawable(context, R.drawable.divider);
        mOrientation = orientation;

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation == LinearLayoutManager.VERTICAL) {
            drawHorizontalDivider(c,parent,state);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {

        int left,top,right,bottom;
        left =0;
        right = parent.getWidth();
        int count   = parent.getChildCount();

        for(int i=0;i<count;i++){
            View current = parent.getChildAt(i);
            top = current.getTop();
            bottom = top + mDivider.getIntrinsicHeight();
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
    }
}
