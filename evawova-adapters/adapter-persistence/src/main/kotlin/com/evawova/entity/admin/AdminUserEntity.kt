package com.evawova.entity.admin

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "admin_user")
class AdminUserEntity(
    email: String,
    password: String,
    name: String,
    role: String,
    status: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Comment("이메일")
    @Column(nullable = false, unique = true)
    var email: String = email
        protected set

    @Comment("비밀번호")
    @Column
    var password: String = password
        protected set

    @Comment("이름")
    @Column
    var name: String = name
        protected set

    @Comment("권한")
    @Column
    var role: String = role
        protected set

    @Comment("상태")
    @Column
    var status: String = status
        protected set
}
