package com.ivchern.exchange_employers.Security.Services;

import com.ivchern.exchange_employers.Model.User.ERole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Principal;

@Service
public class SecurityService {
    private final UserDetailsService userDetailsService;

    public SecurityService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
    public boolean isOwner(Long ownerId, Principal principal){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(authentication.getName());
        if (userDetails instanceof UserDetailsImpl) {
            UserDetailsImpl customUserDetails = (UserDetailsImpl) userDetails;
            Long userId = customUserDetails.getId();

            if(userId == ownerId || userDetails.getAuthorities().contains(ERole.ROLE_ADMIN.toString()) ||
                                    userDetails.getAuthorities().contains(ERole.ROLE_ADMIN.toString())) {
                return true;
            }
        }
        return false;
    }


}
