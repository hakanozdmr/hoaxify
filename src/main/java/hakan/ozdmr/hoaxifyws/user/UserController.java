package hakan.ozdmr.hoaxifyws.user;

import hakan.ozdmr.hoaxifyws.error.ApiError;
import hakan.ozdmr.hoaxifyws.shared.GenericMessage;
import hakan.ozdmr.hoaxifyws.shared.Messages;
import hakan.ozdmr.hoaxifyws.user.exception.NotUniqueEmailException;
import jakarta.validation.Valid;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    UserService userService;


    Logger LOGGER = Logger.getLogger(UserController.class.getName());

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    GenericMessage createUser(@Valid @RequestBody User user){
        userService.save(user);
        String message = Messages.getMessageForLocale("hoaxify.create.user.success.message",LocaleContextHolder.getLocale());
        LOGGER.info(message+" " + user.getUsername());
        return new GenericMessage(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        String message = Messages.getMessageForLocale("hoaxify.error.validation",LocaleContextHolder.getLocale());
        apiError.setMessage(message);
        apiError.setStatus(400);
        var validationErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField,FieldError::getDefaultMessage, (existing,replacing)-> existing) );
//        Map<String,String> validationErrors = new HashMap<>();
//        for (var fieldError : exception.getBindingResult().getFieldErrors()){
//            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
//        }
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiError handleNotUniqueEmailEx(NotUniqueEmailException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage(exception.getMessage());
        apiError.setStatus(400);
        apiError.setValidationErrors(exception.getValidationErrors());
        return apiError;
    }
}
