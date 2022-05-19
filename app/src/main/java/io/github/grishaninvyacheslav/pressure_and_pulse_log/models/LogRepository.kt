package io.github.grishaninvyacheslav.pressure_and_pulse_log.models

import com.google.firebase.firestore.FirebaseFirestore
import io.github.grishaninvyacheslav.pressure_and_pulse_log.entities.LogEntry
import io.github.grishaninvyacheslav.pressure_and_pulse_log.toLogEntity
import io.github.grishaninvyacheslav.pressure_and_pulse_log.toMap

class LogRepository(private val db: FirebaseFirestore) : ILogRepository {
    private val collectionPath = "pressure-and-pulse-log"

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