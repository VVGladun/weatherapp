package gladun.vlad.weather.util

import java.util.concurrent.atomic.AtomicBoolean

/**
 * An alternative to SingleLiveEvent for providing one-shot eventing.
 *
 * Can be used by wrapping an existing object with `Event(myObj)`,
 * or can be subclassed to make your class a one shot event or provide the event content via some other means.
 */
open class SingleEvent<out T>(private val eventContent: T? = null) {

    private val handled = AtomicBoolean(false)

    val hasBeenHandled
        get() = handled.get()

    @Suppress("UNCHECKED_CAST")
    protected open val content: T
        get() = eventContent
            ?: (this as? T)
            ?: throw IllegalArgumentException("Must provide `eventContent` via the constructor, override `content`, or be the event content")

    fun getContentIfNotHandled(): T? {
        return if (!handled.getAndSet(true)) content else null
    }

    fun getContentIfNotHandled(onEventUnhandledContent: (T) -> Unit) {
        getContentIfNotHandled()?.let { onEventUnhandledContent(it) }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent() = content
}