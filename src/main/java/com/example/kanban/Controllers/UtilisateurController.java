package com.example.kanban.Controllers;

import com.example.kanban.Models.PasswordResetToken;
import com.example.kanban.Models.Utilisateur;
import com.example.kanban.Services.EmailService;
import com.example.kanban.Services.UtilisateurService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/utilisateurs")
public class UtilisateurController {

    private final UtilisateurService utilisateurService;
    private final EmailService emailService;

    @Autowired
    public UtilisateurController(UtilisateurService utilisateurService, EmailService emailService) {
        this.utilisateurService = utilisateurService;
        this.emailService = emailService;
    }


    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        return new ResponseEntity<>(utilisateurs, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur createdUtilisateur = utilisateurService.createUtilisateur(utilisateur);
        return new ResponseEntity<>(createdUtilisateur, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id);
        if (utilisateur != null) {
            return new ResponseEntity<>(utilisateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable Long id, @RequestBody Utilisateur utilisateur) {
        Utilisateur updatedUtilisateur = utilisateurService.updateUtilisateur(id, utilisateur);
        if (updatedUtilisateur != null) {
            return new ResponseEntity<>(updatedUtilisateur, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        Utilisateur utilisateur = utilisateurService.findByEmail(email);
        if (utilisateur == null) {
            return ResponseEntity.badRequest().body("User with email " + email + " does not exist.");
        }
        PasswordResetToken token = utilisateurService.createPasswordResetToken(utilisateur);

        String resetLink = "http://localhost:3000/reset-password?token=" + token.getToken();
        String htmlContent = "<html>" +
                "<head><style>" +
                "body { font-family: Arial, sans-serif; }" +
                ".button { background-color: #FFC436; color: #191D88; padding: 10px 20px; text-decoration: none; border-radius: 5px; font-size: 16px; }" +
                "</style></head>" +
                "<body>" +
                "<h1>CoWorking Password Reset</h1>" +
                "<p>You have requested to reset your password. Please click the button below to proceed:</p>" +
                "<a href='" + resetLink + "' class='button'>Reset Password</a>" +
                "<p>If you did not request a password reset, please ignore this email.</p>" +
                "</body></html>";

        try {
            emailService.sendHtmlMessage(email, "Reset Your Password", htmlContent);
            return ResponseEntity.ok("Reset link sent to your email.");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email");
        }
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        PasswordResetToken resetToken = utilisateurService.validatePasswordResetToken(token);
        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return ResponseEntity.badRequest().body("Invalid or expired reset token.");
        }

        Utilisateur user = resetToken.getUser();
        if (user == null) {
            return ResponseEntity.badRequest().body("No user associated with this token.");
        }

        utilisateurService.updatePassword(user, newPassword);
        return ResponseEntity.ok("Password has been successfully reset.");
    }


}
