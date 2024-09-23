package org.solo.mypage.domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MypageVO {

    private int userNo;
    private String userID;
    //private int salary;
    private int cash;
    private int stock;
    private int property;
    private int deposit;
    private String consume;
    //private String job;
}
