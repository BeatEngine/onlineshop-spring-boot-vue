package org.beatengine.onlineshop.entity.mapping;

import org.beatengine.onlineshop.entity.User;
import org.beatengine.onlineshop.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
}
