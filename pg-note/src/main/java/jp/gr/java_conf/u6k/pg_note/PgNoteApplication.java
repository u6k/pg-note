
package jp.gr.java_conf.u6k.pg_note;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import jp.gr.java_conf.u6k.pg_note.command.CreateLinknoteCommand;
import jp.gr.java_conf.u6k.pg_note.command.UrlImportCommand;
import jp.gr.java_conf.u6k.pg_note.command.ListNoteCommand;
import jp.gr.java_conf.u6k.pg_note.command.ListNotebookCommand;

public class PgNoteApplication extends Application<PgNoteConfiguration> {

    public static void main(String[] args) throws Exception {
        new PgNoteApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<PgNoteConfiguration> bootstrap) {
        bootstrap.addCommand(new ListNotebookCommand());
        bootstrap.addCommand(new ListNoteCommand());
        bootstrap.addCommand(new CreateLinknoteCommand());
        bootstrap.addCommand(new UrlImportCommand());
    }

    @Override
    public void run(PgNoteConfiguration config, Environment env) throws Exception {
        // TODO Auto-generated method stub
    }

}
