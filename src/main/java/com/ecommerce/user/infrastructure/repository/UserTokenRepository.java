package com.ecommerce.user.infrastructure.repository;

import com.ecommerce.user.infrastructure.entities.UserToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserTokenRepository extends CrudRepository<UserToken, UUID> {
    Optional<UserToken> findByRefreshToken(String refreshToken);
    Optional<UserToken> deleteByUserId(String userId);
}