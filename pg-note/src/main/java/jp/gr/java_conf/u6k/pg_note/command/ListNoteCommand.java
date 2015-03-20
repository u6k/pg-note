
package jp.gr.java_conf.u6k.pg_note.command;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.gr.java_conf.u6k.pg_note.PgNoteConfiguration;
import jp.gr.java_conf.u6k.pg_note.vo.NoteVO;
import net.arnx.jsonic.JSON;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.notestore.NoteFilter;
import com.evernote.edam.notestore.NoteMetadata;
import com.evernote.edam.notestore.NotesMetadataList;
import com.evernote.edam.notestore.NotesMetadataResultSpec;
import com.evernote.edam.type.NoteSortOrder;

public class ListNoteCommand extends ConfiguredCommand<PgNoteConfiguration> {

    private static final Logger L = LoggerFactory.getLogger(ListNoteCommand.class);

    public ListNoteCommand() {
        super("list-note", "List notes.");
    }

    @Override
    protected void run(Bootstrap<PgNoteConfiguration> bootstrap, Namespace ns, PgNoteConfiguration config) throws Exception {
        EvernoteAuth auth = new EvernoteAuth(EvernoteService.PRODUCTION, config.getDevToken());
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

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("notes", noteVoList);

        String resultText = JSON.encode(result, true);
        System.out.println(resultText);
    }

}
