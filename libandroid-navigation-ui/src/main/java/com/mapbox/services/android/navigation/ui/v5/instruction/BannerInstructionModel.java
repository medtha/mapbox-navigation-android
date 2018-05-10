package com.mapbox.services.android.navigation.ui.v5.instruction;

import android.content.Context;

import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.BannerText;
import com.mapbox.services.android.navigation.v5.milestone.BannerInstructionMilestone;
import com.mapbox.services.android.navigation.v5.routeprogress.RouteProgress;

public class BannerInstructionModel extends InstructionModel {

  private BannerInstructionMilestone milestone;

  public BannerInstructionModel(Context context, BannerInstructionMilestone milestone,
                                RouteProgress progress, String location, @DirectionsCriteria.VoiceUnitCriteria String unitType) {
    super(context, progress, location, unitType);
    this.milestone = milestone;
  }

  @Override
  BannerText getPrimaryBannerText() {
    return milestone.getBannerInstructions().primary();
  }

  @Override
  BannerText getSecondaryBannerText() {
    return milestone.getBannerInstructions().secondary();
  }
}
