package com.example;

import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPAbortedException;
import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPDataTransferException;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPFile;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;
import it.sauronsoftware.ftp4j.FTPListParseException;

/**
 * Created by linky on 16-2-22.
 */
public class FTPMain {

    public static void main(String[] args) {

        FTPClient client = new FTPClient();

        try {

//            ftp://ygdy8:ygdy8@y219.dydytt.net:9053/[阳光电影www.ygdy8.com].布鲁克林.BD.720p.中英双字幕.rmvb

            client.connect("120.55.240.193");
            client.login("liuxiongbin", "liangjiu2009");

//            client.connect("y219.dydytt.net", 9053);
//            client.login("ygdy8", "ygdy8");

            FTPFile[] a = client.list();
            if (a != null) {
                for (FTPFile file : a) {
                    System.out.println(" name " + file.getName());
                    System.out.println(" link " + file.getLink());
                    System.out.println(" size " + file.getSize());
                    System.out.println(" type " + file.getType());
                }
            } else {
                System.out.println(" a == null ");
            }

        } catch (FTPIllegalReplyException e) {
            e.printStackTrace();
            System.out.println(" FTPIllegalReplyException ");
        } catch (FTPException e) {
            e.printStackTrace();
            System.out.println(" FTPException ");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(" IOException ");
        } catch (FTPAbortedException e) {
            e.printStackTrace();
            System.out.println(" FTPAbortedException ");
        } catch (FTPListParseException e) {
            e.printStackTrace();
            System.out.println(" FTPListParseException ");
        } catch (FTPDataTransferException e) {
            e.printStackTrace();
            System.out.println(" FTPDataTransferException ");
        }
    }
}
