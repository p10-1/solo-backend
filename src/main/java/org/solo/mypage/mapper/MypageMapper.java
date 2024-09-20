package org.solo.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.solo.mypage.domain.MypageVO;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MypageMapper {
    void insertUserData(MypageVO mypageVO);

    boolean findUserData(String userID);

    void updateUserData(MypageVO userData);
}
