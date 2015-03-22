
package jp.gr.java_conf.u6k.pg_note.command;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.gr.java_conf.u6k.pg_note.PgNoteConfiguration;
import jp.gr.java_conf.u6k.pg_note.evernote.EvernoteUtil;
import jp.gr.java_conf.u6k.pg_note.vo.NoteVO;
import net.arnx.jsonic.JSON;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListNoteCommand extends ConfiguredCommand<PgNoteConfiguration> {

    private static final Logger L = LoggerFactory.getLogger(ListNoteCommand.class);

    public ListNoteCommand() {
        super("list-note", "List notes.");
    }

    @Override
    protected void run(Bootstrap<PgNoteConfiguration> bootstrap, Namespace ns, PgNoteConfiguration config) throws Exception {
        String devToken = config.getDevToken();
        L.debug("devToken: " + devToken);

        List<NoteVO> noteVoList = new EvernoteUtil(devToken).listNote();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("notes", noteVoList);

        String resultText = JSON.encode(result, true);
        System.out.println(resultText);
    }

}
