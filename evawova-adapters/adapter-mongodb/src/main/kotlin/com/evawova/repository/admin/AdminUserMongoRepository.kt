package com.evawova.repository.admin

import org.springframework.data.mongodb.repository.MongoRepository

interface AdminUserMongoRepository : MongoRepository<Product, String>
