
package jp.gr.java_conf.u6k.pg_note.evernote;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.gr.java_conf.u6k.pg_note.PgNoteException;
import jp.gr.java_conf.u6k.pg_note.vo.NoteVO;
import jp.gr.java_conf.u6k.pg_note.vo.NotebookVO;
import jp.gr.java_conf.u6k.pg_note.vo.TagVO;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.error.EDAMNotFoundException;
import com.evernote.edam.error.EDAMSystemException;
import com.evernote.edam.error.EDAMUserException;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteMetadata;
import com.evernote.edam.notestore.NotesMetadataList;
import com.evernote.edam.notestore.NotesMetadataResultSpec;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteAttributes;
import com.evernote.edam.type.NoteSortOrder;
import com.evernote.edam.type.Notebook;
import com.evernote.edam.type.Tag;
import com.evernote.thrift.TException;

public class EvernoteUtil {

    private static final Logger L = LoggerFactory.getLogger(EvernoteUtil.class);

    private String _devToken;

    public EvernoteUtil(String devToken) {
        _devToken = devToken;
    }

    public void createLinknote(String url, String notebookGuid, String tagGuid) {
        try {
            if (url == null) {
                throw new NullPointerException("url");
            }

            Document doc;
            try {
                doc = Jsoup.connect(url).timeout(10 * 1000).get();
            } catch (IOException e) {
                L.warn("HTTP Error", e);
                return;
            }
            Elements titleE = doc.select("title");
            String title;
            if (titleE.size() > 0) {
                title = titleE.get(0).html();
            } else {
                title = "no title";
            }
            L.debug("title: " + title);

            String noteBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
            noteBody += "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">";
            noteBody += "<en-note>" + StringEscapeUtils.escapeXml10(title) + "\n" + StringEscapeUtils.escapeXml10(url) + "</en-note>";

            EvernoteAuth auth = new EvernoteAuth(EvernoteService.PRODUCTION, _devToken);
            ClientFactory factory = new ClientFactory(auth);
            NoteStoreClient noteStore = factory.createNoteStoreClient();

            Note note = new Note();
            note.setTitle(title);
            note.setContent(noteBody);
            if (notebookGuid != null) {
                note.setNotebookGuid(notebookGuid);
            }
            if (tagGuid != null) {
                note.setTagGuids(Arrays.asList(tagGuid));
            }

            NoteAttributes attrs = new NoteAttributes();
            attrs.setSourceURL(url);
            note.setAttributes(attrs);

            noteStore.createNote(note);
        } catch (EDAMUserException | EDAMSystemException | TException | EDAMNotFoundException e) {
            throw new PgNoteException(e);
        }
    }

    public List<NotebookVO> listNotebook() {
        try {
            EvernoteAuth auth = new EvernoteAuth(EvernoteService.PRODUCTION, _devToken);
            ClientFactory factory = new ClientFactory(auth);
            NoteStoreClient noteStore = factory.createNoteStoreClient();

            List<NotebookVO> notebookVoList = new ArrayList<NotebookVO>();

            List<Notebook> notebookList = noteStore.listNotebooks();
            for (Notebook notebook : notebookList) {
                notebookVoList.add(new NotebookVO(notebook));
            }

            return notebookVoList;
        } catch (EDAMUserException | EDAMSystemException | TException e) {
            throw new PgNoteException(e);
        }
    }

    public List<NoteVO> listNote() {
        try {
            EvernoteAuth auth = new EvernoteAuth(EvernoteService.PRODUCTION, _devToken);
            ClientFactory factory = new ClientFactory(auth);
            NoteStoreClient noteStore = factory.createNoteStoreClient();

            List<NoteVO> noteVoList = new ArrayList<NoteVO>();

            NoteFilter filter = new NoteFilter();
            filter.setOrder(NoteSortOrder.UPDATED.getValue());

            NotesMetadataResultSpec spec = new NotesMetadataResultSpec();
            spec.setIncludeTitle(true);
            spec.setIncludeNotebookGuid(true);
            spec.setIncludeAttributes(true);

            NotesMetadataList noteList = noteStore.findNotesMetadata(filter, 0, 100, spec);
            for (NoteMetadata note : noteList.getNotes()) {
                noteVoList.add(new NoteVO(note));
            }

            return noteVoList;
        } catch (EDAMUserException | EDAMSystemException | TException | EDAMNotFoundException e) {
            throw new PgNoteException(e);
        }
    }

    public List<TagVO> listTag() {
        try {
            EvernoteAuth auth = new EvernoteAuth(EvernoteService.PRODUCTION, _devToken);
            ClientFactory factory = new ClientFactory(auth);
            NoteStoreClient noteStore = factory.createNoteStoreClient();

            List<TagVO> tagVoList = new ArrayList<TagVO>();

            List<Tag> tagList = noteStore.listTags();
            for (Tag tag : tagList) {
                tagVoList.add(new TagVO(tag));
            }

            return tagVoList;
        } catch (EDAMUserException | EDAMSystemException | TException e) {
            throw new PgNoteException(e);
        }
    }

}
