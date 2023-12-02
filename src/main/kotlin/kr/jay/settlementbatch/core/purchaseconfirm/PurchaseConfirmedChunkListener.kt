package kr.jay.settlementbatch.core.purchaseconfirm

import net.gpedro.integrations.slack.SlackApi
import net.gpedro.integrations.slack.SlackMessage
import org.springframework.batch.core.ChunkListener
import org.springframework.batch.core.scope.context.ChunkContext

/**
 * PurchaseConfirmedChunkListener
 *
 * @author jaypark
 * @version 1.0.0
 * @since 12/2/23
 */
class PurchaseConfirmedChunkListener: ChunkListener {
    private val slackWebHookUrl = ""

    override fun beforeChunk(context: ChunkContext) {
        super.beforeChunk(context)
    }

    override fun afterChunk(context: ChunkContext) {
        super.afterChunk(context)
    }

    override fun afterChunkError(context: ChunkContext) {
        val api = SlackApi(this.slackWebHookUrl)
        api.call(SlackMessage("PurchaseConfirmedChunkListener::ERROR!!"))
        super.afterChunkError(context)
    }
}