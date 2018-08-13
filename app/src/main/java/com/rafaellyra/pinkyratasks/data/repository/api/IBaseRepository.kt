package com.rafaellyra.pinkyratasks.data.repository.api

interface IBaseRepository<T: IBaseEntity> {
    fun get(id: Long)
    fun list()
    fun persist(item: T)
    fun delete(id: Long)
}