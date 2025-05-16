package com.example.subpay.batch

import com.example.subpay.domain.Subscription
import com.example.subpay.repository.SubscriptionRepository
import com.example.subpay.service.PayService
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
import java.time.LocalDate

@Configuration
class SubscriptionPayBatchJob(
    private val jobRepository: JobRepository,
    private val transactionManager: PlatformTransactionManager,
    private val payService: PayService,
) {

    @Bean
    fun subscriptionPayJob(subscriptionPayStep: Step): Job {
        return JobBuilder(JOB_NAME, jobRepository)
            .start(subscriptionPayStep)
            .build()
    }

    @Bean
    fun subscriptionPayStep(
        subscriptionRepository: SubscriptionRepository
    ): Step =
        StepBuilder(STEP_NAME, jobRepository)
            .chunk<Subscription, Subscription>(CHUNK_SIZE, transactionManager)
            .reader(reader(subscriptionRepository))
            .processor(processor())
            .writer(writer())
            .build()

    @Bean
    @StepScope
    fun reader(
        subscriptionRepository: SubscriptionRepository
    ): ItemReader<Subscription> =
        RepositoryItemReaderBuilder<Subscription>()
            .name("subscriptionPayReader")
            .repository(subscriptionRepository)
            .methodName("findAll")
            .pageSize(CHUNK_SIZE)
            .sorts(mapOf("id" to Sort.Direction.ASC))
            .build()

    @Bean
    @StepScope
    fun processor(): ItemProcessor<Subscription, Subscription> =
        ItemProcessor { subscription ->
            val today = LocalDate.now() // TODO : 일배치 시점에 맞게 변경
            subscription.takeIf { it.isTodayInCycle(today) }
        }

    @Bean
    @StepScope
    fun writer(
        @Value("#{jobParameters['dryRun']}") dryRun: Boolean? = false,
    ): ItemWriter<Subscription> =
        ItemWriter { items ->
            if (dryRun == true) {
                println("[DRY RUN] 결제가 수행될 구독 목록: $items")
                return@ItemWriter
            }
            items.forEach { subscription ->
                val paymentHistory = payService.pay(subscription)
                println("Payment history: $paymentHistory")
            }
        }

    companion object {
        const val JOB_NAME = "subscriptionPayJob"
        const val STEP_NAME = "subscriptionPayStep"
        const val CHUNK_SIZE = 100
    }
}
