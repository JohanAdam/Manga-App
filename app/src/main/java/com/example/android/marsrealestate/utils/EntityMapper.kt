package com.example.android.marsrealestate.utils

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntityToDm(entity: Entity): DomainModel

    fun mapToEntityFromDm(domainModel: DomainModel): Entity

}