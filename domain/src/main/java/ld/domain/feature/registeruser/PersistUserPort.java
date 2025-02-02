package ld.domain.feature.registeruser;

import ld.domain.user.User;

public interface PersistUserPort {
    User saveUser(User user);
}
