package solo.dev.solobackend.member.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import solo.dev.solobackend.member.domain.MemberVO;
import solo.dev.solobackend.member.mapper.MemberMapper;

@Repository
public class MemberDAO {
    private SqlSessionTemplate sqlSessionTemplate;

    public MemberDAO() {
        System.out.println("WeatherDAO created");
    }

    @Autowired
    public MemberDAO(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    public MemberVO findByKakaoId(String kakaoId) {
        return sqlSessionTemplate.getMapper(MemberMapper.class).findByKakaoId(kakaoId);
    }

    public void insertNewUserInfo(MemberVO member) {
        sqlSessionTemplate.getMapper(MemberMapper.class).insertNewUserInfo(member);
    }
}
