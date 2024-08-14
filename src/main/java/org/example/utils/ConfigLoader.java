package org.example.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Optional;

public class ConfigLoader {
    private static final Logger logger = LoggerFactory.getLogger(ConfigLoader.class);
    private static final Properties props = new Properties();

    static {
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                logger.error("Unable to find config.properties");
                throw new RuntimeException("Configuration file not found.");
            }
            props.load(input);
        } catch (IOException ex) {
            logger.error("Error loading properties file", ex);
            throw new RuntimeException("Error loading configuration", ex);
        }
    }

    public static String getApiUrl() {
        return Optional.ofNullable(props.getProperty("list.api.base.url"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getApiKey() {
        return Optional.ofNullable(props.getProperty("X.API.Key"))
                .orElseThrow(() -> new RuntimeException("API key not found in config.properties"));
    }

    public static String getActionApiUrl() {
        return Optional.ofNullable(props.getProperty("Action.api.url"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getActionName() {
        return Optional.ofNullable(props.getProperty("Action.actionName"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getConnetionBaseUrl() {
        return Optional.ofNullable(props.getProperty("Connection.api.base.url"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getConnetionRedirectUrl() {
        return Optional.ofNullable(props.getProperty("Connection.redirect.url"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getConnetionIntegrationId() {
        return Optional.ofNullable(props.getProperty("Connection.integration.id"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getConnectionListBaseUrl() {
        return Optional.ofNullable(props.getProperty("Connection.list.baseUrl"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getConnectedBaseUrl() {
        return Optional.ofNullable(props.getProperty("Connection.connected.baseUrl"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getConnectedAccountId() {
        return Optional.ofNullable(props.getProperty("Connection.connected.account.id"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getIntegrationBaseUrl() {
        return Optional.ofNullable(props.getProperty("Integration.get.integration.BaseUrl"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getIntegrationId() {
        return Optional.ofNullable(props.getProperty("Integration.get.integration.Id"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getActionGitHubApiRootPoint() {
        return Optional.ofNullable(props.getProperty("Action.Git.Hub.Api.RootPoint"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getActionConnectedAccountId() {
        return Optional.ofNullable(props.getProperty("Action.Connected.AccountId"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }

    public static String getAppName() {
        return Optional.ofNullable(props.getProperty("Action.App.Name"))
                .orElseThrow(() -> new RuntimeException("API base URL not found in config.properties"));
    }


}
