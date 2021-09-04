package com.example.kfsm

import org.springframework.hateoas.server.core.Relation

@Relation(itemRelation = "turnstile", collectionRelation = "turnstiles")
data class TurnstileData(
    val id: Long,
    val locked: Boolean,
    val message: String
) {
    val currentState: TurnstileState
        get() = if (locked) TurnstileState.LOCKED else TurnstileState.UNLOCKED
}