package me.dmjohnson.teql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class FileContext implements Context {
    private FileInputStream stream;
    private Charset encoding;

    public FileContext(FileInputStream stream, Charset encoding){
        this.stream = stream;
        this.encoding = encoding;
    }
    public FileContext(String filename) throws FileNotFoundException{
        this(new FileInputStream(filename), Charset.forName("UTF-8"));
    }
    public FileContext(File file) throws FileNotFoundException{
        this(new FileInputStream(file), Charset.forName("UTF-8"));
    }
    public FileContext(FileInputStream stream){
        this(stream, Charset.forName("UTF-8"));
    }
    public FileContext(String filename, String encoding) throws FileNotFoundException{
        this(new FileInputStream(filename), Charset.forName(encoding));
    }
    public FileContext(File file, String encoding) throws FileNotFoundException{
        this(new FileInputStream(file), Charset.forName(encoding));
    }
    public FileContext(FileInputStream stream, String encoding){
        this(stream, Charset.forName(encoding));
    }
    public FileContext(String filename, Charset encoding) throws FileNotFoundException{
        this(new FileInputStream(filename), encoding);
    }
    public FileContext(File file, Charset encoding) throws FileNotFoundException{
        this(new FileInputStream(file), encoding);
    }

    private class FileContextIterator implements Iterator<Pair<Cursor,Character>>{
        private Cursor nextCursor;
        private BufferedReader reader;

        public FileContextIterator(FileContext context) throws IOException{
            this.reader = new BufferedReader(new InputStreamReader(context.stream, context.encoding));
            this.nextCursor = new Cursor(0, 0, 1, 1, context.stream.available()==0);
        }

        @Override
        public boolean hasNext() {
            return nextCursor != null;
        }

        @Override
        public Pair<Cursor, Character> next() {
            if (nextCursor == null){
                // Should never happen
                return null;
            }
            if (nextCursor.eof){
                // Last cursor at end of file
                return new ImmutablePair<Cursor,Character>(nextCursor, null);
            }
            final int result;
            try {
                result = reader.read();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            final Character out;
            final Cursor cursor;
            if (result == -1){
                // End of file
                out = null;
                cursor = new Cursor(nextCursor.byte_pos, nextCursor.char_pos, nextCursor.line_no, nextCursor.col_no, true);
                nextCursor = null;
            }
            else{
                out = (char)result;
                cursor = nextCursor;
                // TODO detect multi-char newlines
                if (out.equals('\n')){
                    nextCursor = new Cursor(0, cursor.char_pos+1, cursor.line_no+1, 1, false);
                }
                else{
                    nextCursor = new Cursor(0, cursor.char_pos+1, cursor.line_no, cursor.col_no+1, false);
                }
                
            }
            return new ImmutablePair<Cursor,Character>(cursor, out);
        }
    }

    @Override
    public Iterator<Pair<Cursor, Character>> iterator() {
        try {
            return new FileContextIterator(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
}
