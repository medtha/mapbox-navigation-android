package com.mapbox.services.android.navigation.ui.v5.map;

import android.graphics.Bitmap;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.style.layers.Layer;

import java.util.List;

public class MapLayerInteractor implements LayerInteractor {

  private MapboxMap mapboxMap;

  public MapLayerInteractor(MapboxMap mapboxMap) {
    this.mapboxMap = mapboxMap;
  }

  @Override
  public void addLayer(Layer layer) {
    mapboxMap.addLayer(layer);
  }

  @Override
  public Layer getLayer(String layerId) {
    return mapboxMap.getLayerAs(layerId);
  }

  @Override
  public List<Layer> getLayers() {
    return mapboxMap.getLayers();
  }

  @Override
  public void addLayerImage(String imageName, Bitmap image) {
    mapboxMap.addImage(imageName, image);
  }
}
