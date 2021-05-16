package com.mmichalec.allegroRecruitmentTask.util
/*
Sealed Class - object can only be of one of given types
In opposition to enum we can have multiple objects of the same class
Sealed classes are designed to be used when there are a very specific set of possible options

Generic type class that contains data and status about loading this data
Wraps data itself - contains data and loading state of network. It informs if we need to  display ERROR message or loading bar.
 */

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null // better to use throwable because I can extract data (message) from it.
) {
    //subclasses of the resource
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(throwable: Throwable, data: T? = null) : Resource<T>(data, throwable)
}

//values of this classes are emitted by NetworkBoundResources