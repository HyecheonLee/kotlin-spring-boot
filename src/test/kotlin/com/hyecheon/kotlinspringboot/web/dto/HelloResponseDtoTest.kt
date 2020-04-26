package com.hyecheon.kotlinspringboot.web.dto

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class HelloResponseDtoTest {
    @Test
    internal fun `data 클래스 테스트`() {
        val name = "test"
        val amount = 1000
        val dto = HelloResponseDto(name, amount)

        assertThat(dto.name).isEqualTo(name)
        assertThat(dto.amount).isEqualTo(amount)
    }
}
