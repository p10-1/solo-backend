package org.solo.asset.service;

import org.solo.asset.domain.AssetVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AssetService {


    List<AssetVO> getAssetData(String userId, int months);

    Map<String, Double> calculateAssetAverages();
}
