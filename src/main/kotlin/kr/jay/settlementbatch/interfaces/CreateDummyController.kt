package kr.jay.settlementbatch.interfaces

import kr.jay.settlementbatch.application.DummyFacade
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * CreateDummyController
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/12/23
 */

@RestController
@RequestMapping("/test/dummy")
class CreateDummyController(
    private val dummyFacade: DummyFacade
) {

    @PostMapping("/order")
    fun create(){
        dummyFacade.createOrder()
    }
}