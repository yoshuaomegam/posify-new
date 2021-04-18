package com.latihan.posify.repository;

import com.latihan.posify.model.PasswordResetToken;
import com.latihan.posify.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByUser(User user);

    Optional<PasswordResetToken> findByToken(String token);
}
