
package jp.gr.java_conf.u6k.pg_note.command;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.gr.java_conf.u6k.pg_note.PgNoteConfiguration;
import jp.gr.java_conf.u6k.pg_note.vo.NotebookVO;
import net.arnx.jsonic.JSON;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.evernote.auth.EvernoteAuth;
import com.evernote.auth.EvernoteService;
import com.evernote.clients.ClientFactory;
import com.evernote.clients.NoteStoreClient;
import com.evernote.edam.type.Notebook;

public class ListNotebookCommand extends ConfiguredCommand<PgNoteConfiguration> {

    private static final Logger L = LoggerFactory.getLogger(ListNotebookCommand.class);

    public ListNotebookCommand() {
        super("list-notebook", "List notebooks.");
    }

    @Override
    protected void run(Bootstrap<PgNoteConfiguration> bootstrap, Namespace ns, PgNoteConfiguration config) throws Exception {
        EvernoteAuth auth = new EvernoteAuth(EvernoteService.PRODUCTION, config.getDevToken());
        ClientFactory factory = new ClientFactory(auth);
        NoteStoreClient noteStore = factory.createNoteStoreClient();

        List<NotebookVO> notebookVoList = new ArrayList<NotebookVO>();

        List<Notebook> notebookList = noteStore.listNotebooks();
        for (Notebook notebook : notebookList) {
            notebookVoList.add(new NotebookVO(notebook));
        }

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("notebooks", notebookVoList);

        String resultText = JSON.encode(result, true);
        System.out.println(resultText);
    }

}
