package com.evawova.repository.admin

import com.evawova.entity.admin.AdminUserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AdminUserJpaRepository : JpaRepository<AdminUserEntity, Long>
