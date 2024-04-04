package kz.sdu.project.service;

import kz.sdu.project.AttendanceSystemApplication;
import kz.sdu.project.dto.AuthDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AttendanceSystemApplication.class)
public class LoginUnitTest {

    @Autowired
    private AuthService authService;

    private static final String TOKEN_FIELD = "token";

    @Test
    public void loginCorrectly() {
        AuthDto authDto = new AuthDto();
        authDto.setLogin("210107177");
        authDto.setPassword("210107177");
        Assert.assertNotNull(authService.login(authDto).get(TOKEN_FIELD));
    }

    @Test
    public void loginShouldBeInSystem() {
        AuthDto authDto = new AuthDto();
        authDto.setLogin("210107178");
        authDto.setPassword("210107177");
        Assert.assertThrows("Username not found",
                UsernameNotFoundException.class, () -> authService.login(authDto));
    }

    @Test
    public void passwordMustBeCorrect() {
        AuthDto authDto = new AuthDto();
        authDto.setLogin("210107177");
        authDto.setPassword("woekrokewf"); // wrong password

        Assert.assertThrows("Password was incorrect",
                IllegalArgumentException.class, () -> authService.login(authDto));
    }
}
