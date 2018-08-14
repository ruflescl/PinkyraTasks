package com.rafaellyra.pinkyratasks.data.model

import com.rafaellyra.pinkyratasks.data.repository.api.IBaseEntity

data class UserModel(val id: Long,
                     val name: String,
                     val username: String,
                     val email: String,
                     val address: AddressModel,
                     val phone: String,
                     val website: String,
                     val company: CompanyModel): IBaseEntity {
    override fun id(): Long {
        return id
    }
}
