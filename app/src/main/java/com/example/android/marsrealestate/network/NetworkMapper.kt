package com.example.android.marsrealestate.network

import com.example.android.marsrealestate.model.Anime
import com.example.android.marsrealestate.model.AnimeNetworkEntity
import com.example.android.marsrealestate.model.AnimesNetworkEntity
import com.example.android.marsrealestate.utils.EntityMapper
import javax.inject.Inject

class NetworkMapper

@Inject
constructor() : EntityMapper<AnimeNetworkEntity, Anime> {

    override fun mapFromEntityToDm(entity: AnimeNetworkEntity): Anime {
        return Anime(
                id = entity.id,
                url = entity.url,
                image_url = entity.imageUrl,
                title = entity.title,
                airing = entity.airing,
                synopsis = entity.synopsis,
                type = entity.type,
                episode = entity.episode,
                rated = entity.rated,
                end_date = entity.endDate
        )
    }

    override fun mapToEntityFromDm(domainModel: Anime): AnimeNetworkEntity {
        return AnimeNetworkEntity(
                id = domainModel.id,
                url = domainModel.url,
                imageUrl = domainModel.image_url,
                title = domainModel.title,
                airing = domainModel.airing,
                synopsis = domainModel.synopsis,
                type = domainModel.type,
                episode = domainModel.episode,
                rated = domainModel.rated,
                endDate = domainModel.end_date
        )
    }

    fun mapFromEntityList(entities: AnimesNetworkEntity): List<Anime> {
        return entities.result.map  {
            mapFromEntityToDm(it)
        }
    }

}