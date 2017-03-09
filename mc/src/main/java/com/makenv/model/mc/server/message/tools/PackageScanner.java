package com.makenv.model.mc.server.message.tools;

import java.io.IOException;
import java.util.List;

/**
 * Created by wgy on 2016/12/27.
 */
public interface PackageScanner {

    public List<String> getFullyQualifiedClassNameList() throws IOException;
}
