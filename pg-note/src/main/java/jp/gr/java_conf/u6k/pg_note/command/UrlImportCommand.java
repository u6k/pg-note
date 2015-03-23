
package jp.gr.java_conf.u6k.pg_note.command;

import io.dropwizard.cli.ConfiguredCommand;
import io.dropwizard.setup.Bootstrap;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Map;

import jp.gr.java_conf.u6k.pg_note.PgNoteConfiguration;
import jp.gr.java_conf.u6k.pg_note.PgNoteException;
import jp.gr.java_conf.u6k.pg_note.evernote.EvernoteUtil;
import net.arnx.jsonic.JSON;
import net.sourceforge.argparse4j.inf.Namespace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlImportCommand extends ConfiguredCommand<PgNoteConfiguration> {

    private static final Logger L = LoggerFactory.getLogger(UrlImportCommand.class);

    public UrlImportCommand() {
        super("url-import", "Url import from JSON file.");
    }

    @Override
    protected void run(Bootstrap<PgNoteConfiguration> bootstrap, Namespace ns, PgNoteConfiguration config) throws Exception {
        EvernoteUtil evernoteUtil = new EvernoteUtil(config.getDevToken());

        String jsonFilePath = System.getProperty("jsonFilePath");
        L.info("jsonFilePath: " + jsonFilePath);
        String notebookGuid = System.getProperty("notebook");
        L.info("notebook: " + notebookGuid);
        String tagGuid = System.getProperty("tag");
        L.info("tag: " + tagGuid);

        List<Map<String, String>> pixivLinkJson = JSON.decode(new FileInputStream(jsonFilePath));
        while (true) {
            boolean isFinished = true;

            try {
                for (Map<String, String> pixivLink : pixivLinkJson) {
                    if (!"ok".equals(pixivLink.get("status"))) {
                        String url = pixivLink.get("url");
                        L.debug("create linknote: url: " + url);

                        evernoteUtil.createLinknote(url, notebookGuid, tagGuid);

                        pixivLink.put("status", "ok");
                        JSON.encode(pixivLinkJson, new FileOutputStream(jsonFilePath));
                        L.debug("ok");
                    }
                }
            } catch (PgNoteException e) {
                L.warn("ng", e);
                isFinished = false;
            }

            if (isFinished) {
                break;
            } else {
                L.info("sleep: 10m");
                Thread.sleep(1000 * 60 * 10);
            }
        }
    }

}
