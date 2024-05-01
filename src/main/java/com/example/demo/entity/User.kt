package com.example.demo.entity

import com.example.demo.enums.Role
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


@Entity
@Table(name = "user", schema = "erp")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Int = 0,

    private val firstname: String? = null,

    private val lastname: String? = null,

    private val email: String? = null,

    private val password: String? = null,

    @Enumerated(EnumType.STRING)
    private val role: Role = Role.USER

) : UserDetails {

    private constructor(builder: Builder) : this(
        firstname = builder.firstname,
        lastname = builder.lastname,
        email = builder.email,
        password = builder.password,
        role = builder.role
    )

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return password ?: "no password"
    }

    override fun getUsername(): String {
        return email ?: "no username"
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    class Builder {

        var firstname: String? = null
            private set

        var lastname: String? = null
            private set

        var email: String? = null
            private set

        var password: String? = null
            private set

        var role: Role = Role.USER
            private set

        fun firstname(firstname: String?) = apply { this.firstname = firstname }

        fun lastname(lastname: String?) = apply { this.lastname = lastname }

        fun email(email: String?) = apply { this.email = email }

        fun password(password: String?) = apply { this.password = password }

        fun role(role: Role) = apply { this.role = role }

        fun build() = User(this)
    }
}
