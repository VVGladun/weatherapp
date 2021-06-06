package gladun.vlad.weather.util

import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

private val FULL_DATE_FORMAT = DateTimeFormatter.ofPattern("HH:mm a dd MMMM yyyy", Locale.ENGLISH)

fun ZonedDateTime.toFullDateString(): String {
    return FULL_DATE_FORMAT.format(this)
}