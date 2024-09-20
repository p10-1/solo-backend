package org.solo.mypage.service;

import org.solo.mypage.domain.MypageVO;

public interface MypageService {
    void insertUserData(MypageVO mypageVO);
    boolean findUserData(String userID);
    void updateUserData(MypageVO userData);
}
