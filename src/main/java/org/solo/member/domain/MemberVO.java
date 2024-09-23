package org.solo.member.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {
    private String kakaoId; // 카카오 ID
    private String nickName; // 닉네임
    private String profileImage; // 프로필 이미지 URL
    private String name; // 추가 입력된 이름
    private String email; // 추가 입력된 이메일
    private String birthDate; // 생년월일
}

//CREATE TABLE `kakaoMember` (
//        `kakaoId` varchar(100) NOT NULL,
//  `nickName` varchar(50) NOT NULL,
//  `profileImage` varchar(500) DEFAULT NULL,
//  `name` varchar(50) NOT NULL,
//  `email` varchar(100) NOT NULL,
//  `birthdate` date NOT NULL,
//PRIMARY KEY (`kakaoId`)
//)