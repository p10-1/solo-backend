package solo.dev.solobackend.member.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import solo.dev.solobackend.member.domain.MemberVO;

@Mapper
public interface MemberMapper {
    MemberVO findByKakaoId(@Param("kakaoId") String kakaoId);
    void insertNewUserInfo(MemberVO member);
}
