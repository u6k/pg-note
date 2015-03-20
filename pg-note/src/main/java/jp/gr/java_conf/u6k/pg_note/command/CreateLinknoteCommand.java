
package jp.gr.java_conf.u6k.pg_note.command;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import jp.gr.java_conf.u6k.pg_note.PgNoteConfiguration;
import net.sourceforge.argparse4j.inf.Namespace;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.type.Note;
import com.evernote.edam.type.NoteAttributes;

public class CreateLinknoteCommand extends ConfiguredCommand<PgNoteConfiguration> {

    private static final Logger L = LoggerFactory.getLogger(CreateLinknoteCommand.class);

    public CreateLinknoteCommand() {
        super("create-linknote", "Create LinkNote.");
    }

    @Override
    protected void run(Bootstrap<PgNoteConfiguration> bootstrap, Namespace ns, PgNoteConfiguration config) throws Exception {
        String url = System.getProperty("url");
        L.debug("url: " + url);

        Document doc = Jsoup.connect(url).timeout(10 * 1000).get();
        Elements titleE = doc.select("title");
        String title;
        if (titleE.size() > 0) {
            title = titleE.get(0).html();
        } else {
            title = "no title";
        }
        L.debug("title: " + title);

        String notebookGuid = System.getProperty("notebook");
        L.debug("notebook: " + notebookGuid);

        String noteBody = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        noteBody += "<!DOCTYPE en-note SYSTEM \"http://xml.evernote.com/pub/enml2.dtd\">";
        noteBody += "<en-note>" + title + "\n" + url + "</en-note>";

        EvernoteAuth auth = new EvernoteAuth(EvernoteService.PRODUCTION, config.getDevToken());
        ClientFactory factory = new ClientFactory(auth);
        NoteStoreClient noteStore = factory.createNoteStoreClient();

        Note note = new Note();
        note.setTitle(title);
        note.setContent(noteBody);
        note.setNotebookGuid(notebookGuid);

        NoteAttributes attrs = new NoteAttributes();
        attrs.setSourceURL(url);
        note.setAttributes(attrs);

        noteStore.createNote(note);
    }

}
