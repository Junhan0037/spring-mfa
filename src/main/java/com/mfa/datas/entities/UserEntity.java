package com.mfa.datas.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Entity
@ToString
@EqualsAndHashCode(callSuper = false)
@Table(name = "users", schema = "mfa")
@NoArgsConstructor
public class UserEntity implements Serializable {

    @Id
    @Column(nullable = false)
    private long id;

    @Column(length = 50)
    private String username;

    @Column(length = 512)
    private String password;

    @Column(length = 1000)
    private String roles;

    @Builder
    public UserEntity(long id, String username, String password, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

}
