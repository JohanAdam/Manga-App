package com.nyan.android.animeme.network

import com.nyan.android.animeme.model.Anime
import com.nyan.android.animeme.model.AnimeNetworkEntity
import com.nyan.android.animeme.model.AnimesNetworkEntity
import com.nyan.android.animeme.utils.EntityMapper
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

    fun mapResultFromEntityList(entities: AnimesNetworkEntity): List<Anime> {
        return entities.result.map  {
            mapFromEntityToDm(it)
        }
    }

    fun mapTopFromEntityList(entities: AnimesNetworkEntity): List<Anime> {
        return entities.top.map {
            mapFromEntityToDm(it)
        }
    }

}