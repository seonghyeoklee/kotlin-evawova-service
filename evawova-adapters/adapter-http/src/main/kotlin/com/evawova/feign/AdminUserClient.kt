package com.evawova.feign

import org.springframework.cloud.openfeign.FeignClient

@FeignClient(name = "EVAWOVA-APP-LOGGER-SERVICE")
interface AdminUserClient
