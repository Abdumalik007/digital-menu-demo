package food.system.role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_CREATE("admin:create"),
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_DELETE("admin:delete"),
    WAITER_CREATE("waiter:create"),
    WAITER_READ("waiter:read"),
    WAITER_UPDATE("waiter:update"),
    WAITER_DELETE("waiter:delete");

    private final String permission;
}
