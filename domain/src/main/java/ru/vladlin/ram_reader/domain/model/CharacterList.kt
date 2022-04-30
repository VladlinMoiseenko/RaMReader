package ru.vladlin.ram_reader.domain.model

data class CharacterList(
    val info: Info,
    val results: List<CharacterModel>
)

data class Info(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)