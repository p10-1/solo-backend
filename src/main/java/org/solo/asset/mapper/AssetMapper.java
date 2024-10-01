package org.solo.asset.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.solo.asset.domain.AssetVO;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Mapper
@Repository
public interface AssetMapper {
    List<AssetVO> getAssetData(@Param("userId") String userId,
                               @Param("startDate") LocalDate startDate,
                               @Param("endDate") LocalDate endDate);
    List<AssetVO> getAllAssetData();

}

