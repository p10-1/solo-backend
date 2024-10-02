package org.solo.asset.service;

import org.solo.asset.domain.AssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.solo.asset.mapper.AssetMapper;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(months -1);
        List<AssetVO> asset = assetMapper.getAssetData(userId, startDate, endDate);
        System.out.println("asset" + asset);
        return asset;
    }
    // 모든 사용자의 평균 자산을 계산하는 메서드

    @Override
    public Map<String, Double> calculateAssetAverages() {
        List<AssetVO> allAssets = assetMapper.getAllAssetData();
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
                        System.out.println("Null or empty value found for asset: " + asset);
                        return 0.0; // null이거나 빈 문자열인 경우 0으로 처리
                    }
                    try {
                        return Arrays.stream(value.replaceAll("[\\[\\]\"]", "").split(","))
                                .filter(s -> !s.trim().isEmpty()) // 빈 문자열 필터링
                                .mapToDouble(s -> {
                                    try {
                                        return Double.parseDouble(s.trim());
                                    } catch (NumberFormatException e) {
                                        System.err.println("Error parsing value: " + s);
                                        return 0.0; // 파싱 실패 시 0으로 처리
                                    }
                                })
                                .sum();
                    } catch (Exception e) {
                        System.err.println("Error processing asset value: " + value);
                        e.printStackTrace();
                        return 0.0; // 예외 발생 시 0으로 처리
                    }
                })
                .average()
                .orElse(0.0); // 데이터가 없으면 0.0 반환
    }

}
