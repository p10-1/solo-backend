package org.solo.asset.service;

import org.solo.asset.domain.AssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.solo.asset.mapper.AssetMapper;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {
    private final AssetMapper assetMapper;

    @Autowired
    public AssetServiceImpl(AssetMapper assetMapper) {
        this.assetMapper = assetMapper;
    }
//    @Override
//    public List<AssetVO> getAllAssetData() {
//        return assetMapper.getAllAssetData();
//    }
    // 특정 사용자의 자산 데이터를 기간에 맞게 가져오는 메서드

    @Override
    public List<AssetVO> getAssetData(String userId, int months) {
        LocalDate endDate_ = LocalDate.now();
        LocalDate startDate = endDate_.minusMonths(months -1);
        LocalDate endDate = endDate_.plusDays(1);
        List<AssetVO> asset = assetMapper.getAssetData(userId, startDate, endDate);
        return asset;
    }
    // 모든 사용자의 평균 자산을 계산하는 메서드

    @Override
    public Map<String, Double> calculateAssetAverages() {
        List<AssetVO> allAssets = assetMapper.getAllAssetData(); // 모든 사용자의 최신 데이터를 가져옴
        int totalUserCount = assetMapper.getTotalUserCount(); // 전체 사용자 수
//        System.out.println("----------------------------------- " +calculateAverages(allAssets, totalUserCount));

        return calculateAverages(allAssets, totalUserCount);
    }

    @Override
    public Map<String, Object> compareAssetWithAverages(String type) {
        Map<String, Double> overallAverages = calculateAssetAverages(); // 전체 평균
        Map<String, Double> typeAverages;

        if (type == null || type.equals("null") || type.equals("undefined")) {
            typeAverages = overallAverages; // 기본적으로 전체 평균 사용
            type = "전체";
        } else {
            typeAverages = calculateAssetAveragesByType(type); // 특정 유형 평균 계산
        }

        Map<String, Object> comparisonData = new HashMap<>();
        comparisonData.put("overallAverage", overallAverages);
        comparisonData.put("typeAverage", typeAverages);
        comparisonData.put("userType", type);

        return comparisonData;
    }
    @Override
    public Map<String, Double> calculateAssetAveragesByType(String type) {
        List<AssetVO> assetsOfType = assetMapper.getAssetDataByType(type);
        int userCountByType = assetMapper.getUserCountByType(type);
//        System.out.println("----------------------------------- " +calculateAverages(assetsOfType, userCountByType));

        return calculateAverages(assetsOfType, userCountByType);
    }

    private Map<String, Double> calculateAverages(List<AssetVO> assets, int userCount) {
//        System.out.println("[calculateAverages] Calculating averages, user count: " + userCount);
        Map<String, Double> totals = new HashMap<>();
        Map<String, Integer> counts = new HashMap<>();
        totals.put("cash", 0.0);
        totals.put("deposit", 0.0);
        totals.put("stock", 0.0);
        totals.put("insurance", 0.0);
        counts.put("cash", 0);
        counts.put("deposit", 0);
        counts.put("stock", 0);
        counts.put("insurance", 0);

        for (AssetVO asset : assets) {
//            System.out.println("[calculateAverages] Processing asset: " + asset);
            if (asset.getCash() != null && !asset.getCash().isEmpty()) {
                double cashSum = sumJsonArray(asset.getCash());
                totals.put("cash", totals.get("cash") + cashSum);
                counts.put("cash", counts.get("cash") + 1);
            }
            if (asset.getDeposit() != null && !asset.getDeposit().isEmpty()) {
                double depositSum = sumJsonArray(asset.getDeposit());
                totals.put("deposit", totals.get("deposit") + depositSum);
                counts.put("deposit", counts.get("deposit") + 1);
            }
            if (asset.getStock() != null && !asset.getStock().isEmpty()) {
                double stockSum = sumJsonArray(asset.getStock());
                totals.put("stock", totals.get("stock") + stockSum);
                counts.put("stock", counts.get("stock") + 1);
            }
            if (asset.getInsurance() != null && !asset.getInsurance().isEmpty()) {
                double insuranceSum = sumJsonArray(asset.getInsurance());
                totals.put("insurance", totals.get("insurance") + insuranceSum);
                counts.put("insurance", counts.get("insurance") + 1);
            }
        }

        Map<String, Double> averages = new HashMap<>();
        for (String key : totals.keySet()) {
            int count = counts.get(key);
            averages.put(key, (count > 0 ? totals.get(key) / count : 0.0));
//            System.out.println("[calculateAverages] Calculated average for " + key + ": " + averages.get(key));
        }

        return averages;
    }



    private double sumJsonArray(String jsonArray) {
        String[] values = jsonArray.replaceAll("[\\[\\]\"]", "").split(",");
        return Arrays.stream(values)
                .filter(s -> !s.trim().isEmpty())
                .mapToDouble(Double::parseDouble)
                .sum();
    }

}
