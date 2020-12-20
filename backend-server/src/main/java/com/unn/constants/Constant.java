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

    /**
     * Path
     */

    public final Path UPLOAD_PATH = Paths.get("/files");
}
