package kr.jay.settlementbatch.interfaces

import kr.jay.settlementbatch.application.ClaimCompleteFacade
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * ClaimController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 11/17/23
 */

@RestController
@RequestMapping("/claim")
class ClaimController(
    private val facade: ClaimCompleteFacade
) {
    @GetMapping("/complete/{id}")
    fun completeClaim(@PathVariable id: Long): ResponseEntity.HeadersBuilder<*> {
        facade.receiptComplete(id)
        return ResponseEntity.noContent()
    }
}