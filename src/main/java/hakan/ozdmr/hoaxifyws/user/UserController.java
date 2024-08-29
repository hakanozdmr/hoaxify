package hakan.ozdmr.hoaxifyws.user;

import hakan.ozdmr.hoaxifyws.error.ApiError;
import hakan.ozdmr.hoaxifyws.shared.GenericMessage;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
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
        LOGGER.info("Kullanıcı Kaydedildi " + user.getUsername());
        return new GenericMessage("Kullanıcı Kaydedildi");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    ResponseEntity<ApiError> handleMethodArgNotValidEx(MethodArgumentNotValidException exception){
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation error");
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
        apiError.setMessage("Validation error");
        apiError.setStatus(400);
        Map<String,String> validationErrors = new HashMap<>();
        validationErrors.put("email" , "E-mail in use");
        apiError.setValidationErrors(validationErrors);
        return apiError;
    }
}
