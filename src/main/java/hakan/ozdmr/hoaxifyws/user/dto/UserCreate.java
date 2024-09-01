package hakan.ozdmr.hoaxifyws.user.dto;

import hakan.ozdmr.hoaxifyws.user.User;
import hakan.ozdmr.hoaxifyws.user.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreate(@NotBlank(message = "{hoaxify.constraints.username.notblank}")
                         @Size(min = 4, max = 255) String username,
                         @NotBlank
                         @Email
                         @UniqueEmail String email,
                         @NotBlank
                         @Size(min = 8, max = 255)
                         @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "{hoaxify.constraints.password.pattern}")
                         String password
) {
    public User toUser(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }
}