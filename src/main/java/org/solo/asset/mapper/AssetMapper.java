package org.solo.asset.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.asset.domain.AssetVO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AssetMapper {
    AssetVO getAssetData(String userId);

}
