package org.beatengine.onlineshop.controller;

import io.jsonwebtoken.lang.Assert;
import org.beatengine.onlineshop.Onlineshop;
import org.beatengine.onlineshop.TestDatabaseBaseTest;
import org.beatengine.onlineshop.entity.User;
import org.beatengine.onlineshop.repository.UserRepository;
import org.beatengine.onlineshop.security.JwtAuthenticationFilter;
import org.beatengine.onlineshop.security.SecurityConfig;
import org.beatengine.onlineshop.security.controller.LoginAuthController;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.client.ExchangeResult;
import org.springframework.test.web.servlet.client.RestTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

/**
 * ControllerBaseTestSingleUser uses the testDatabase from TestDatabaseBaseTest and
 * Prepare a single user that have an active login.
 * Provides:
 * ControllerBaseTestSingleUser.testUser --> The test user
 * ControllerBaseTestSingleUser.password --> The test-password (not hash)
 * ControllerBaseTestSingleUser.restTestClient --> The HttpClient for MVC Tests
 */

@Import(TestDatabaseBaseTest.class)
@ExtendWith(OutputCaptureExtension.class) // For checking spring start logs
@ContextConfiguration(classes = {Onlineshop.class, TestDatabaseBaseTest.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerBaseTestSingleUser extends TestDatabaseBaseTest{

    @Autowired
    UserRepository userRepository;

    @Autowired
    private DataSource dataSource;

    @Autowired
    LoginAuthController loginAuthController;

    public User testUser;
    public final String password = "TESTrEsT76setfs8TEST7ezf8sezf87TEST";

    @LocalServerPort
    public int port;

    public String authCookieValue = null;

    public RestTestClient restTestClient;

    public void checkIfLoginPageIsAvailable()
    {
        restTestClient.get().uri(LoginAuthController.LOGIN_AUTH_PATH_TEST)
                .exchange()
                .expectStatus().isOk();
    }

    public void checkIfMainPageIsAvailableAfterAuth()
    {
        final ExchangeResult exchangeResult = restTestClient.get().uri("/")
                .cookie(JwtAuthenticationFilter.AUTH_COOKIE_NAME, authCookieValue)
                .exchange()
                .expectStatus().isOk().returnResult();
        final String html = new String(exchangeResult.getResponseBodyContent(), StandardCharsets.UTF_8);
        Assert.notNull(html);
        //HTML with app loader after authentication
        Assert.isTrue(html.contains("id=\"app\""));
    }

    /**
     * Login the testUser and retrieve the session cookie value in this.authCookieValue
     */
    public void loginUser()
    {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("email", testUser.getEmail());
        form.add("password", password);

        final ExchangeResult exchangeResult = restTestClient.post().uri(LoginAuthController.LOGIN_AUTH_PATH_TEST)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form)
                .exchange()
                .returnResult();

        final String responseHtml = new String(exchangeResult.getResponseBodyContent(), StandardCharsets.UTF_8);
        Assert.doesNotContain(responseHtml, "Bad credentials");

        ResponseCookie first = exchangeResult.getResponseCookies().getFirst(JwtAuthenticationFilter.AUTH_COOKIE_NAME);
        Assert.notNull(first, "Cookie from login is null!");
        Assert.isTrue(first.isHttpOnly(), "Cookie is accessible from JavaScript insecure!");
        Assert.isTrue(first.isSecure(), "Cookie is insecure!");

        authCookieValue = first.getValue();
    }

    public void bindRestTestClient()
    {
        restTestClient = RestTestClient.bindToServer().baseUrl("http://localhost:"+port)
                .build();
    }

    @BeforeEach
    public void beforeEach(CapturedOutput output) throws SQLException {
        // IMPORTANT ensure the tests doesn't run on the real database!
        Assertions.assertTrue(dataSource.getConnection().getMetaData().
                        getURL().endsWith("/"+ TestDatabaseBaseTest.TEST_DATABASE_NAME),
                "Used database: " +  dataSource.getConnection().getMetaData().
                        getURL() + "\nExpected: " + TestDatabaseBaseTest.TEST_DATABASE_NAME
        );

        setupTestDatabase();
        bindRestTestClient();
        checkIfLoginPageIsAvailable();
        loginUser();
        checkIfMainPageIsAvailableAfterAuth();
    }

    @Test
    public void Base()
    {

    }


    public void setupTestDatabase()
    {
        final String email = "findAccountDetailsREST@test.test";
        testUser = new User();
        testUser.setPictureId(null);
        testUser.setEmail(email);
        testUser.setDisplayName("Bob");
        testUser.setSha256Pass(SecurityConfig.passwordEncoder().encode(password));

        User byEmail = userRepository.findByEmail(email);
        if(byEmail != null)
        {
            userRepository.delete(byEmail);
        }

        // Create Test-User
        userRepository.save(testUser);
        byEmail = userRepository.findByEmail(email);
        Assert.notNull(byEmail);
    }

    @AfterEach
    public void afterEach()
    {
        if(testUser != null) {
            userRepository.delete(testUser);
        }
    }
}
