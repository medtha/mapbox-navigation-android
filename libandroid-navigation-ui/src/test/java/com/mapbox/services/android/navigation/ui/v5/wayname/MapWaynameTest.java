package com.mapbox.services.android.navigation.ui.v5.wayname;

import com.mapbox.mapboxsdk.style.layers.Layer;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MapWaynameTest {

//  @Test
//  public void givenStreetsStyleProvided_waynameLayerIsAdded() throws Exception {
//    List<Layer> layers = new ArrayList<>();
//    Layer streetsLayer = mock(Layer.class);
//    Layer waynameLayer = mock(Layer.class);
//    when(streetsLayer.getId()).thenReturn("road-label-small");
//    MapboxMap mapboxMap = mock(MapboxMap.class);
//    when(mapboxMap.getLayers()).thenReturn(layers);
//
//    new MapWayname(mapboxMap);
//
//    verify(mapboxMap).addLayer(eq(waynameLayer));
//  }

  @Test
  public void givenStreetsStyleProvided_waynameLayerIsAdded() throws Exception {
    List<Layer> layers = new ArrayList<>();
    Layer streetsLayer = mock(Layer.class);
    when(streetsLayer.getId()).thenReturn("road-label-small");
    layers.add(streetsLayer);
    Layer waynameLayer = mock(Layer.class);
    MapboxMapWrapper mapboxMap = mock(MapboxMapWrapper.class);
    when(mapboxMap.getLayers()).thenReturn(layers);

    new MapWayname(mapboxMap, waynameLayer);

    verify(mapboxMap).addLayer(eq(waynameLayer));
  }
}