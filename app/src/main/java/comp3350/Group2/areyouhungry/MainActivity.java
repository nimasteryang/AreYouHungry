package comp3350.Group2.areyouhungry;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import comp3350.Group2.areyouhungry.persistence.DataAccessStub;
import comp3350.Group2.areyouhungry.persistence.Messages;
import comp3350.Group2.areyouhungry.ui.home.HomeActivity;

public class MainActivity extends AppCompatActivity {
    public static final String dbName="SC";
    private static String dbPathName = "database/SC";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        /* Startup database. */
        copyDatabaseToDevice();
        startUp();

        /* StartUp home activity. */
        Intent home_intent = new Intent(MainActivity.this, HomeActivity.class);
        MainActivity.this.startActivity(home_intent);
        finish();
    }

    private void copyDatabaseToDevice(){
        final String DB_PATH = "db";

        String[] assetNames;
        Context context = getApplicationContext();
        File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
        AssetManager assetManager = getAssets();

        try{
            assetNames = assetManager.list(DB_PATH);
            for (int i = 0; i < assetNames.length; i++){
                assetNames[i] = DB_PATH + "/" + assetNames[i];
            }
            copyAssetsToDirectory(assetNames, dataDirectory);
            MainActivity.setDBPathName(dataDirectory.toString() + "/" + MainActivity.dbName);
        } catch (IOException ioe){
            Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
        }
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException{
        AssetManager assetManager = getAssets();

        for (String asset : assets){
            String[] components = asset.split("/");
            String copyPath = directory.toString() + "/" + components[components.length - 1];
            char[] buffer = new char[1024];
            int count;

            File outFile = new File(copyPath);

            if (!outFile.exists()){
                InputStreamReader in = new InputStreamReader(assetManager.open(asset));
                FileWriter out = new FileWriter(outFile);

                count = in.read(buffer);
                while (count != -1){
                    out.write(buffer, 0, count);
                    count = in.read(buffer);
                }

                out.close();
                in.close();
            }
        }
    }

    private void startUp(){
        Services.createDataAccess(dbName);
    }

    public static String getDBPathName(){
        if (dbPathName == null)
            return dbName;
        else
            return dbPathName;
    }

    public static void setDBPathName(String pathName){
        System.out.println("Setting DB path to: " + pathName);
        dbPathName = pathName;
    }
}