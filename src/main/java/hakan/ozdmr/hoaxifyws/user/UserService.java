package hakan.ozdmr.hoaxifyws.user;

import hakan.ozdmr.hoaxifyws.user.exception.NotUniqueEmailException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public  void save(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }catch (DataIntegrityViolationException e){
            throw new NotUniqueEmailException();
        }
    }
}
