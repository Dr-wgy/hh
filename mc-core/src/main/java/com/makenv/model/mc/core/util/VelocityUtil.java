package com.makenv.model.mc.core.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.parser.node.ASTReference;
import org.apache.velocity.runtime.parser.node.ASTprocess;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.StringWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by alei on 2016/8/31.
 */
public class VelocityUtil {

  private static VelocityEngine velocityEngine;

  private static Boolean testOrNot = false;

  public static VelocityEngine newInstance(){

    if(velocityEngine == null) {

      synchronized(VelocityEngine.class) {

        if(velocityEngine == null) {

          velocityEngine = new VelocityEngine();

          if(!testOrNot) {

            velocityEngine.init();

          }
          else {

            Properties p = new Properties();
            p.setProperty("file.resource.loader.path","..");
            velocityEngine.init(p);

          }

        }
      }
    }
    return velocityEngine;
  }


  public static boolean isTestOrNot() {
    return testOrNot;
  }

  public static void setTestOrNot(boolean testOrNot) {

    VelocityUtil.testOrNot = testOrNot;
  }

  public static String buildTemplate(String templateFile, Map<String, String> values) {
    Template template = VelocityUtil.newInstance().getTemplate(templateFile);
    VelocityContext velocityContext = new VelocityContext();
    for (String key : values.keySet()) {
      velocityContext.put(key, values.get(key));
    }
    StringWriter writer = new StringWriter();
    template.merge(velocityContext, writer);
    return writer.toString().replaceAll("\r\n", "\n");
  }

  public static Set<String> getParameterNamesByVelocityTemplate(String templateFile) {


    return getParameterNamesByVelocityTemplate(templateFile, "utf-8");
  }

  public static Set<String> getParameterNamesByVelocityTemplate(String templateFile,String encoding) {

    Set set = new HashSet();

    Template template = VelocityUtil.newInstance().getTemplate(templateFile,encoding);

    Object data = template.getData();

    if(data instanceof ASTprocess) {

      ASTprocess process = ((ASTprocess)data);

      int length = process.jjtGetNumChildren();

      for(int i = 0 ;i < length;i++) {

        Node node = process.jjtGetChild(i);

        if(node instanceof ASTReference) {

          ASTReference reference = (ASTReference)node;

          String parameterName = reference.getRootString();

          if(parameterName != null) {

            set.add(parameterName);

          }
        }
      }
    }

    return set;
  }
}
