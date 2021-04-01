package com.unn.constants;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface Constant {
    /**
     * Params sizes
     */

    public final int USER_PARAMS_SIZE = 127;
    public final int FACILITY_PARAM_SIZE = 127;
    public final int DOCUMENT_NUMBER_SIZE = 127;
    public final int DESCRIPTION_SIZE = 1023;
    public final String METRIC_NOT_SPECIFIED = "not specified";

    /**
     * Path
     */

    public final Path UPLOAD_PATH = Paths.get("/files");

    /**
     * Email message
     */
    public final String NOTIFICATION = "Do not forget about your appointment with %s at %s";
}
