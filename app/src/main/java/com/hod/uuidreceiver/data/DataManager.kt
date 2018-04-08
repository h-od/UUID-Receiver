package com.hod.uuidreceiver.data

import com.hod.uuidreceiver.BuildConfig
import io.reactivex.Observable
import io.reactivex.Single

class DataManager(private val local: Local, private val network: Remote): DataContract {

    override fun fetch(): Observable<Entity> =
            network.fetch()
                    .map { it.nextPath.removePrefix(BuildConfig.BASE_URL) }
                    .flatMap { network.fetch(it) }
                    .map { local.put(it.responseCode) }

    override fun fetchPrevious(): Single<Entity> {
        val entity = local.fetch()
        return Single.just(Entity(responseCode = entity.responseCode, count = entity.count))
    }
}
