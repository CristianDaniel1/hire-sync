package com.syncteam.hiresync.security;

import com.syncteam.hiresync.entities.AppUser;
import com.syncteam.hiresync.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    public AppUser getLoggedUserOrThrow() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof CustomAuthentication customAuth))
            throw new AccessDeniedException("User is not authenticated");

        return customAuth.getUser();
    }
}
