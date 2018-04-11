package com.mapbox.services.android.navigation.ui.v5;

import android.location.Location;
import android.support.design.widget.BottomSheetBehavior;
import android.text.TextUtils;

import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;

class NavigationPresenter {

  private NavigationContract.View view;
  private String wayname = "";

  NavigationPresenter(NavigationContract.View view) {
    this.view = view;
  }

  void onRecenterClick() {
    view.setSummaryBehaviorHideable(false);
    view.setSummaryBehaviorState(BottomSheetBehavior.STATE_EXPANDED);
    view.resetCameraPosition();
    view.hideRecenterBtn();
  }

  void onCancelBtnClick() {
    view.finishNavigationView();
  }

  void onMapScroll() {
    view.setSummaryBehaviorHideable(true);
    view.setSummaryBehaviorState(BottomSheetBehavior.STATE_HIDDEN);
    view.setCameraTrackingEnabled(false);
  }

  void onSummaryBottomSheetHidden() {
    view.showRecenterBtn();
  }

  void onRouteUpdate(DirectionsRoute directionsRoute) {
    view.drawRoute(directionsRoute);
    view.startCamera(directionsRoute);
  }

  void onProgressUpdate(RouteProgress routeProgress) {
    String wayname = routeProgress.currentLegProgress().currentStep().name();
    boolean validWayname = !TextUtils.isEmpty(wayname);
    boolean newWaynameString = !this.wayname.contentEquals(wayname);
    if (validWayname && newWaynameString) {
      view.updateWaynameVisibility(true);
      view.updateWaynameLayer(wayname);
      this.wayname = wayname;
    } else if (!validWayname) {
      view.updateWaynameVisibility(false);
    }
  }

  void onDestinationUpdate(Point point) {
    view.addMarker(point);
  }

  void onShouldRecordScreenshot() {
    view.takeScreenshot();
  }

  void onNavigationLocationUpdate(Location location) {
    view.resumeCamera(location);
    view.updateLocationLayer(location);
  }
}
