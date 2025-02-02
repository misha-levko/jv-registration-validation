package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationTest {
    private static final int Minimum_Age = 18;
    private final String Login_Default = "name@gmail.com";
    private final String Password_Example = "123abc";
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUserExample() {
        user = new User();
        user.setAge(Minimum_Age);
        user.setLogin(Login_Default);
        user.setPassword(Password_Example);
    }

    @AfterEach
    void afterEach() {
        Storage.people.clear();
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_Age_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_Age_Ok() {
        user.setAge(18);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_Login_Ok() {
        user.setLogin(Login_Default);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_Login_notOk() {
        Storage.people.add(user);
        assertThrows(RuntimeException.class, () ->
                    registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_Password_Ok() {
        user.setPassword(Password_Example);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_lengthPassword_notOk() {
        user.setPassword("12ab");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }
}
