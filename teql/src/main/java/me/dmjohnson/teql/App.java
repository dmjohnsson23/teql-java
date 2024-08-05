package me.dmjohnson.teql;

import java.io.IOException;

import me.dmjohnson.teql.selector.RegexSelector;
import me.dmjohnson.teql.selector.Selection;
import me.dmjohnson.teql.selector.Selector;

public class App{
    public static void main( String[] args ){
        Context context;
        try {
            context = Context.fromFile("/home/dominick/OneDrive/Linux/teql/test/files/aristotle.html");
            System.out.println(context.subContext(0, 100).data);
        } catch (IOException e) {
            System.out.println("Failed to open file");
            return;
        }
        Selector selector = new RegexSelector("i");
        Selection result = selector.find_one(context);
        if (result == null){
            System.out.println("No match found");
        }
        else{
            System.out.println( result.start );
            System.out.println( result.end );
        }
    }
}
