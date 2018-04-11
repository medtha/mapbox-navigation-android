package com.mapbox.services.android.navigation.ui.v5.wayname;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.location.Location;

import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.style.layers.Layer;
import com.mapbox.mapboxsdk.style.layers.Property;
import com.mapbox.services.android.navigation.ui.v5.utils.ViewUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.visibility;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.MAPBOX_WAYNAME_ICON;
import static com.mapbox.services.android.navigation.v5.navigation.NavigationConstants.MAPBOX_WAYNAME_LAYER;

public class MapWayname {

  private static final Set<String> ROAD_LABEL_LAYER_ID = new HashSet<String>() {
    {
      add("road-label-small");
      add("road-layer-medium");
      add("road-layer-large");
    }
  };

  private MapboxMapWrapper mapboxMap;
  private Layer waynameLayer;

  public MapWayname(MapboxMapWrapper mapboxMap, Layer waynameLayer) {
    this.mapboxMap = mapboxMap;
    this.waynameLayer = waynameLayer;
    if (containsStreetsStyle()) {
      initWaynameLayer();
    }
  }

  public void updateWaynameWithLocation(Location location) {
    List<Feature> roads = findRoadLabelFeatures(location);
    for (Feature road : roads) {
      Timber.d("Road %s", road.toString());
    }
  }

  public void updateWaynameLayer(Context context, String wayname) {
    Layer waynameLayer = mapboxMap.getLayer(MAPBOX_WAYNAME_LAYER);
    if (waynameLayer != null) {
      createWaynameIcon(context, wayname, waynameLayer);
    }
  }

  public void updateWaynameVisibility(boolean isVisible) {
    Layer waynameLayer = mapboxMap.getLayer(MAPBOX_WAYNAME_LAYER);
    if (waynameLayer != null) {
      waynameLayer.setProperties(visibility(isVisible ? Property.VISIBLE : Property.NONE));
    }
  }

  private void initWaynameLayer() {
    mapboxMap.addLayer(waynameLayer);
  }

  private boolean containsStreetsStyle() {
    for (Layer layer : mapboxMap.getLayers()) {
      if (ROAD_LABEL_LAYER_ID.contains(layer.getId())) {
        return true;
      }
    }
    return false;
  }

  private List<Feature> findRoadLabelFeatures(Location location) {
    LatLng latLng = new LatLng(location);
    PointF mapPoint = mapboxMap.getProjection().toScreenLocation(latLng);
    String[] layerIds = new String[0];
    ROAD_LABEL_LAYER_ID.toArray(layerIds);
    return mapboxMap.queryRenderedFeatures(mapPoint, layerIds);
  }

  private void createWaynameIcon(Context context, String wayname, Layer waynameLayer) {
    boolean isVisible = waynameLayer.getVisibility().getValue().contentEquals(Property.VISIBLE);
    if (isVisible) {
      WaynameView waynameView = new WaynameView(context);
      waynameView.setWaynameText(wayname);
      Bitmap waynameBitMap = ViewUtils.loadBitmapFromView(waynameView);
      if (waynameBitMap != null) {
        mapboxMap.addImage(MAPBOX_WAYNAME_ICON, waynameBitMap);
        waynameLayer.setProperties(iconImage(MAPBOX_WAYNAME_ICON));
      }
    }
  }
}
