package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log

import com.google.firebase.firestore.FirebaseFirestore
import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry
import io.github.grishaninvyacheslav.pressure_and_pulse_log.toLogEntity
import io.github.grishaninvyacheslav.pressure_and_pulse_log.toMap
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.guid.IDeviceGuidProvider
import kotlinx.coroutines.tasks.await

class LogRepository(
    private val db: FirebaseFirestore,
    private val deviceGuidProvider: IDeviceGuidProvider
) :
    ILogRepository {
    private suspend fun getCollectionPath(): String{
        return deviceGuidProvider.getDeviceGUID()
    }

    private val sortComparator =
        Comparator<LogEntry> { a, b -> (a.timestamp - b.timestamp).toInt() }

    override suspend fun getLog(): List<LogEntry> {
        val result = db.collection(getCollectionPath()).get().await()
        return List(result.size()) { index ->
            result.documents[index].data!!.toLogEntity()
        }.sortedWith(sortComparator)
    }

    override suspend fun addLogEntry(
        entry: LogEntry
    ) {
        db.collection(getCollectionPath()).add(entry.toMap()).await()
    }
}