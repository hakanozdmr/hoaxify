package hakan.ozdmr.hoaxifyws.user.exception;

import hakan.ozdmr.hoaxifyws.shared.Messages;
import org.springframework.context.i18n.LocaleContextHolder;

public class ActivationNotificationException extends RuntimeException{

    public ActivationNotificationException() {
        super(Messages.getMessageForLocale("hoaxify.create.user.email.failure", LocaleContextHolder.getLocale()));
    }
}
