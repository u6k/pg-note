
package jp.gr.java_conf.u6k.pg_note;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class PgNoteApplication extends Application<PgNoteConfiguration> {

    public static void main(String[] args) throws Exception {
        new PgNoteApplication().run(args);
    }

    @Override
    public void run(PgNoteConfiguration config, Environment env) throws Exception {
        // TODO Auto-generated method stub
    }

}