
package jp.gr.java_conf.u6k.pg_note.vo;

import com.evernote.edam.type.Tag;

public class TagVO {

    private String guid;

    private String name;

    public TagVO() {
    }

    public TagVO(Tag tag) {
        this.guid = tag.getGuid();
        this.name = tag.getName();
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
