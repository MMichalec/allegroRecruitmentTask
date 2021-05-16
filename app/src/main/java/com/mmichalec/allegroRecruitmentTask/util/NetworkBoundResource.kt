package com.mmichalec.allegroRecruitmentTask.util

import kotlinx.coroutines.flow.*
/*
Class that coordinates caching process. Decides when to update db from API
ResultType and RequestType are needed because not always you are recieving same data from API that is in DB

Inline is recommended with higher-order-functions to speed up app.
Compilator does not keep the pointer to the inline function, it's copy-pasting a whole function

Crossline marker is used to mark lambdas that musn't allow non-local returns.
 */

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
//flow builder executed every time NBR is called
) = flow {
    val data = query().first()
//flow val stores the value of each query().map so it can be emited later.
    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))
//fetch() can fail so we need to wrap in in try block
        try {
            saveFetchResult(fetch())
            //emiting data throgh NBR wraped in Resource class
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            //emiting old cached data from DB if something goes wrong. We still want to get DB updates.
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        //if we don't need to fetch data
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}
