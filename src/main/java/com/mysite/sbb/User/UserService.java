package com.mysite.sbb.User;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.zip.DataFormatException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // user 생성
    public SiteUser create(String username, String nickname, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setNickname(nickname);
        user.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        this.userRepository.save(user);
        return user;
    }

    // user 불러오기
    public SiteUser getUser(String username) throws DataFormatException {
        Optional<SiteUser> siteUser =this.userRepository.findByusername(username);
        if(siteUser.isPresent()){
            return siteUser.get();
        }
        else{
            throw new DataFormatException("siteuser not found");
        }
    }
}