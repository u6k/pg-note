
package jp.gr.java_conf.u6k.pg_note.command;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;
import jp.gr.java_conf.u6k.pg_note.PgNoteConfiguration;
import jp.gr.java_conf.u6k.pg_note.evernote.EvernoteUtil;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateLinknoteCommand extends ConfiguredCommand<PgNoteConfiguration> {

    private static final Logger L = LoggerFactory.getLogger(CreateLinknoteCommand.class);

    public CreateLinknoteCommand() {
        super("create-linknote", "Create LinkNote.");
    }

    @Override
    protected void run(Bootstrap<PgNoteConfiguration> bootstrap, Namespace ns, PgNoteConfiguration config) throws Exception {
        String devToken = config.getDevToken();
        L.debug("devToken: " + devToken);

        String url = System.getProperty("url");
        L.debug("url: " + url);

        String notebookGuid = System.getProperty("notebook");
        L.debug("notebook: " + notebookGuid);

        String tagGuid = System.getProperty("tag");
        L.debug("tag: " + tagGuid);

        new EvernoteUtil(devToken).createLinknote(url, notebookGuid, tagGuid);
    }

}
