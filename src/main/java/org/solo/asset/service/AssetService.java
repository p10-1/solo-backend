package org.solo.asset.service;

import org.solo.asset.domain.AssetVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface AssetService {

    // 특정 사용자의 자산 데이터를 가져오는 메서드

    List<AssetVO> getAssetData(String userId, int maxMonths);
    // 모든 사용자의 평균 자산을 계산하는 메서드

    Map<String, Double> calculateAssetAverages();

    Map<String, Object> compareAssetWithAverages(String type);

}
