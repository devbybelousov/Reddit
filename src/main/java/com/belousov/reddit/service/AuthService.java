package com.belousov.reddit.service;

import com.belousov.reddit.dto.RegisterRequest;
import com.belousov.reddit.exceptions.SpringRedditException;
import com.belousov.reddit.model.NotificationEmail;
import com.belousov.reddit.model.User;
import com.belousov.reddit.model.VerificationToken;
import com.belousov.reddit.repository.UserRepository;
import com.belousov.reddit.repository.VerificationTokenRepository;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class AuthService {

  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final MailService mailService;

  @Transactional
  public void signup(RegisterRequest registerRequest) {
    User user = new User();
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setCreated(Instant.now());
    user.setEnabled(false);
    userRepository.save(user);

    String token = generateVerificationToken(user);
    mailService
        .sendMail(new NotificationEmail("Please Activate Your Account",
            user.getEmail(),
            "Thank you for signing up to Spring Reddit, " +
            "please click on the below url to activate your account: " +
            "http://localhost:8080/api/auth/accountVerification/" + token));
  }

  private String generateVerificationToken(User user) {
    String token = UUID.randomUUID().toString().replace('-', ')');
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken(token);
    verificationToken.setUser(user);

    verificationTokenRepository.save(verificationToken);
    return token;
  }

  public void verifyAccount(String token) {
    VerificationToken verificationToken = verificationTokenRepository.findByToken(token).orElseThrow(() -> new SpringRedditException("Invalid token."));
    fetchUserAndEnable(verificationToken);
  }

  @Transactional
  public void fetchUserAndEnable(VerificationToken verificationToken){
    String username = verificationToken.getUser().getUsername();
    User user = userRepository.findByUsername(username).orElseThrow(() -> new SpringRedditException("User not found with name - " + username));
    user.setEnabled(true);
    userRepository.save(user);
  }
}
