package com.example.subpay.batch

import com.example.subpay.domain.Subscription
import com.example.subpay.repository.SubscriptionRepository
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.Sort
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class SubscriptionStatusBatchJob(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager
) {

    @Bean
    fun subscriptionStatusJob(subscriptionStatusStep: Step): Job =
        JobBuilder(JOB_NAME, jobRepository)
            .start(subscriptionStatusStep)
            .build()

    @Bean
    fun subscriptionStatusStep(
        subscriptionRepository: SubscriptionRepository,
    ): Step =
        StepBuilder(STEP_NAME, jobRepository)
            .chunk<Subscription, Subscription>(CHUNK_SIZE, transactionManager)
            .reader(subscriptionStatusReader(subscriptionRepository))
            .processor(subscriptionStatusProcessor())
            .writer(subscriptionStatusWriter(subscriptionRepository))
            .build()

    @Bean
    @StepScope
    fun subscriptionStatusReader(
        subscriptionRepository: SubscriptionRepository
    ): ItemReader<Subscription> =
        RepositoryItemReaderBuilder<Subscription>()
            .name("subscriptionStatusReader")
            .repository(subscriptionRepository)
            .methodName("findAll")
            .pageSize(CHUNK_SIZE)
            .sorts(mapOf("id" to Sort.Direction.ASC))
            .build()

    @Bean
    @StepScope
    fun subscriptionStatusProcessor(
        @Value("#{jobParameters['checkTime']}") checkTime: String? = null
    ): ItemProcessor<Subscription, Subscription> =
        ItemProcessor { subscription ->
            val now = if (checkTime != null) {
                LocalDateTime.parse(checkTime, DateTimeFormatter.ofPattern("yyyy-MM-dd-HH"))
            } else {
                LocalDateTime.now()
            }
            if (subscription.subscriptionStatus == Subscription.SubscriptionStatus.ACTIVE &&
                subscription.subscriptionExpirationDate.isBefore(now)) {
                subscription.subscriptionStatus = Subscription.SubscriptionStatus.INACTIVE
                subscription
            } else {
                null
            }
        }

    @Bean
    @StepScope
    fun subscriptionStatusWriter(
        subscriptionRepository: SubscriptionRepository,
        @Value("#{jobParameters['dryRun']}") dryRun: Boolean? = false,
    ): ItemWriter<Subscription> {
        return ItemWriter { subscriptions ->
            if (dryRun == true) {
                println("[DRY RUN] ${subscriptions.size()}개의 구독 상태가 업데이트 될 예정입니다.")
                subscriptions.forEach {
                    println("→ ${it.id} 상태 변경 예정: ${it.subscriptionStatus}")
                }
            } else {
                println("[REAL RUN] ${subscriptions.size()}개의 구독 상태를 저장합니다.")
                subscriptionRepository.saveAll(subscriptions)
            }
        }
    }

    companion object {
        const val JOB_NAME = "subscriptionStatusJob"
        const val STEP_NAME = "subscriptionStatusStep"
        const val CHUNK_SIZE = 100
    }
}
