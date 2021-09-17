package dev.vladimirj.tidal.search.domain

sealed class DomainResult {
    data class Success<T>(val data: List<T>, val totalSize: Int, val next: String?) : DomainResult()
    data class Error(val message: String) : DomainResult()
}