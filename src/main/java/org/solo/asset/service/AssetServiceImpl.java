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

    @Override
    public List<AssetVO> getAssetData(String userId, int months) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(months -1);
        List<AssetVO> asset = assetMapper.getAssetData(userId, startDate, endDate);
        System.out.println("asset" + asset);
        return asset;
    }
    @Override
    public Map<String, Double> calculateAssetAverages() {
        List<AssetVO> allAssets = assetMapper.getAllAssetData();
        Map<String, Double> averages = new HashMap<>();

        averages.put("cash", calculateAverage(allAssets, AssetVO::getCash));
        averages.put("deposit", calculateAverage(allAssets, AssetVO::getDeposit));
        averages.put("stock", calculateAverage(allAssets, AssetVO::getStock));
        averages.put("property", calculateAverage(allAssets, AssetVO::getProperty));

        return averages;
    }

    private Double calculateAverage(List<AssetVO> assets, Function<AssetVO, String> getter) {
        return assets.stream()
                .mapToDouble(asset -> {
                    String value = getter.apply(asset);
                    return Arrays.stream(value.replaceAll("[\\[\\]\"]", "").split(","))
                            .mapToDouble(Double::parseDouble)
                            .sum();
                })
                .average()
                .orElse(0.0);
    }

}
