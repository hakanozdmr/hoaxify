package hakan.ozdmr.hoaxifyws.user.validation;

import hakan.ozdmr.hoaxifyws.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;


public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail,String> {

    UserRepository userRepository ;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByEmail(s);
    }
}
