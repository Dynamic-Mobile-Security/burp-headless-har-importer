/*
Released as open source by NCC Group Plc - http://www.nccgroup.com/

Developed by Jose Selvi, jose dot selvi at nccgroup dot com

https://github.com/nccgroup/BurpImportSitemap

Released under AGPL see LICENSE for more information
*/

package sting.imports;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import burp.IExtensionHelpers;
import burp.IHttpRequestResponse;
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderMode;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;
import de.sstoehr.harreader.model.HarHeader;
import sting.HarClientPanel;

import java.util.List;
import java.io.File;
import java.io.BufferedWriter;
import java.io.Writer;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class RAYImport {
    static private String hostName = "";
    static private String delimeter = "\r\n";
    static private String space = " ";

    public static List<String> getLoadFile() {
        List<String> fileNames = new ArrayList<String>();
        JSONParser parser = new JSONParser();
		try {
			Object obj = parser.parse(new FileReader("har_files_to_import.json"));
            JSONObject jsonObject = (JSONObject) obj;
			JSONArray fileList = (JSONArray) jsonObject.get("harfiles");
 
			Iterator<JSONObject> iterator = fileList.iterator();
			while (iterator.hasNext()) {
                String fileName = (String) iterator.next().get("path");
                fileNames.add(fileName);
			}
        } catch (Exception e) {
		    //handle exception
		}

        return fileNames;
    }

    public static ArrayList<String> readFile(String filename) {
        BufferedReader reader;
        ArrayList<String> lines = new ArrayList<String>();

        try {
            reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            return new ArrayList<String>();
        }
        try {
            String line;
            while ( (line = reader.readLine()) != null ) {
                lines.add(line);
            }
        } catch (IOException e) {
            return new ArrayList<String>();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return lines;
    }

    public static ArrayList<IHttpRequestResponse> loadHAR(boolean doTrick) {
        ArrayList<IHttpRequestResponse> requests = new ArrayList<IHttpRequestResponse>();
        IExtensionHelpers helpers = HarClientPanel.callbacks.getHelpers();
        
        List<String> fileNames = getLoadFile();
        HarReader harReader = new HarReader();
        Iterator<HarEntry> items = null;
        System.out.println("[INFO] Requests will be parsed from har.files: " + fileNames.toString() );
        for (String filename : fileNames) {
            if ( filename.length() == 0 ) {
                writeLogFile("[ERROR] could not open file: " + filename);
                continue;
            }
            try {
                Har har = harReader.readFromFile(new File(filename), HarReaderMode.LAX);
                items = har.getLog().getEntries().iterator();
            } catch (HarReaderException e) {
                writeLogFile("[ERROR] to read HAR from from file: " + filename);
                String stackTrace = e.getStackTrace().toString();
                writeLogFile("[ERROR] exception: " + e.toString() + " stackTrace: " + stackTrace);
                new ArrayList<IHttpRequestResponse>();
            }
            while (items.hasNext()) {
                try {
                    HarEntry reqRespEntry = items.next();
                    String url = reqRespEntry.getRequest().getUrl();
    
                    byte[] requestBytes = helpers.stringToBytes(makeRequest(reqRespEntry.getRequest(), doTrick) );
    
                    // TODO - RESPONSE FORMATING
                    byte[] responseBytes = helpers.stringToBytes( makeResponse(reqRespEntry.getResponse()) );
    
                    RAYRequestResponse x = new RAYRequestResponse(url, requestBytes, responseBytes);
                    requests.add(x);
    
                } catch (Exception e) {
                    writeLogFile("[ERROR] to transform String to bytes." );
                    writeLogFile("[ERROR] exception: " + e.toString() );
                    continue;
                }
            }
        }
        writeLogFile("[INFO] requests are readed from the HAR.file: " + requests.toString() );
        System.out.println("[OK] Requests parsed.");
        return requests;
    }

    static public String makeRequest(HarRequest harRequest, boolean doTrick) {
        StringBuilder request = new StringBuilder();

        // TODO - doTrick: Добавь к параметру метод в качестве значения.   stingrayParamToExclude=post | put | get
        //  Если в множестве уже будет присутствовать УРЛ у которого НЕТ параметров.
        String method = harRequest.getMethod().name();
        String version = harRequest.getHttpVersion();
        String headers = printHeaders(harRequest.getHeaders());

        String urlPath = getPath(harRequest.getUrl());
        String postData = harRequest.getPostData().getText();
        postData = (null == postData) ? "" : postData;
        request.append(method)
                .append(space)
                .append(urlPath)
                .append(space)
                .append(version)
                .append(delimeter)
                .append(headers)
                .append(delimeter)
                .append(postData.length() == 0 ? "" : postData + delimeter);

        return request.toString();
    }

    static public String makeResponse(HarResponse harResponse) {
        StringBuilder response = new StringBuilder();

        String version = harResponse.getHttpVersion();
        String status = harResponse.getStatus() + " " + harResponse.getStatusText();
        String headers = printHeaders(harResponse.getHeaders());
        response.append(version)
                .append(space)
                .append(status)
                .append(delimeter)
                .append(headers);
        
        return response.toString();
    }

    static public String printHeaders(List<HarHeader> headers) {
        StringBuilder headersString = new StringBuilder();

        Iterator<HarHeader> items = headers.iterator();
        while (items.hasNext()) {
            HarHeader header = items.next();
            getHost(header);
            headersString.append(header.getName()).append(": ").append(header.getValue()).append(delimeter);
            writeLogFile("[INFO] Header: " + header.toString());
            writeLogFile("[INFO] Header: " + header.getName());
            writeLogFile("[INFO] Header: " + header.getValue());
        }

        return headersString.toString();
    }

    static public String getHost(HarHeader header) {
        if ( "Host".equals(header.getName()) )
            hostName = header.getValue();
        return hostName;
    }

    static public String getPath(String url) {
        String pathUrl = url.substring(url.indexOf(hostName) + hostName.length(), url.length());
        
        return pathUrl;
    }

    // for debug purpose - you should recompile jar
    static void writeLogFile(String text) {
        boolean isDebug = false;
        if (isDebug) {
            String logFile = "/tmp/aa_har_logs/har_plugin.log";
            boolean append = true;
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(logFile, append), "utf-8"))) {
                    writer.write(text);
                    writer.write(delimeter);
            } catch (Exception e) {
                //return new ArrayList<IHttpRequestResponse>();
            }
        }
    }

    // tool encoding
    public static String encodeFromISO88591toUTF8(String isoString) {
        String utf8String = "";
        if (null != isoString) {
            try {
                utf8String = new String(isoString.getBytes("ISO-8859-1"), "UTF-8");          // "ISO-8859-1" from ASCII
            } catch (UnsupportedEncodingException ex) {
                // nothing
            }
        }
        return utf8String;
    }
}