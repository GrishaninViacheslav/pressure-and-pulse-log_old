package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.log

import com.google.firebase.firestore.FirebaseFirestore
import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry
import io.github.grishaninvyacheslav.pressure_and_pulse_log.toLogEntity
import io.github.grishaninvyacheslav.pressure_and_pulse_log.toMap
import io.github.grishaninvyacheslav.pressure_and_pulse_log.models.guid.IGuidProvider


class LogRepository(private val db: FirebaseFirestore, private val guidProvider: IGuidProvider) :
    ILogRepository {
    private val collectionPath by lazy {
        guidProvider.getGUID()
    }

    private val sortComparator =
        Comparator<LogEntry> { a, b -> (a.timestamp - b.timestamp).toInt() }

    override fun getLog(
        onSuccessListener: (List<LogEntry>) -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    ) {
        db.collection(collectionPath)
            .get()
            .addOnSuccessListener { result ->
                List(result.size()) { index ->
                    result.documents[index].data!!.toLogEntity()
                }
                    .sortedWith(sortComparator)
                    .let { onSuccessListener(it) }
            }
            .addOnFailureListener { exception ->
                onFailureListener(exception)
            }
    }

    override fun addLogEntry(
        entry: LogEntry,
        onSuccessListener: () -> Unit,
        onFailureListener: (exception: Exception) -> Unit
    ) {
        db.collection(collectionPath)
            .add(entry.toMap())
            .addOnSuccessListener {
                onSuccessListener()
            }
            .addOnFailureListener { exception ->
                onFailureListener(exception)
            }
    }
}