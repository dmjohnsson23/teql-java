package me.dmjohnson.teql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.commons.lang3.tuple.Pair;

import me.dmjohnson.teql.parsing.CharacterClassSubParser;
import me.dmjohnson.teql.parsing.EndAnchorSubParser;
import me.dmjohnson.teql.parsing.EverythingSubParser;
import me.dmjohnson.teql.parsing.FinalSubParser;
import me.dmjohnson.teql.parsing.LineEndAnchorSubParser;
import me.dmjohnson.teql.parsing.LineStartAnchorSubParser;
import me.dmjohnson.teql.parsing.LiteralSubParser;
import me.dmjohnson.teql.parsing.Mark;
import me.dmjohnson.teql.parsing.Parser;
import me.dmjohnson.teql.parsing.StartAnchorSubParser;
import me.dmjohnson.teql.parsing.StartSubParser;
import me.dmjohnson.teql.parsing.SubParser;
import me.dmjohnson.teql.parsing.SubParserNonBranching;

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

        StartSubParser start = new StartSubParser(new SubParser[]{
            new LiteralSubParser("Jabberwock")
                .then(new FinalSubParser())
        }, "selector");
        Parser parser = new Parser(new StartSubParser[]{start});
        Mark[][] results = parser.find_all(context);
        int i = 0;
        for (Mark[] result : results) {
            i++;
            System.out.println("Result "+i);
            for (Mark mark : result) {
                System.out.println(mark);
            }
        }
    }
}
