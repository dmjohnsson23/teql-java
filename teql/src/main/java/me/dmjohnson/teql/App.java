package me.dmjohnson.teql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.lang3.tuple.Pair;

public class App{
    public static void main( String[] args ){
        Context context;
        try {
            context = new FileContext(new FileInputStream("/home/dominick/OneDrive/Linux/teql/test/files/jabberwocky.txt"));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return;
        }

        for (Pair<Cursor, Character> pair : context) {
            System.out.println(pair);
        }
    }
}
