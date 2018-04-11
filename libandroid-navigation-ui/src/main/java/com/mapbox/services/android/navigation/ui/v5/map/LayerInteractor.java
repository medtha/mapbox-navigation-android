package com.mapbox.services.android.navigation.ui.v5.map;

import android.graphics.Bitmap;

import com.mapbox.mapboxsdk.style.layers.Layer;

import java.util.List;

public interface LayerInteractor {

  void addLayer(Layer layer);

  Layer getLayer(String layerId);

  List<Layer> getLayers();

  void addLayerImage(String imageName, Bitmap image);
}
