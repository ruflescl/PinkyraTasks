package com.rafaellyra.pinkyratasks.data.model

data class AddressModel(val street: String,
                        val suite: String,
                        val city: String,
                        val zipcode: String,
                        val geo: GeoModel)