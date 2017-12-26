package com.dalton.user;

import com.dalton.roles.Role;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Created by dalton on 22/12/17.
 */

@Entity
@Table(name = "blog_user")
public class User implements UserDetails{
    
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private boolean enabled = true;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonIgnore
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Timestamp getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles;
	}
	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

}