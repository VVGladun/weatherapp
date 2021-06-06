package gladun.vlad.weather.data.network

abstract class KnownException(message: String, cause: Throwable?) :
    RuntimeException(message, cause) {
}

class InvalidDataException(message: String, cause: Throwable? = null): KnownException(message, cause)

//TODO: other types of error - for 500, for 404, unauth etc

class UnknownException(cause: Throwable): KnownException(cause.message ?: cause.javaClass.simpleName, cause)