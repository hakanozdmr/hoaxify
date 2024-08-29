package hakan.ozdmr.hoaxifyws.user.exception;

import hakan.ozdmr.hoaxifyws.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Collections;
import java.util.Map;

public class NotUniqueEmailException extends RuntimeException{
    public NotUniqueEmailException(){
        super(Messages.getMessageForLocale("hoaxify.error.validation", LocaleContextHolder.getLocale()));
    }

    public Map<String,String> getValidationErrors(){
        return Collections.singletonMap("email",Messages.getMessageForLocale("hoaxify.constraints.email.notunique", LocaleContextHolder.getLocale()));
    }
}
