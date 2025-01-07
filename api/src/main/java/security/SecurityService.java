package security;

import io.github.api.domain.UserModel;
import io.github.api.service.UserModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UserModelService userModelService;

    public UserModel getUserLogged() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof CustomAuthentication customAuthentication){
            return customAuthentication.getUserModel();
        }

        return null;
    }
}
