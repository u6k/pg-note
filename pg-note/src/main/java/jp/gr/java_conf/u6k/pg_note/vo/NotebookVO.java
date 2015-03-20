
package jp.gr.java_conf.u6k.pg_note.vo;

import com.evernote.edam.type.Notebook;

public class NotebookVO {

    private String guid;

    private String name;

    public NotebookVO() {
    }

    public NotebookVO(Notebook notebook) {
        this.guid = notebook.getGuid();
        this.name = notebook.getName();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
