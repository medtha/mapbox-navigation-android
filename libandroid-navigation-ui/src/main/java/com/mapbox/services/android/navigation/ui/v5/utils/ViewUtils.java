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

import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.PADDING_MULTIPLIER;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.TWELVE_DP;

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

  public static Bitmap loadBitmapFromView(View view) {
    if (view.getMeasuredHeight() <= 0) {
      view.measure(CoordinatorLayout.LayoutParams.WRAP_CONTENT, CoordinatorLayout.LayoutParams.WRAP_CONTENT);
      Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(bitmap);
      view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
      view.draw(canvas);
      return bitmap;
    }
    return null;
  }

  public static int buildDynamicMapPadding(Context context, int mapViewHeight,
                                           int bottomSheetHeight, boolean isWaynameVisible) {
    int padding = mapViewHeight - ((bottomSheetHeight * PADDING_MULTIPLIER));
    if (isWaynameVisible) {
      return (int) (padding - ViewUtils.dpToPx(context, TWELVE_DP));
    }
    return padding;
  }

  public static float dpToPx(Context context, int dp) {
    Resources resources = context.getResources();
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
  }
}
