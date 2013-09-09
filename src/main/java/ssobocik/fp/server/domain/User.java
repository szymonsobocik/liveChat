package ssobocik.fp.server.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Represents user in database
 *
 * @author szymon.sobocik
 */
@Entity
@Table(name = "FPUser")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @NotNull(message = "Username should not be null")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "password")
    private String password;

    /**
     * Simple fast workaround instead of spring security roles
     */
    @Column(name = "isAdmin")
    private boolean admin;

    @Version
    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
