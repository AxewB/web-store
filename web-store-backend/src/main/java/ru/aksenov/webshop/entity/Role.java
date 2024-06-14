package ru.aksenov.webshop.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "user_role")
@Data
@EqualsAndHashCode(of = {"id"})

public class Role implements GrantedAuthority {
    @Id
    private long id;
    private String role_name;

    @Override
    public String getAuthority() {
        return Long.toString(this.id);
    }
}
