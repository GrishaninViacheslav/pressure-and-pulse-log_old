package io.github.grishaninvyacheslav.pressure_and_pulse_log.models.clipboard

import java.lang.RuntimeException

class UnavailableClipboardManager: RuntimeException("Сервис буфера обмена не доступен")