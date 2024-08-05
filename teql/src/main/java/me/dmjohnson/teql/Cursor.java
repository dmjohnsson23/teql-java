package me.dmjohnson.teql;

public class Cursor {
    public final long byte_pos;
    public final long char_pos;
    public final long line_no;
    public final long col_no;
    public final boolean eof;

    public Cursor(long byte_pos, long char_pos, long line_no, long col_no, boolean eof){
        this.byte_pos = byte_pos;
        this.char_pos = char_pos;
        this.line_no = line_no;
        this.col_no = col_no;
        this.eof = eof;
    }
}
