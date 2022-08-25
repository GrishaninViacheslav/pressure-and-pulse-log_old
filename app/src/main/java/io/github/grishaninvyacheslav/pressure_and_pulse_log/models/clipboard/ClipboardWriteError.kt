package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.clipboard

import java.lang.RuntimeException

class ClipboardWriteError: RuntimeException("Не удалось скопировать в буфер")