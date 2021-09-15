package mk.napijse.junit;

import mk.napijse.model.entities.User;
import mk.napijse.model.enumerations.Role;
import mk.napijse.model.exceptions.InvalidArgumentsException;
import mk.napijse.model.exceptions.UserNotFoundException;
import mk.napijse.repository.SecureTokenRepository;
import mk.napijse.repository.UserRepository;
import mk.napijse.service.AuthenticationService;
import mk.napijse.service.MailService;
import mk.napijse.service.SecureTokenService;
import mk.napijse.service.UserService;
import mk.napijse.service.impl.AuthenticationServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserService userService;
    @Mock
    private SecureTokenRepository secureTokenRepository;
    @Mock
    private SecureTokenService tokenService;
    @Mock
    private MailService mailService;

    private AuthenticationService service;

    @Before
    public void init() {
        User user = new User("user", "user", "user@mail.com", "user", "user", Role.ROLE_USER);
        Mockito.when(this.userRepository.findByUsernameAndPassword(Mockito.anyString(),Mockito.anyString())).thenReturn(java.util.Optional.of(user));
        this.service = Mockito.spy(new AuthenticationServiceImpl(this.userRepository, this.encoder, this.userService,
                this.secureTokenRepository, this.tokenService, this.mailService));
    }

    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidArgumentsException expected",
                InvalidArgumentsException.class,
                () -> this.service.login(null, "user"));
    }

    @Test
    public void testEmptyUsername() {
        String username = "";
        Assert.assertThrows("InvalidArgumentsException expected",
                InvalidArgumentsException.class,
                () -> this.service.login(username, "user"));
    }

    @Test
    public void testEmptyPassword() {
        String username = "user";
        String password = "";
        Assert.assertThrows("InvalidArgumentsException expected",
                InvalidArgumentsException.class,
                () -> this.service.login(username, password));
    }

    @Test
    public void testNullPassword() {
        String username = "user";
        String password = null;
        Assert.assertThrows("InvalidArgumentsException expected",
                InvalidArgumentsException.class,
                () -> this.service.login(username, password));
    }


    @Test
    public void testUnsuccessfulLogIn() {
        Assert.assertThrows("User with username: nonexistent was not found",
                UserNotFoundException.class, ()->this.service.login("nonexistent", "password"));
    }


}
