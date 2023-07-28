package erika.core.linq

import kotlinx.datetime.Instant
import org.junit.Test

class InstantTest {
    @Test
    fun test_Instant() {
        val s = Instant.parse("2000-10-31T01:30:00.000-05:00")
        print(s)
    }
}