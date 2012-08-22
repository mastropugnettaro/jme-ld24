package com.teamjmonkey.file;

import com.jme3.asset.AssetInfo;
import com.jme3.asset.AssetKey;
import com.jme3.asset.AssetLoader;
import com.jme3.asset.AssetManager;
import com.jme3.system.JmeSystem;
import com.teamjmonkey.util.Manager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Read and write values stored on the database, including high scores and fastest times for each level
 *
 * @author Wesley Shillingford
 */
public class FileManager implements Manager {

    private String DATABASE_URL;
    private final String TABLE_NAME_LEVEL_STATS = "levelStats";
    private final String HIGHSCORE = "highscore";
    private final String COMPLETED = "completed";
    private final String TIME = "fastestTime";
    private final String TABLE_NAME_SETTINGS = "usersettings";
    private final String MUTE_SOUND_FX = "mutesoundfx";
    private final String MUTE_MUSIC = "mutemusic";
    private final String POST_PROCESSING = "postprocessing";
    private DbConnection db;
    private Connection connection;
    private AssetManager assetManager;

    public FileManager(AssetManager assetManager) {

        //IMPORTANT incorporate the UserSettings in here

        createSQLFile();

        try {
            connectToDatabase();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createSQLFile() {
        File storageFolder = JmeSystem.getStorageFolder();

        File settingsDirectory = new File(storageFolder, ".TeamJMonkey");
        if (!settingsDirectory.exists()) {
            if (settingsDirectory.mkdir()) {
                assetManager.registerLoader(DatabaseLoader.class, "db");

                String levelFilename = "Files/teamjmonkey.db";
                AssetKey myKey1 = new AssetKey(levelFilename);
                AssetInfo info1 = assetManager.locateAsset(myKey1);
                InputStream inputStream = (InputStream) info1.openStream();

                OutputStream outStream = null;

                String absolutePath = "";

                try {
                    File bfile = new File(settingsDirectory, "teamjmonkey.db");
                    absolutePath = bfile.getAbsolutePath();
                    outStream = new FileOutputStream(bfile);

                    byte[] buffer = new byte[1024];

                    int length;

                    //copy the file content in bytes
                    while ((length = inputStream.read(buffer)) > 0) {
                        outStream.write(buffer, 0, length);
                    }

                    inputStream.close();
                    outStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    DATABASE_URL = "jdbc:sqlite:" + absolutePath;
                }
            }
        }
    }

    public class DatabaseLoader implements AssetLoader {

        public InputStream load(AssetInfo assetInfo) throws IOException {
            return assetInfo.openStream();
        }
    }

    private void connectToDatabase() throws Exception {
        db = new DbConnection(DATABASE_URL);
        connection = db.getConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public int getHighScore(int level) {
        return (Integer) select(TABLE_NAME_LEVEL_STATS, HIGHSCORE, level);
    }

    public void setHighScore(int score, int level) {
        update(TABLE_NAME_LEVEL_STATS, HIGHSCORE, level, score);
        setCompleted(true, level);
    }

    public boolean getCompleted(int level) {
        return (Integer) select(TABLE_NAME_LEVEL_STATS, COMPLETED, level) == 1;
    }

    public void setCompleted(boolean value, int level) {
        update(TABLE_NAME_LEVEL_STATS, COMPLETED, level, value == true ? 1 : 0);
    }

    public int getFastestTime(int level) {
        return (Integer) select(TABLE_NAME_LEVEL_STATS, TIME, level);
    }

    public void setFastestTime(int seconds, int level) {
        update(TABLE_NAME_LEVEL_STATS, TIME, level, seconds);
        setCompleted(true, level);
    }

    public boolean getMuteSoundFX() {
        return (Integer) selectSettings(MUTE_SOUND_FX) == 1;
    }

    public void setMuteSoundFX(boolean value) {
        updateSettings(MUTE_SOUND_FX, value == true ? 1 : 0);
    }

    public boolean getMuteMusic() {
        return (Integer) selectSettings(MUTE_MUSIC) == 1;
    }

    public void setMuteMusic(boolean value) {
        updateSettings(MUTE_MUSIC, value == true ? 1 : 0);
    }

    public boolean getPostProcessing() {
        return (Integer) selectSettings(POST_PROCESSING) == 1;
    }

    public void setPostProcessing(boolean value) {
        updateSettings(POST_PROCESSING, value == true ? 1 : 0);
    }

    private Object selectSettings(String columnName) {
        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            String sql = "SELECT `" + columnName + "` FROM " + TABLE_NAME_SETTINGS + " LIMIT 1";
            stmt = connection.prepareStatement(sql);
            results = stmt.executeQuery();

            if (results.next()) {
                return results.getObject(columnName);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                stmt.close();
                results.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return null;
    }

    private void updateSettings(String columnName, Object value) {
        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE " + TABLE_NAME_SETTINGS + " SET `" + columnName + "` = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setObject(1, value);
            stmt.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    private void update(String tableName, String columnName, int level, Object value) {

        PreparedStatement stmt = null;

        try {
            String sql = "UPDATE " + TABLE_NAME_LEVEL_STATS + " SET `" + columnName + "` = ? WHERE `id` = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setObject(1, value);
            stmt.setInt(2, level);
            stmt.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                stmt.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
    }

    private Object select(String tableName, String columnName, int level) {

        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            String sql = "SELECT `" + columnName + "` FROM " + tableName + " WHERE `id` = ? LIMIT 1";
            stmt = connection.prepareStatement(sql);
            stmt.setObject(1, level);
            results = stmt.executeQuery();

            if (results.next()) {
                return results.getObject(columnName);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } finally {
            try {
                stmt.close();
                results.close();
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }
        return null;
    }

    public void load(int level) {
        //dunno what to load
    }

    public void cleanup() {
        // dunno what to clean up
    }
}