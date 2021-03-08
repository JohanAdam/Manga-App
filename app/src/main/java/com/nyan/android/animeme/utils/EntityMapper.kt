package com.nyan.android.animeme.utils

interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntityToDm(entity: Entity): DomainModel

    fun mapToEntityFromDm(domainModel: DomainModel): Entity

}