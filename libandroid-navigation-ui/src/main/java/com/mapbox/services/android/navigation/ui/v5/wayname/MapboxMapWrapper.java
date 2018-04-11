package com.mapbox.services.android.navigation.ui.v5.wayname;

import android.graphics.Bitmap;
import android.graphics.PointF;

import com.mapbox.geojson.Feature;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Projection;
import com.mapbox.mapboxsdk.style.layers.Layer;

import java.util.List;

public class MapboxMapWrapper {

  private final MapboxMap mapboxMap;

  public MapboxMapWrapper(MapboxMap mapboxMap) {
    this.mapboxMap = mapboxMap;
  }

  void addLayer(Layer layer) {
    mapboxMap.addLayer(layer);
  }

  Layer getLayer(String layerId) {
    return mapboxMap.getLayerAs(layerId);
  }

  List<Layer> getLayers() {
    return mapboxMap.getLayers();
  }

  Projection getProjection() {
    return mapboxMap.getProjection();
  }

  List<Feature> queryRenderedFeatures(PointF mapPoint, String[] layerIds) {
    return mapboxMap.queryRenderedFeatures(mapPoint, layerIds);
  }

  void addImage(String iconName, Bitmap bitmap) {
    mapboxMap.addImage(iconName, bitmap);
  }
}
