package com.rafaellyra.pinkyratasks.data.model

data class UserModel(val id: Long,
                     val name: String,
                     val username: String,
                     val email: String,
                     val address: AddressModel,
                     val phone: String,
                     val website: String,
                     val company: CompanyModel,
                     var tasks: List<TaskModel> = ArrayList<TaskModel>()){
}
