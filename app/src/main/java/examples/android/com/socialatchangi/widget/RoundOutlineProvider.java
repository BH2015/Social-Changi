package examples.android.com.socialatchangi.widget;


import android.graphics.Outline;
import android.view.View;
import android.view.ViewOutlineProvider;

public class RoundOutlineProvider extends ViewOutlineProvider {

    private final int mSize;

    public RoundOutlineProvider(int size) {
        if (0 > size) {
            throw new IllegalArgumentException("size needs to be > 0. Actually was " + size);
        }
        mSize = size;
    }

    @Override
    public final void getOutline(View view, Outline outline) {
        outline.setOval(0, 0, mSize, mSize);
    }

}
