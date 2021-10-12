package com.github.lernejo.front.user;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
record LoginController(SessionRepository sessionRepository) {

    @PostMapping("/login")
    UserInfo login(@RequestParam String username, @RequestParam String password) {
        UserSession userSession = sessionRepository.create(username);
        SessionContext.set(userSession);
        return new UserInfo(username, userSession.getId());
    }

    @GetMapping("/logout")
    void logout() {
        sessionRepository.destroy(SessionContext.clear().getId());
    }
}
