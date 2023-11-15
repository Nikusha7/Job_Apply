package ge.nika.job_apply.util;

/**
 * Represents the database connection properties, including the URL, username, and password.
 * This class provides a convenient way to encapsulate and access these properties.
 */
public final class DatabaseProperties {
    private final String url;
    private final String username;
    private final String password;

    /**
     * Constructs a DatabaseProperties object with the specified URL, username, and password.
     *
     * @param url      the URL of the database
     * @param username the username for database connection
     * @param password the password for database connection
     */
    public DatabaseProperties(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the URL of the database.
     *
     * @return the database URL
     */
    public String getUrl() {
        return url;
    }


    /**
     * Gets the username for database connection.
     *
     * @return the database username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the password for database connection.
     *
     * @return the database password
     */
    public String getPassword() {
        return password;
    }

}