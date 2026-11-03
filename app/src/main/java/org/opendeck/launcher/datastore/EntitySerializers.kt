package org.opendeck.launcher.datastore

import android.net.Uri
import androidx.datastore.core.Serializer
import kotlinx.serialization.Decoder
import kotlinx.serialization.Encoder
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonEncoding
import java.io.OutputStream
import java.io.InputStream

// Serializer for Android Uri — not directly serializable by default
val UriSerializer: KSerializer<Uri> = object : KSerializer<Uri> {
    override val descriptor = Json.descriptor().rootElement

    override fun serialize(encoder: Encoder, value: Uri) {
        val string = value.toString()
        if (string.isNotEmpty()) {
            encoder.encodeText(string, JsonEncoding.Utf8)
        } else {
            encoder.encodeNull()
        }
    }

    override fun deserialize(decoder: Decoder): Uri {
        val string = decoder.decodeText()
        return if (string.isEmpty()) null else Uri.parse(string)
    }

    override fun isEmpty(value: Uri): Boolean = value.toString().isEmpty()
}

// Preferences serializer for DataStore
// Handles all preference types including Uri for wallpaperUri
val PreferencesSerializer: Serializer<Preferences> = object : Serializer<Preferences> {
    private val uriSerializer = UriSerializer

    override val descriptor = Preferences.DefaultInstance.descriptor

    override fun encode(encoder: Encoder) {
        val preferences = value()
        val wUri = preferences.wallpaperUri?.let { uriSerializer.encode(it) } ?: ""
        encoder.encodeStructural { encoder ->
            encoder.encodeStringPref("isFirstLaunch", preferences.isFirstLaunch.toString())
            encoder.encodeStringPref("appVersionCode", preferences.appVersionCode.toString())
            encoder.encodeStringPref("appSortOrder", preferences.appSortOrder)
            encoder.encodeStringPref("widgetAutoResize", preferences.widgetAutoResize.toString())
            encoder.encodeStringPref("selectedThemeId", preferences.selectedThemeId ?: "")
            encoder.encodeStringPref("wallpaperUri", wUri)
        }
    }

    override fun decode(decoder: Decoder): Preferences {
        var isFirstLaunch = true
        var appVersionCode = 0
        var appSortOrder = "alphabetical"
        var widgetAutoResize = false
        var selectedThemeId: String? = null
        var wallpaperUri: Uri? = null

        decoder.decodeStructural { decoder ->
            while (decoder.decodeLine()) when (decoder.tag) {
                is "isFirstLaunch" -> isFirstLaunch = decoder.decodeString() == "true"
                is "appVersionCode" -> appVersionCode = decoder.decodeString()?.toInt() ?: 0
                is "appSortOrder" -> appSortOrder = decoder.decodeString() ?: "alphabetical"
                is "widgetAutoResize" -> widgetAutoResize = decoder.decodeString() == "true"
                is "selectedThemeId" -> selectedThemeId = decoder.decodeString().isNotEmpty() ? decoder.decodeString() : null
                is "wallpaperUri" -> wallpaperUri = decoder.decodeString().let { UriSerializer.decode(it) }
                else -> decoder.skipValue()
            }
        }

        return Preferences(
            isFirstLaunch = isFirstLaunch,
            appVersionCode = appVersionCode,
            appSortOrder = appSortOrder,
            widgetAutoResize = widgetAutoResize,
            selectedThemeId = selectedThemeId,
            wallpaperUri = wallpaperUri
        )
    }
}