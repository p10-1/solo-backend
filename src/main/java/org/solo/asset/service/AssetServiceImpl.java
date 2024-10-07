package org.solo.asset.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.solo.asset.domain.AssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.solo.asset.mapper.AssetMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;

@Service
@Transactional
public class AssetServiceImpl implements AssetService {
    private final AssetMapper assetMapper;

    @Autowired
    public AssetServiceImpl(AssetMapper assetMapper) {
        this.assetMapper = assetMapper;
    }
    // 특정 사용자의 자산 데이터를 기간에 맞게 가져오는 메서드

    @Override
    public List<AssetVO> getAssetData(String userId, int months) {
        System.out.println("AssetServiceImpl: getAssetData called for userId: " + userId);
        LocalDate endDate_ = LocalDate.now();
        LocalDate startDate = endDate_.minusMonths(months -1);
        LocalDate endDate = endDate_.plusDays(1);
        List<AssetVO> asset = assetMapper.getAssetData(userId, startDate, endDate);
        System.out.println("AssetServiceImpl: Retrieved assets: " + asset);
        return asset;
    }
    // 모든 사용자의 평균 자산을 계산하는 메서드

    @Override
    public Map<String, Double> calculateAssetAverages() {
        System.out.println("AssetServiceImpl: calculateAssetAverages called");
        List<AssetVO> allAssets = assetMapper.getAllAssetData();
        System.out.println("AssetServiceImpl: All assets: " + allAssets);
        Map<String, Double> averages = new HashMap<>();

        averages.put("cash", calculateAverage(allAssets, AssetVO::getCash));
        averages.put("deposit", calculateAverage(allAssets, AssetVO::getDeposit));
        averages.put("stock", calculateAverage(allAssets, AssetVO::getStock));
        averages.put("insurance", calculateAverage(allAssets, AssetVO::getInsurance));

        return averages;
    }
    // 주어진 자산 리스트에서 특정 자산 유형의 평균을 계산하는 메서드
    private Double calculateAverage(List<AssetVO> assets, Function<AssetVO, String> getter) {
        return assets.stream()
                .mapToDouble(asset -> {
                    String value = getter.apply(asset);
                    if (value == null || value.isEmpty()) {
                        return 0.0;
                    }
                    return Arrays.stream(value.replaceAll("[\\[\\]\"]", "").split(","))
                            .filter(s -> !s.trim().isEmpty())
                            .mapToDouble(s -> Double.parseDouble(s.trim()))
                            .sum();
                })
                .average()
                .orElse(0.0);
    }

    @Override
    public Map<String, Object> compareAssetWithAverages(String type) {
        System.out.println("AssetServiceImpl: compareAssetWithAverages called with type: " + type);

        Map<String, Double> overallAverages = calculateAssetAverages();
        System.out.println("AssetServiceImpl: Overall averages: " + overallAverages);

        Map<String, Double> typeAverages;

        if (type == null || type.equals("null") || type.equals("undefined")) {
            typeAverages = overallAverages;
            type = "전체";
        } else {
            typeAverages = calculateAssetAveragesByType(type);
        }
        System.out.println("AssetServiceImpl: Type averages for " + type + ": " + typeAverages);

        Map<String, Object> comparisonData = new HashMap<>();
        comparisonData.put("overallAverage", overallAverages);
        comparisonData.put("typeAverage", typeAverages);
        comparisonData.put("userType", type);

        System.out.println("AssetServiceImpl: Comparison data: " + comparisonData);
        return comparisonData;
    }

    private Map<String, Double> calculateAssetAveragesByType(String type) {
        System.out.println("AssetServiceImpl: calculateAssetAveragesByType called for type: " + type);
        List<AssetVO> assetsOfType = assetMapper.getAssetDataByType(type);
        System.out.println("AssetServiceImpl: Assets of type " + type + ": " + assetsOfType);

        if (assetsOfType.isEmpty()) {
            System.out.println("AssetServiceImpl: No assets found for type " + type + ", returning overall averages");
            return calculateAssetAverages();
        }

        Map<String, Double> averages = new HashMap<>();
        averages.put("cash", calculateAverage(assetsOfType, AssetVO::getCash));
        averages.put("deposit", calculateAverage(assetsOfType, AssetVO::getDeposit));
        averages.put("stock", calculateAverage(assetsOfType, AssetVO::getStock));
        averages.put("insurance", calculateAverage(assetsOfType, AssetVO::getInsurance));

        System.out.println("AssetServiceImpl: Calculated averages for type " + type + ": " + averages);
        return averages;
    }


}
