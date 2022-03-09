package team.jlpt.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import team.jlpt.auth.userinfo.GoogleUserInfo;
import team.jlpt.auth.userinfo.KakaoUserInfo;
import team.jlpt.auth.userinfo.OAuth2UserInfo;
import team.jlpt.entity.Member;
import team.jlpt.entity.Role;
import team.jlpt.repository.MemberRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;
        String provider = userRequest.getClientRegistration().getRegistrationId();  //google

        if(provider.equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(provider.equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }


        String providerId = oAuth2UserInfo.getProviderId();
        String name = provider + "_" + providerId;

        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String password = bCryptPasswordEncoder.encode("password"+uuid);

        String email = oAuth2UserInfo.getEmail();

        Member findMember = memberRepository.findByEmail(email).orElse(null);

        if(findMember == null){ // join
            findMember = Member.Oauth2Register()
                    .email(email)
                    .name(name)
                    .password(password)
                    .provider(provider)
                    .providerId(providerId).build();

            memberRepository.save(findMember);
        }
        return new PrincipalDetails(findMember, oAuth2UserInfo);
    }
}
