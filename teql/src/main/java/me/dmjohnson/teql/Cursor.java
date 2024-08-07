package me.dmjohnson.teql;

public class Cursor {
    /** 
     * The position of the cursor measured in bytes from the beginning of the context.
     * 
     * Some contexts read directly as string rather than decoding from bytes. For such contexts, 
     * this should always be 0.
     */
    public final long byte_pos;
    /** 
     * The position of the cursor measured in characters from the beginning of the context.
     */
    public final long char_pos;
    /** 
     * The line number of the character immediately after the current cursor; e.g. the line number
     * that a character inserted at this cursor would have. Begins at 1.
     */
    public final long line_no;
    /** 
     * The column number of the character immediately after the current cursor; e.g. the column 
     * number that a character inserted at this cursor would have. Begins at 1.
     */
    public final long col_no;
    /** 
     * If true, there is no character after this cursor; we are at the end of the context. 
     */
    public final boolean eof;

    public Cursor(long byte_pos, long char_pos, long line_no, long col_no, boolean eof){
        this.byte_pos = byte_pos;
        this.char_pos = char_pos;
        this.line_no = line_no;
        this.col_no = col_no;
        this.eof = eof;
    }

    public String toString(){
        return "Cursor(byte_pos: "+byte_pos+", char_pos: "+char_pos+", line_no: "+line_no+", col_no: "+col_no+", eof: "+eof+")";
    }
}
