package com.evawova.entity.admin

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import org.springframework.data.domain.AbstractAggregateRoot

@Entity
@Table(name = "admin_user")
class AdminUserEntity(
    @Comment("PK")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = 0,
    @Comment("이메일")
    @Column(nullable = false, unique = true)
    var email: String,
    @Comment("비밀번호")
    @Column
    var password: String,
    @Comment("이름")
    @Column
    var name: String,
    @Comment("권한")
    @Column
    var role: String,
    @Comment("상태")
    @Column
    var status: String,
) : AbstractAggregateRoot<AdminUserEntity>() {
    constructor(email: String, password: String, name: String, role: String, status: String) : this(
        id = null,
        email = email,
        password = password,
        name = name,
        role = role,
        status = status,
    ) {
        registerEvent(AdminUserCreatedEvent(this))
    }
}
