
package jp.gr.java_conf.u6k.pg_note;

@SuppressWarnings("serial")
public class PgNoteException extends RuntimeException {

    public PgNoteException() {
        super();
    }

    public PgNoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public PgNoteException(String message) {
        super(message);
    }

    public PgNoteException(Throwable cause) {
        super(cause);
    }

}
