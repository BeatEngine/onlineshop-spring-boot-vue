package org.beatengine.onlineshop.controller;

import org.beatengine.onlineshop.Onlineshop;
import org.beatengine.onlineshop.TestDatabaseBaseTest;
import org.beatengine.onlineshop.controller.api.UserController;
import org.beatengine.onlineshop.entity.mapping.AccountDetails;
import org.beatengine.onlineshop.repository.UserRepository;
import org.beatengine.onlineshop.security.JwtAuthenticationFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.client.ExchangeResult;
import tools.jackson.databind.ObjectMapper;


import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/***
 *  This UserControllerTest tests the complete workflow from login testUser per POST to UserController.getDetails
 */

@Import(TestDatabaseBaseTest.class)
@ExtendWith(OutputCaptureExtension.class) // For checking spring start logs
@ContextConfiguration(classes = {Onlineshop.class, TestDatabaseBaseTest.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest extends ControllerBaseTestSingleUser {

    @Autowired
    private UserController controller;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void beforeEach(CapturedOutput output) throws SQLException {
        super.beforeEach(output); // IMPORTANT ensure the tests doesn't run on the real database!
    }

    @AfterEach
    public void afterEach()
    {
        super.afterEach(); // Clean SingleTestUser
    }


    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }


    @Test
    void getDetailsTest() {

        final AccountDetails accountDetails =
                new AccountDetails(userRepository.findAccountDetails(BigInteger.valueOf(testUser.getId())));

        assertThat(accountDetails.getUserId()).
                withFailMessage("Database pre-conditions not met: unexpected accountDetails returned!").
                isEqualTo(testUser.getId());

        assertThat(this.authCookieValue).
                withFailMessage("Test preconditions active session cookie not available").isNotNull();

        final ExchangeResult result = restTestClient.get().uri(UserController.GET_DETAILS_PATH_TEST)
                .cookie(JwtAuthenticationFilter.AUTH_COOKIE_NAME, authCookieValue)
                .exchange()
                .expectStatus().isOk()
                .returnResult();
                //.expectBody(AccountDetails.class)
               //.isEqualTo(accountDetails);


        final String responseBody = new String(result.getResponseBodyContent(), StandardCharsets.UTF_8);
        final ObjectMapper objectMapper = new ObjectMapper();
        final AccountDetails accountDetailsResult = objectMapper.readValue(responseBody, AccountDetails.class);

        assertThat(accountDetails).usingEquals(AccountDetails.INTEGRATION_TEST_EQUALS).isEqualTo(accountDetailsResult);

    }


}
