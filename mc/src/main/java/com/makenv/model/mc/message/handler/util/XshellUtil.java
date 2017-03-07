package com.makenv.model.mc.message.handler.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by wgy on 2017/2/24.
 */
public class XshellUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(XshellUtil.class);

    private static final String[] linuxShell = new String[]{"/bin/sh","-c"};

    public static void execCommandWithReturn(String cmd,Validator intHandler) {

        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = rt.exec(cmd);
            CmdInputLogProcessor error = new CmdInputLogProcessor(proc.getErrorStream());
            CmdInputLogProcessor input = new CmdInputLogProcessor(proc.getInputStream());
            FutureTask<Boolean> inputFuture = new FutureTask<Boolean>(input);
            FutureTask<Boolean> errorFuture = new FutureTask<Boolean>(error);
            new Thread(errorFuture).start();
            new Thread(inputFuture).start();
            int exitCode = proc.waitFor();
            Boolean flag = inputFuture.get();
            if(exitCode == 0 && flag) {

                System.out.println("执行成功");
            }
            else {

                System.out.println("执行失败");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static void execCommandWithLinux(String cmd,Validator intHandler) {

        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        try {
            List list = new ArrayList();
            list.addAll(Arrays.asList(linuxShell));
            list.add(cmd);
            String []cmdArray = (String[]) list.toArray(new String[0]);
            proc = rt.exec(cmdArray);
            CmdInputLogProcessor error = new CmdInputLogProcessor(proc.getErrorStream());
            CmdInputLogProcessor input = new CmdInputLogProcessor(proc.getInputStream());
            FutureTask<Boolean> inputFuture = new FutureTask<Boolean>(input);
            FutureTask<Boolean> errorFuture = new FutureTask<Boolean>(error);
            new Thread(errorFuture).start();
            new Thread(inputFuture).start();
            int exitCode = proc.waitFor();
            Boolean flag = inputFuture.get();
            if(exitCode == 0 && flag) {

                System.out.println("执行成功");
            }
            else {

                System.out.println("执行失败");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void execCommandWithReturn(String[] cmdArray,Validator intHandler) {

        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        try {
            proc = rt.exec(cmdArray);
            CmdInputLogProcessor input = new CmdInputLogProcessor(proc.getErrorStream());
            CmdErrorLogProcessor error = new CmdErrorLogProcessor(proc.getInputStream());
            FutureTask<Boolean> inputFuture = new FutureTask<Boolean>(input);
            FutureTask errorFuture = new FutureTask(error);
            new Thread(errorFuture).start();
            new Thread(inputFuture).start();
            int exitCode = proc.waitFor();
            Boolean flag = inputFuture.get();
            if(exitCode == 0 && flag) {

                System.out.println("执行成功");
            }
            else {

                System.out.println("执行失败");

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void executeCmd(String cmdArray) {

        Runtime rt = Runtime.getRuntime();
        Process proc = null;
        try {

            rt.exec(cmdArray);

            int exitCode = proc.waitFor();

            if(exitCode == 0) {

                System.out.println("执行成功");
            }
            else {

                System.out.println("执行失败");

            }

        } catch (IOException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
