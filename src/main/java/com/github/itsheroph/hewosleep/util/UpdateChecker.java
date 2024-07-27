package com.github.itsheroph.hewosleep.util;

import com.github.itsheroph.hewosleep.HewoSleep;
import com.github.itsheroph.hewosleep.util.version.Version;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UpdateChecker {

    private static final String REPO = "ItsHeroPH/HewoSleep";

    private static final String LATEST_VERSION_URL = "https://api.github.com/repos/" + REPO + "/releases/latest";

    public static final String RELEASES_URL = "https://github.com/ItsHeroPH/HewoSleep/releases";

    private final HewoSleep plugin;
    private final String currentVersion;

    public UpdateChecker(HewoSleep plugin) {

        this.plugin = plugin;
        this.currentVersion = plugin.getDescription().getVersion();

    }

    public boolean isLatestVersion() {

        Version newVersion = this.parseLatestVersion();
        Version version = new Version(this.plugin, this.currentVersion);

        if (newVersion.getMajor() > version.getMajor()) {

            return false;

        }

        if (newVersion.getMajor() < version.getMajor()) {

            return true;

        }

        if (newVersion.getMinor() > version.getMinor()) {

            return false;

        }

        if (newVersion.getMinor() < version.getMinor()) {

            return true;

        }

        return newVersion.getPatch() <= version.getPatch();

    }

    public Version parseLatestVersion() {

        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(LATEST_VERSION_URL).openConnection();
            connection.connect();

            if (connection.getResponseCode() == HttpURLConnection.HTTP_INTERNAL_ERROR || connection.getResponseCode() == HttpURLConnection.HTTP_FORBIDDEN) new Version(this.plugin, this.currentVersion);

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
            String latestRelease = new Gson().fromJson(reader, JsonObject.class).get("tag_name").getAsString();

            return new Version(this.plugin, latestRelease);

        } catch (IOException e) {

            this.plugin.getPluginLogger().error(e.getMessage());
            return new Version(this.plugin, this.currentVersion);

        }

    }
}
