package com.example.kfsm

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.stereotype.Component
import javax.xml.bind.annotation.XmlRootElement

@XmlRootElement
class TurnstileResource(data: TurnstileData, vararg links: Link) : EntityModel<TurnstileData>(data, *links)

@Component
class TurnstileResourceAssembler : RepresentationModelAssemblerSupport<TurnstileData, TurnstileResource>(
    TurnstileController::class.java,
    TurnstileResource::class.java
) {
    override fun toModel(entity: TurnstileData): TurnstileResource {
        val links = makelinks(entity)
        return TurnstileResource(entity, *links)
    }

    fun toCollection(entities: Iterable<TurnstileData>): CollectionModel<TurnstileResource> {
        val result = CollectionModel(entities.map { toModel(it) })
        result.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TurnstileController::class.java).list()).withSelfRel())
        return result
    }

    private fun makelinks(entity: TurnstileData): Array<Link> {
        val links = mutableListOf(
            WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TurnstileController::class.java).get(entity.id)).withSelfRel()
        )
        links += TurnstileService.possibleEvents(entity).map { event ->
            WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(TurnstileController::class.java).event(entity.id, event)
            ).withRel(event)
        }
        return links.toTypedArray()
    }
}