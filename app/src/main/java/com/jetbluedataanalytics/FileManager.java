package com.jetbluedataanalytics;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jetbluedataanalytics.data.JetBlueGraph;

import org.apache.commons.lang3.SerializationUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Bilawal on 11/7/2015.
 */
public class FileManager {

    private static final String FILE_NAME = "rawdata";

    public static JetBlueGraph read(Context context){
        InputStream fis = null;
        JetBlueGraph graph = null;
        try {
            fis = context.openFileInput("rawdata");
            graph = (JetBlueGraph) SerializationUtils.deserialize(fis);

        }catch(Exception e){
            //Toast.makeText(context, "ERROR READING FILE", Toast.LENGTH_SHORT).show();
            Log.d("MYDATA", "ERROR READING");


            return null;
        }

        Log.d("MYDATA", "Data Read: " + graph);
        return graph;
    }


    public static boolean write(Context context, JetBlueGraph g){

        FileOutputStream fos = null;
        try {
            //fos = new FileOutputStream("rawdata");
            fos = context.openFileOutput("rawdata", Context.MODE_PRIVATE);

        } catch (FileNotFoundException e) {
            Log.d("MYDATA", "ERROR WRITING1");
            return false;
        }

        byte[] bytes = SerializationUtils.serialize(g);


        try {
            fos.write(bytes);
            fos.close();
        }catch(IOException ex){
            Log.d("MYDATA", "ERROR WRITING");
            return false;
        }

        Log.d("MYDATA", "WROTE DATA");

        return true;

    }

}
