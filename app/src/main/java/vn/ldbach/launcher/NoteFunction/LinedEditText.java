package vn.ldbach.launcher.NoteFunction;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * Draw lines like real notepad
 */

public final class LinedEditText extends AppCompatEditText {
    static final int LINE_SPACING = 8;
    private static Paint linePaint;

    static {
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStyle(Paint.Style.STROKE);
    }

    Rect bounds = new Rect();

    public LinedEditText(Context context, AttributeSet attributes) {
        super(context, attributes);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int firstLineY = getLineBounds(0, bounds);
        int lineHeight = getLineHeight();
        int totalLines = Math.max(getLineCount(), getHeight() / lineHeight);
        for (int i = 0; i < totalLines; i++) {
            int lineY = firstLineY + i * lineHeight + LINE_SPACING;
            canvas.drawLine(bounds.left, lineY, bounds.right, lineY, linePaint);
        }
        super.onDraw(canvas);
    }
}
