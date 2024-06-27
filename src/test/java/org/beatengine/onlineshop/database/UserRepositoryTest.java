package org.beatengine.onlineshop.database;

import org.beatengine.onlineshop.entity.Role;
import org.beatengine.onlineshop.entity.User;
import org.beatengine.onlineshop.entity.mapping.AccountDetail;
import org.beatengine.onlineshop.entity.mapping.AccountDetails;
import org.beatengine.onlineshop.repository.RoleRepository;
import org.beatengine.onlineshop.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE) //Using the real database (configure a real test-database)!!!
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    TestEntityManager entityManager;

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
