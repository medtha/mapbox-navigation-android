package com.mapbox.services.android.navigation.ui.v5.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.support.design.widget.CoordinatorLayout;
import android.util.Base64;
import android.util.TypedValue;
import android.view.View;

import java.io.ByteArrayOutputStream;

public class ViewUtils {

  public static Bitmap captureView(View view) {
    View rootView = view.getRootView();
    rootView.setDrawingCacheEnabled(true);
    Bitmap bitmap = Bitmap.createBitmap(rootView.getDrawingCache());
    rootView.setDrawingCacheEnabled(false);
    return bitmap;
  }

  public static String encodeView(Bitmap capture) {
    // Resize to 250px wide while keeping the aspect ratio
    int width = 250;
    int height = Math.round((float) width * capture.getHeight() / capture.getWidth());
    Bitmap scaled = Bitmap.createScaledBitmap(capture, width, height, /*filter=*/true);

    // Convert to JPEG low-quality (~20%)
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    scaled.compress(Bitmap.CompressFormat.JPEG, 20, stream);

    // Convert to base64 encoded string
    byte[] data = stream.toByteArray();
    return Base64.encodeToString(data, Base64.DEFAULT);
  }

  public static Bitmap loadBitmapFromView(View v) {
    if (v.getMeasuredHeight() <= 0) {
      v.measure(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
      Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
      Canvas c = new Canvas(b);
      v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
      v.draw(c);
      return b;
    }
    return null;
  }

  public static float dpToPx(Context context, int dp) {
    Resources resources = context.getResources();
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
  }
}
