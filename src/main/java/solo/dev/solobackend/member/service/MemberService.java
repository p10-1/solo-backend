package solo.dev.solobackend.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solo.dev.solobackend.member.dao.MemberDAO;
import solo.dev.solobackend.member.domain.MemberVO;

@Service
public class MemberService {
    private final MemberDAO memberDAO;

    @Autowired
    public MemberService(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public MemberVO findByKakaoId(String kakaoId) {
        return memberDAO.findByKakaoId(kakaoId);
    }

    public MemberVO insertNewUserInfo(String kakaoId, String nickName, String profileImage, String name, String email, String birthDate) {
        System.out.println("inserting new user info");
        MemberVO newUser = new MemberVO();
        newUser.setKakaoId(kakaoId);
        newUser.setNickName(nickName);
        newUser.setProfileImage(profileImage);
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setBirthDate(birthDate);

        memberDAO.insertNewUserInfo(newUser);
        return newUser;
    }
}
