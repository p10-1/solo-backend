package org.solo.asset.service;

import org.solo.asset.domain.AssetVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.solo.asset.mapper.AssetMapper;

import java.time.LocalDate;
import java.util.List;

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
        LocalDate startDate = endDate.minusMonths(months - 1);
        List<AssetVO> asset = assetMapper.getAssetData(userId, startDate, endDate);
        System.out.println("asset" + asset);
        return asset;
    }
}
