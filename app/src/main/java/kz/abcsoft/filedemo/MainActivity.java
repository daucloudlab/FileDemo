package kz.abcsoft.filedemo;

import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = "myLogs" ;

    private final String FILENAME = "file" ;

    private final String DIR_SD = "MyFiles" ;
    private final String FILENAME_SD = "fileSD" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnWrite:
                writeFile() ;
                break ;
            case R.id.btnRead:
                readFile() ;
                break;
            case R.id.btnWriteSD:
                writeFileSD() ;
                break;
            case R.id.btnReadSD:
                readFileSD() ;
                break ;
        }
    }

    public void writeFile(){
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                openFileOutput(FILENAME, MODE_PRIVATE))) ;
            bw.write("Содержимое файла");
            bw.close();
            Log.d(LOG_TAG, "файл записан") ;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void readFile(){
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    openFileInput(FILENAME)));
            String str = "" ;
            while( (str = br.readLine()) != null){
                Log.d(LOG_TAG, str) ;
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeFileSD(){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState()) ;
            return ;
        }

        File sdPath = Environment.getExternalStorageDirectory() ;
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD) ;
        sdPath.mkdirs() ;

        File sdFile = new File(sdPath, FILENAME_SD) ;

        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(sdFile)) ;
            bw.write("Содержимое файла на sd");
            bw.close() ;
            Log.d(LOG_TAG, "Файл записан на SD: " + sdFile.getAbsolutePath()) ;
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void readFileSD(){
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.d(LOG_TAG, "SD карта не доступна: " + Environment.getExternalStorageState()) ;
            return ;
        }

        File sdPath = Environment.getExternalStorageDirectory() ;
        sdPath = new File(sdPath.getAbsolutePath() + "/" + DIR_SD) ;
        File sdFile = new File(sdPath, FILENAME_SD) ;

        try{
            BufferedReader br = new BufferedReader(new FileReader(sdFile)) ;
            String str = "" ;

            while((str = br.readLine()) != null){
                Log.d(LOG_TAG, str) ;
            }

        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
