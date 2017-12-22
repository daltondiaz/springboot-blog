package com.dalton.roles;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by dalton on 22/12/17.
 */

@Entity
@Table(name = "blog_role")
public class Role implements GrantedAuthority {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String description;
    @Column
    private String roleName;
    @Column
    private boolean status;

	@Override
	public String getAuthority() {
		return roleName;
    }
    


}