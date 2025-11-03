package com.example.swproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements UserDetails {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "users_id", nullable = false)
  private Long id;

  @Size(max = 225)
  @NotNull
  @Column(name = "login_id", nullable = false, length = 225)
  private String loginId;

  @Size(max = 225)
  @NotNull
  @Column(name = "login_pw", nullable = false, length = 225)
  private String loginPw;

  @Size(max = 225)
  @NotNull
  @Column(name = "name", nullable = false, length = 225)
  private String name;

  @Size(max = 30)
  @NotNull
  @Column(name = "email", nullable = false, length = 30)
  private String email;

  @Size(max = 30)
  @NotNull
  @Column(name = "country", nullable = false, length = 30)
  private String country;

  @Size(max = 30)
  @Column(name = "religion", length = 30)
  private String religion;

  @OneToMany(mappedBy="user")
  List<UsersBadge> badges = new ArrayList<UsersBadge>();

  @OneToMany(mappedBy="user")
  List<Review> reviews=new ArrayList<Review>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
  }

  @Override
  public String getPassword() {
    return this.loginPw;
  }

  @Override
  public String getUsername() {
    return this.loginId;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true; // 계정 만료되지 않음
  }

  @Override
  public boolean isAccountNonLocked() {
    return true; // 계정 잠기지 않음
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true; // 비밀번호 만료되지 않음
  }

  @Override
  public boolean isEnabled() {
    return true; // 계정 활성화됨
  }
}