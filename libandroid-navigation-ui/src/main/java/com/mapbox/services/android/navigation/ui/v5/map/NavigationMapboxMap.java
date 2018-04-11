package com.mapbox.services.android.navigation.ui.v5.map;

import android.content.Context;
import android.location.Location;

import com.mapbox.api.directions.v5.models.DirectionsRoute;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;
import com.mapbox.services.android.navigation.ui.v5.R;
import com.mapbox.services.android.navigation.ui.v5.ThemeSwitcher;
import com.mapbox.services.android.navigation.ui.v5.camera.NavigationCamera;
import com.mapbox.services.android.navigation.ui.v5.route.NavigationMapRoute;
import com.mapbox.services.android.navigation.v5.navigation.MapboxNavigation;

import java.util.List;

public class NavigationMapboxMap {

  private MapboxMap mapboxMap;
  private NavigationMapRoute mapRoute;
  private NavigationCamera mapCamera;
  private LocationLayerPlugin locationLayer;

  public NavigationMapboxMap(MapView mapView, MapboxMap mapboxMap, MapboxNavigation mapboxNavigation) {
    this.mapboxMap = mapboxMap;
    initRoute(mapView, mapboxMap);
    initCamera(mapboxMap, mapboxNavigation);
    initLocationLayer(mapView, mapboxMap);
  }

  public LocationLayerPlugin retrieveLocationLayer() {
    return locationLayer;
  }

  public void drawRoute(DirectionsRoute directionsRoute) {
    mapRoute.addRoute(directionsRoute);
  }

  public void addMarker(Context context, Point position) {
    LatLng markerPosition = new LatLng(position.latitude(),
      position.longitude());
    Icon marker = ThemeSwitcher.retrieveMapMarker(context);
    mapboxMap.addMarker(new MarkerOptions()
      .position(markerPosition)
      .icon(marker));
  }

  public void clearMarkers() {
    List<Marker> mapMarkers = mapboxMap.getMarkers();
    for (Marker marker : mapMarkers) {
      mapboxMap.removeMarker(marker);
    }
  }

  public void updateLocation(Location location) {
    locationLayer.forceLocationUpdate(location);
    // TODO projection manager for wayname
  }

  public void updateCameraTrackingEnabled(boolean isEnabled) {
    mapCamera.updateCameraTrackingLocation(isEnabled);
  }

  public void startCamera(DirectionsRoute directionsRoute) {
    mapCamera.start(directionsRoute);
  }

  public void resumeCamera(Location location) {
    mapCamera.resume(location);
  }

  public void resetCameraPosition() {
    mapCamera.resetCameraPosition();
  }

  public void onDestroy() {
    mapCamera.onDestroy();
  }

  private void initRoute(MapView mapView, MapboxMap map) {
    Context context = mapView.getContext();
    int routeStyleRes = ThemeSwitcher.retrieveNavigationViewStyle(context, R.attr.navigationViewRouteStyle);
    mapRoute = new NavigationMapRoute(null, mapView, map, routeStyleRes);
  }

  private void initLocationLayer(MapView mapView, MapboxMap map) {
    Context context = mapView.getContext();
    int locationLayerStyleRes = ThemeSwitcher.retrieveNavigationViewStyle(context,
      R.attr.navigationViewLocationLayerStyle);
    locationLayer = new LocationLayerPlugin(mapView, map, null, locationLayerStyleRes);
    locationLayer.setRenderMode(RenderMode.GPS);
  }

  private void initCamera(MapboxMap map, MapboxNavigation mapboxNavigation) {
    mapCamera = new NavigationCamera(map, mapboxNavigation);
  }
}
