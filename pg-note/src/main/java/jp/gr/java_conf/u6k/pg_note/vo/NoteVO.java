
package jp.gr.java_conf.u6k.pg_note.vo;

import com.evernote.edam.notestore.NoteMetadata;

public class NoteVO {

    private String guid;

    private String title;

    private String notebookGuid;

    private String sourceUrl;

    public NoteVO() {
    }

    public NoteVO(NoteMetadata note) {
        this.guid = note.getGuid();
        this.title = note.getTitle();
        this.notebookGuid = note.getNotebookGuid();
        this.sourceUrl = note.getAttributes().getSourceURL();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotebookGuid() {
        return notebookGuid;
    }

    public void setNotebookGuid(String notebookGuid) {
        this.notebookGuid = notebookGuid;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

}
