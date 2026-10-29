package org.beatengine.onlineshop.entity.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiPredicate;

/**
 * The mapping for the UserControllerApi<br>
 *     final private Long userId;<br>
 *     final private String email;<br>
 *     final private String displayName;<br>
 *     final private List<String> userRoles;<br>
 *     final private Long pictureId;<br>
 */
public class AccountDetails {

    private String auth = Boolean.TRUE.toString();
    private Long userId;
    private String email;
    private String displayName;
    private List<String> userRoles;
    private Long pictureId;

    /**
     * The account details from the database.
     * @param details A list of the role-names for this user
     */
    public AccountDetails(final List<AccountDetail> details) {
        userRoles = new ArrayList<>();
        boolean first = true;
        for(final AccountDetail detail: details)
        {
            final String userRole = detail.getUserRole();
            if(userRole != null) {
                this.userRoles.add(userRole);
            }
            if(first)
            {
                this.userId = detail.getUserId();
                this.email = detail.getEmail();
                this.displayName = detail.getDisplayName();
                this.pictureId = detail.getPictureId();
                first = false;
            }

        }
    }

    /**
     * Use this for Token expired cases
     * @return An empty AccountDetails Object with auth = false
     */
    public static AccountDetails unauthenticated(final String msg) {
        final AccountDetails details = new AccountDetails(Collections.emptyList());
        if(msg == null) {
            details.auth = Boolean.FALSE.toString();
        }
        else
        {
            details.auth = msg;
        }
        return details;
    }

    public  AccountDetails()
    {

    }

    public static AccountDetails unauthenticated() {
        return unauthenticated(null);
    }

    public Long getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getUserRoles() {
        return userRoles;
    }

    public Long getPictureId() {
        return pictureId;
    }

    public String getAuth() {
        return auth;
    }


    public static BiPredicate<AccountDetails, AccountDetails> INTEGRATION_TEST_EQUALS = new BiPredicate<AccountDetails, AccountDetails>() {
        @Override
        public boolean test(AccountDetails accountDetails, AccountDetails accountDetails2) {
            boolean eq = accountDetails.getAuth().equals(accountDetails2.getAuth())
                    && accountDetails.getDisplayName().equals(accountDetails2.getDisplayName())
                    && accountDetails.getEmail().equals(accountDetails2.getEmail())
                    && accountDetails.getUserId().equals(accountDetails2.getUserId())
                    && accountDetails.getUserRoles().equals(accountDetails2.getUserRoles());
            if(accountDetails.getPictureId() != null)
            {
                eq = eq && accountDetails.getPictureId().equals(accountDetails2.getPictureId());
            }
            return eq;
        }
    };
}
