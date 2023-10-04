package food.system.role;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public enum Role {
    ADMIN(List.of(
            Permission.ADMIN_CREATE,
            Permission.ADMIN_READ,
            Permission.ADMIN_UPDATE,
            Permission.ADMIN_DELETE
    ));
    private final List<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities(){
        List<SimpleGrantedAuthority> simpleGrantedAuthorities
                = new ArrayList<>(List.of(new SimpleGrantedAuthority("ROLE_".concat(this.name()))));

        simpleGrantedAuthorities.addAll(
                permissions.stream().map(
                        p -> new SimpleGrantedAuthority(p.name())
                ).toList()
        );
        return simpleGrantedAuthorities;
    }

}
