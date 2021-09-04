package com.example.kfsm

import org.springframework.hateoas.CollectionModel
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/turnstile", produces = [MediaType.APPLICATION_JSON_VALUE])
class TurnstileController(
    private val modelAssembler: TurnstileResourceAssembler,
    private val turnstileService: TurnstileService
) {
    @GetMapping()
    fun list(): ResponseEntity<CollectionModel<TurnstileResource>> {
        return ResponseEntity.ok(modelAssembler.toCollection(turnstileService.list()))
    }

    @PostMapping()
    fun create(): ResponseEntity<TurnstileResource> {
        return ResponseEntity.ok(modelAssembler.toModel(turnstileService.create()))
    }

    @GetMapping("{id}")
    fun get(@PathVariable("id") id: Long): ResponseEntity<TurnstileResource> {
        return ResponseEntity.ok(modelAssembler.toModel(turnstileService.get(id)))
    }

    @PostMapping("{id}/{event}")
    fun event(@PathVariable("id") id: Long, @PathVariable("event") event: String): ResponseEntity<TurnstileResource> {
        return ResponseEntity.ok(modelAssembler.toModel(turnstileService.event(id, event)))
    }
}
