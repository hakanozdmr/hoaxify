package hakan.ozdmr.hoaxifyws.user;

import hakan.ozdmr.hoaxifyws.email.EmailService;
import hakan.ozdmr.hoaxifyws.user.exception.ActivationNotificationException;
import hakan.ozdmr.hoaxifyws.user.exception.InvalidTokenException;
import hakan.ozdmr.hoaxifyws.user.exception.NotUniqueEmailException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    UserRepository userRepository;

    PasswordEncoder passwordEncoder;
    EmailService emailService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional(rollbackOn = MailException.class)
    public  void save(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            emailService.sendActivationEmail(user);
        }catch (DataIntegrityViolationException e){
            throw new NotUniqueEmailException();
        }catch (MailException ex){
            throw new ActivationNotificationException();
        }
    }

    public void activateUser(String token){
        User user = userRepository.findByActivationToken(token);
        if (user == null ){
            throw new InvalidTokenException();
        }
        user.setActive(true);
        user.setActivationToken(null);
        userRepository.save(user);
    }


}
