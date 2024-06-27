package org.beatengine.onlineshop.entity.mapping;

import java.util.List;

public class AccountDetail {

    final private Long userId;
    final private String email;
    final private String displayName;
    final private String userRole;
    final private Long pictureId;

    /**
     * The account details from the database.
     * @param userId
     * @param email
     * @param displayName
     * @param userRole A role-names for this user
     * @param pictureId
     */
    public AccountDetail(final Long userId, final String email, final String displayName,
                          final String userRole, final Long pictureId) {
        this.userId = userId;
        this.email = email;
        this.displayName = displayName;
        this.userRole = userRole;
        this.pictureId = pictureId;
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

    public String getUserRole() {
        return userRole;
    }

    public Long getPictureId() {
        return pictureId;
    }
}
