package org.beatengine.onlineshop.database;

import org.beatengine.onlineshop.Onlineshop;
import org.beatengine.onlineshop.TestDatabaseBaseTest;
import org.beatengine.onlineshop.entity.Role;
import org.beatengine.onlineshop.entity.User;
import org.beatengine.onlineshop.entity.mapping.AccountDetail;
import org.beatengine.onlineshop.entity.mapping.AccountDetails;
import org.beatengine.onlineshop.repository.RoleRepository;
import org.beatengine.onlineshop.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Import(TestDatabaseBaseTest.class)
@ExtendWith(OutputCaptureExtension.class) // For checking spring start logs
@ContextConfiguration(classes = {Onlineshop.class, TestDatabaseBaseTest.class})
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED) //Don't rollback
public class UserRepositoryTest extends TestDatabaseBaseTest{

    @Autowired
    private DataSource dataSource;

    @BeforeEach
    void ensureDatabaseIsTestDatabase(CapturedOutput output) throws SQLException {
        // IMPORTANT ensure the tests doesn't run on the real database!
        Assertions.assertTrue(dataSource.getConnection().getMetaData().
                getURL().endsWith("/"+TestDatabaseBaseTest.TEST_DATABASE_NAME),
                "Used database: " +  dataSource.getConnection().getMetaData().
                        getURL() + "\nExpected: " + TestDatabaseBaseTest.TEST_DATABASE_NAME
                );

        // Check that the Spring start log shows the test-database is used.
        Assertions.assertTrue(output.getAll()
                .contains("/"+TestDatabaseBaseTest.TEST_DATABASE_NAME), "Test database not found in log expected: " + TestDatabaseBaseTest.TEST_DATABASE_NAME);
    }

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    private void assertUnorderedListsEquals(final List<String> expected , final List<String> actual)
    {
        for(final String s : actual)
        {
            if(!expected.contains(s))
            {
                Assertions.fail("Actual element '" + s + "' was not found in expected elements.");
            }
        }

        for(final String s : expected)
        {
            if(!actual.contains(s))
            {
                Assertions.fail("Expected element '" + s + "' was not found in actual elements.");
            }
        }
    }



    @Test
    void findAccountDetails() {

        final User testUser = new User();
        testUser.setPictureId(null);
        testUser.setEmail("findAccountDetails@test.test");
        testUser.setDisplayName("Bob");
        testUser.setSha256Pass("TESTe6tf76setfs8TEST7ezf8sezf87TEST");

        User byEmail = userRepository.findByEmail("findAccountDetails@test.test");
        if(byEmail != null)
        {
            userRepository.delete(byEmail);
        }

        // Create Test-User
        userRepository.save(testUser);

        final Role testRole1 = new Role();
        testRole1.setDescription("Test 1");
        testRole1.setName("findAccountDetails1");
        testRole1.setDisplayName("Role 1");

        final Role testRole2 = new Role();
        testRole2.setDescription("Test 2");
        testRole2.setName("findAccountDetails2");
        testRole2.setDisplayName("Role 2");

        try {
            // Create Roles
            roleRepository.save(testRole1);
            roleRepository.save(testRole2);
            testUser.roles = new HashSet<>();
            testUser.roles.add(testRole1);
            testUser.roles.add(testRole2);
            // Create Role-Relations
            userRepository.save(testUser);

            // TEST findAccountDetails
            List<AccountDetail> accountDetailList = userRepository.findAccountDetails(BigInteger.valueOf(testUser.getId()));
            AccountDetails accountDetails = new AccountDetails(accountDetailList);

            Assertions.assertEquals(testUser.getEmail(), accountDetails.getEmail(), "Unequal Email");
            Assertions.assertEquals(testUser.getDisplayName(), accountDetails.getDisplayName(), "Unequal Email");
            Assertions.assertEquals(testUser.getPictureId(), accountDetails.getPictureId(), "Unequal Email");

            assertUnorderedListsEquals(testUser.roles.stream().map(r -> r.getName()).toList(), accountDetails.getUserRoles());

        }
        catch (final Exception e)
        {
               Assertions.fail(e);
        }
        finally {
            if(testUser.roles != null) {
                testUser.roles.remove(testRole1);
                testUser.roles.remove(testRole2);
                // Remove relations
                userRepository.save(testUser);
            }

            // Delete User
            userRepository.delete(testUser);
            // Delete Roles
            roleRepository.delete(testRole1);
            roleRepository.delete(testRole2);
        }
    }

}
