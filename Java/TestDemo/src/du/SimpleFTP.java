package du;

/*
Copyright Paul James Mutton, 2001-2004, http://www.jibble.org/

This file is part of SimpleFTP.

This software is dual-licensed, allowing you to choose between the GNU
General Public License (GPL) and the www.jibble.org Commercial License.
Since the GPL may be too restrictive for use in a proprietary application,
a commercial license is also provided. Full license information can be
found at http://www.jibble.org/licenses/

$Author: pjm2 $
$Id: SimpleFTP.java,v 1.2 2004/05/29 19:27:37 pjm2 Exp $

*/

// package org.jibble.simpleftp;

import java.io.*;
import java.net.*;
import java.util.*;


/**
 * SimpleFTP is a simple package that implements a Java FTP client.
 * With SimpleFTP, you can connect to an FTP server and upload multiple files.
 *  <p>
 * Copyright Paul Mutton,
 *           <a href="http://www.jibble.org/">http://www.jibble.org/</a>
 * 
 */
public class SimpleFTP {
    private Socket socket = null;
    private BufferedReader reader = null;
    private BufferedWriter writer = null;
    
    private static boolean DEBUG = false;
    
    public SimpleFTP() {
    } // SimpleFTP
    
    
    /**
     * Connects to the default port of an FTP server and logs in as
     * anonymous/anonymous.
     */
    public synchronized void connect(String host) throws IOException 
    {
        connect(host, 21);
    } // connect
    
    
    /**
     * Connects to an FTP server and logs in as anonymous/anonymous.
     */
    public synchronized void connect(String host, int port) throws IOException {
        connect(host, port, "anonymous", "anonymous");
    }
    
    
    /**
     * Connects to an FTP server and logs in with the supplied username
     * and password.
     */
    public synchronized void connect(String host, int port, String user, String pass) throws IOException 
    {
        if (socket != null) {
            throw new IOException("SimpleFTP is already connected. Disconnect first.");
        }
        socket = new Socket(host, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        
        String response, substr;
        while (true) {
          response = readLine();
          System.out.println("response - " + response);
          substr = response.substring(0, 4);
          if (substr.compareTo("220 ") == 0) {
            sendLine("USER " + user);
          } // if
          
          if (substr.compareTo("331 ") == 0) {
            sendLine("PASS " + pass);
          } // if

          if (substr.compareTo("230 ") == 0) {
            break;
          } // if
        } // while
    } // connect
    
    
    /**
     * Disconnects from the FTP server.
     */
    public synchronized void disconnect() throws IOException {
        try {
            sendLine("QUIT");
        } // try
        finally {
            socket.close();
            socket = null;
        } // finally
    } // disconnect
    
    
    /**
     * Returns the working directory of the FTP server it is connected to.
     */
    public synchronized String pwd() throws IOException {
        sendLine("PWD");
        String dir = null;
        String response = readLine();
        if (response.startsWith("257 ")) {
            int firstQuote = response.indexOf('\"');
            int secondQuote = response.indexOf('\"', firstQuote + 1);
            if (secondQuote > 0) {
                dir = response.substring(firstQuote + 1, secondQuote);
            } // if
        } // if
        return dir;
    } // pwd


    /**
     * Changes the working directory (like cd). Returns true if successful.
     */   
    public synchronized boolean cwd(String dir) throws IOException {
        sendLine("CWD " + dir);
        String response = readLine();
        return (response.startsWith("250 "));
    } // cwd
    
    
    /**
     * Sends a file to be stored on the FTP server.
     * Returns true if the file transfer was successful.
     * The file is sent in passive mode to avoid NAT or firewall problems
     * at the client end.
     */
    public synchronized boolean stor(File file) throws IOException {
        if (file.isDirectory()) {
            throw new IOException("SimpleFTP cannot upload a directory.");
        }
        
        String filename = file.getName();

        return stor(new FileInputStream(file), filename);
    } // stor
    
    
    /**
     * Sends a file to be stored on the FTP server.
     * Returns true if the file transfer was successful.
     * The file is sent in passive mode to avoid NAT or firewall problems
     * at the client end.
     */
    public synchronized boolean stor(InputStream inputStream, String filename) throws IOException {

        BufferedInputStream input = new BufferedInputStream(inputStream);
        
        sendLine("PASV");
        String response = readLine();
        if (!response.startsWith("227 ")) {
            throw new IOException("SimpleFTP could not request passive mode: " + response);
        }
        System.out.println("response: " + response);
        
        String ip = null;
        int port = -1;
        int opening = response.indexOf('(');
        int closing = response.indexOf(')', opening + 1);
        if (closing > 0) {
            String dataLink = response.substring(opening + 1, closing);
            StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
            try {
                ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken() + "." + tokenizer.nextToken();
                port = ((Integer.parseInt(tokenizer.nextToken()) << 8) & 0xff00) | (Integer.parseInt(tokenizer.nextToken()) & 0xff);
            }
            catch (Exception e) {
                throw new IOException("SimpleFTP received bad data link information: " + response);
            }
        }
        
        System.out.println("-------------------------" + ip + ":" + port);
        
        sendLine("STOR " + filename);

        System.out.println("HHHHHHHHHHH");
        
        Socket dataSocket = new Socket(ip, port);


        System.out.println("HHHHHHHHHHH");
        
        response = readLine();
        if (!response.startsWith("150 ")) {
            dataSocket.close();
            throw new IOException("SimpleFTP was not allowed to send the file: " + response);
        }
        
        BufferedOutputStream output = new BufferedOutputStream(dataSocket.getOutputStream());
        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while ((bytesRead = input.read(buffer)) != -1) {
            output.write(buffer, 0, bytesRead);
        }
        output.flush();
        output.close();
        input.close();
        dataSocket.close();
        
        response = readLine();
        return response.startsWith("226 ");
    } // stor


    /**
     * Enter binary mode for sending binary files.
     */
    public synchronized boolean bin() throws IOException {
        sendLine("TYPE I");
        String response = readLine();
        return (response.startsWith("200 "));
    } // bin
    
    
    /**
     * Enter ASCII mode for sending text files. This is usually the default
     * mode. Make sure you use binary mode if you are sending images or
     * other binary data, as ASCII mode is likely to corrupt them.
     */
    public synchronized boolean ascii() throws IOException {
        sendLine("TYPE A");
        String response = readLine();
        return (response.startsWith("200 "));
    } // ascii
    
    
    /**
     * Sends a raw command to the FTP server.
     */
    private void sendLine(String line) throws IOException {
        if (socket == null) {
            throw new IOException("SimpleFTP is not connected.");
        }
        try {
            writer.write(line + "\r\n");
            writer.flush();
            if (DEBUG) {
                System.out.println("> " + line);
            }
        }
        catch (IOException e) {
            socket = null;
            throw e;
        }
    } // sendLine
    
    private String readLine() throws IOException {
        String line = reader.readLine();
        if (DEBUG) {
            System.out.println("< " + line);
        }
        return line;
    } // readLine
    
    
    
} // SimpleFTP