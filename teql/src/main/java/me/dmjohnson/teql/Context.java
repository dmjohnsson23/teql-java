package me.dmjohnson.teql;

import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Context {
    public final CharSequence data;
    public final long start;
    public final long end;

    public Context(CharSequence data, long start, long end){
        this.data = data;
        this.start = start;
        this.end = end;
    }

    public Context(CharSequence data){
        this.data = data;
        this.start = 0;
        this.end = data.length();
    }
    
    static Context fromFile(Path path) throws IOException{
        FileChannel fileChannel = FileChannel.open(path, StandardOpenOption.READ);
        long size = fileChannel.size();
        MappedByteBuffer buffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, 0, size);
        return new Context(buffer.asCharBuffer(), 0, size); // FIXME: asCharBuffer uses UTF-16, and that cannot be changed...
    }

    static Context fromFile(String path) throws IOException{
        return Context.fromFile(Path.of(path));
    }

    private long resolveIndex(long index){
        return index; // TODO
    }

    Context subContext(long start, long end){
        return new Context(data, this.resolveIndex(start), this.resolveIndex(end));
    }

}
