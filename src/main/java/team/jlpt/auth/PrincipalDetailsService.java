package team.jlpt.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import team.jlpt.entity.Member;
import team.jlpt.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //username = value of PrincipalDetails.getUsername() = email

        Member findMember = memberRepository.findByEmail(username).orElse(null);
        if(findMember != null){
            return new PrincipalDetails(findMember);
        }

        return null;
    }
}
