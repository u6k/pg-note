
package jp.gr.java_conf.u6k.pg_note.command;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.gr.java_conf.u6k.pg_note.PgNoteConfiguration;
import jp.gr.java_conf.u6k.pg_note.evernote.EvernoteUtil;
import jp.gr.java_conf.u6k.pg_note.vo.NotebookVO;
import net.arnx.jsonic.JSON;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListNotebookCommand extends ConfiguredCommand<PgNoteConfiguration> {

    private static final Logger L = LoggerFactory.getLogger(ListNotebookCommand.class);

    public ListNotebookCommand() {
        super("list-notebook", "List notebooks.");
    }

    @Override
    protected void run(Bootstrap<PgNoteConfiguration> bootstrap, Namespace ns, PgNoteConfiguration config) throws Exception {
        String devToken = config.getDevToken();
        L.debug("devToken: " + devToken);

        List<NotebookVO> notebookVoList = new EvernoteUtil(devToken).listNotebook();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("notebooks", notebookVoList);

        String resultText = JSON.encode(result, true);
        System.out.println(resultText);
    }

}
