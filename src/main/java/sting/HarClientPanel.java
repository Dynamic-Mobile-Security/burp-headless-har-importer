package sting;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URISyntaxException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import burp.IBurpExtender;
import burp.IBurpExtenderCallbacks;
import burp.IContextMenuInvocation;
import burp.IHttpRequestResponse;
import burp.IParameter;

import sting.imports.RAYImport;
import sting.imports.RAYRequestResponse;

public class HarClientPanel implements IBurpExtender {

    public static String name = "Har Import Sitemap Cli";
    public static IBurpExtenderCallbacks callbacks;
    public static burp.IExtensionHelpers helpers;
    
    // Requirement
    private static final long serialVersionUID = 7415151000560298154L;

    // private final burp.IBurpExtenderCallbacks callbacks;
    // private final burp.IExtensionHelpers helpers;
    private final boolean checkFakeParam = true;
    private final String paramname = "stingrayParamToExclude";

	private PrintWriter stdout;
    //private RAYImport rayimport;
		
	@Override
	public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks)
	{
        // keep a reference to our callbacks object
        HarClientPanel.callbacks = callbacks;
        HarClientPanel.helpers = callbacks.getHelpers();
        
        stdout = new PrintWriter(HarClientPanel.callbacks.getStdout(), true);
        stdout.println("Loaded [" + HarClientPanel.name + "] Extension");
        stdout.println("");
        stdout.println("░░                                                                      ");
        stdout.println("  ▒▒░░                                                                  ");
        stdout.println("      ▓▓                                                                ");
        stdout.println("        ▒▒▒▒                                            ░░░░            ");
        stdout.println("          ░░▓▓░░                                    ▒▒██                ");
        stdout.println("              ▓▓▓▓░░                              ██▓▓                  ");
        stdout.println("                ░░████░░                      ▒▒██▓▓▒▒                  ");
        stdout.println("                    ▒▒▓▓▓▓░░░░░░            ░░████▓▓                    ");
        stdout.println("                      ░░▓▓▓▓▓▓▓▓▒▒░░  ░░░░▒▒▓▓▓▓▓▓▒▒                    ");
        stdout.println("                ▒▒░░░░  ░░▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▒▒▒▒▓▓▓▓▒▒                  ");
        stdout.println("                    ▒▒██▓▓░░▒▒▓▓██▓▓▒▒████████▓▓▓▓▓▓▒▒▓▓░░              ");
        stdout.println("                      ░░▓▓▒▒▒▒▒▒▓▓▒▒▒▒▒▒██████████▓▓▓▓▒▒▒▒▒▒            ");
        stdout.println("                          ▓▓▓▓▒▒▒▒▒▒▒▒▒▒▓▓▓▓▓▓██████▓▓▒▒▒▒▒▒▒▒          ");
        stdout.println("                            ▓▓▓▓▒▒░░░░▒▒▒▒▒▒▒▒▓▓██████▓▓▓▓▒▒▒▒▒▒        ");
        stdout.println("                            ░░▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▓▓████████▓▓▓▓▒▒▒▒░░      ");
        stdout.println("                              ▒▒▓▓▓▓▓▓▓▓▒▒▒▒░░▒▒▒▒▓▓██████▓▓▓▓▒▒▓▓      ");
        stdout.println("                                ▒▒▓▓▓▓▓▓▒▒▓▓▒▒▒▒▒▒▒▒▓▓████████▓▓▒▒░░    ");
        stdout.println("                                ████▓▓▒▒████▓▓▒▒░░▒▒▒▒▒▒▓▓████▓▓▓▓▓▓    ");
        stdout.println("                              ▒▒▓▓    ▒▒▓▓██▓▓▓▓▓▓▒▒▒▒▒▒▓▓▓▓████▓▓▓▓▒▒  ");
        stdout.println("                              ▓▓        ▒▒██▓▓▒▒▒▒▓▓▓▓▓▓▒▒▓▓▓▓████▓▓▓▓  ");
        stdout.println("                                          ██▓▓▒▒▓▓░░░░▒▒▓▓▒▒▓▓▓▓████▓▓▒▒");
        stdout.println("                                          ▓▓▓▓    ▓▓▒▒░░▒▒▒▒▒▒▓▓████▓▓▓▓");
        stdout.println("                                            ▓▓      ░░▓▓░░▒▒▒▒▒▒▓▓████▓▓");
        stdout.println("                                            ▓▓          ▓▓▒▒▒▒▓▓▒▒████▓▓");
        stdout.println("                                              ▒▒          ▒▒░░▓▓▒▒████▓▓");
        stdout.println("                                                            ▓▓▒▒▓▓▓▓██▓▓");
        stdout.println("                                                            ▒▒▒▒▓▓▓▓██▓▓");
        stdout.println("                                                            ░░░░▓▓▓▓██▒▒");
        stdout.println("                                              ░░            ░░▒▒▓▓▓▓▓▓  ");
        stdout.println("                                              ▒▒            ▒▒▒▒▓▓██▓▓  ");
        stdout.println("                                              ▒▒            ▓▓▒▒▓▓▓▓    ");
        stdout.println("                                            ▒▒▒▒        ░░▓▓██▓▓██      ");
        stdout.println("                                            ▓▓▓▓    ▓▓▓▓▓▓▓▓▒▒██        ");
        stdout.println("                                            ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓            ");
        stdout.println("                                          ▓▓▓▓▒▒  ▓▓  ▓▓▓▓              ");
        stdout.println("                                          ▓▓▓▓        ▓▓                ");
        stdout.println("                                          ▓▓        ▒▒▒▒                ");
        stdout.println("                                        ▓▓▒▒                            ");
        stdout.println("                                      ░░▓▓                              ");
        stdout.println("                                                                        ");
        stdout.println("                                                                        ");
        stdout.println("                                                                        ");
        stdout.println("                                                                        ");
        stdout.println("                                                                        ");
        stdout.println("      ░░░░  ░░░░░░░░  ░░5w02df15h░░░░53cu217y░░░░░░░░  ░░░░  ░░░░       ");
        
        // set our extension name
        callbacks.setExtensionName(HarClientPanel.name);
        
        // register context menu
        //callbacks.registerContextMenuFactory(new ContextMenu(helpers, stdout));

        // todo
        try (FileOutputStream fileOut = new FileOutputStream(new File("file_log_extension.log"), true)) {
        	PrintWriter writer = new PrintWriter(fileOut);
			String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
			writer.println("Extenstion loaded: " + timeStamp);
			writer.close();	
		} catch (IOException e) {
		    //handle exception
		}

        // TODO --- 
        //this.rayimport = new RAYImport();
        ArrayList<IHttpRequestResponse> rs = sting.imports.RAYImport.loadHAR(checkFakeParam);
        sendToSitemap(rs);

	}

    public void sendToSitemap(ArrayList<IHttpRequestResponse> rs) {
        boolean doTrick = this.checkFakeParam;
        this.sendToSitemap(rs, doTrick);
    }

    public void sendToSitemap(ArrayList<IHttpRequestResponse> rs, boolean doTrick) {
        Iterator<IHttpRequestResponse> i = rs.iterator();
        while (i.hasNext()) {
            IHttpRequestResponse r = i.next();
            this.sendToSitemap(r, doTrick);
        }
    }

    public void sendToSitemap(IHttpRequestResponse r) {
        boolean doTrick = this.checkFakeParam;
        this.sendToSitemap(r, doTrick);
    }

    public void sendToSitemap(IHttpRequestResponse r, boolean doTrick) {
        RAYRequestResponse rr = new RAYRequestResponse(r);

        // We add the fake parameter if enabled, to add all requests.
        if (doTrick) {
            final String uuid = UUID.randomUUID().toString();
            IParameter p = HarClientPanel.helpers.buildParameter(this.paramname, uuid, IParameter.PARAM_URL);

            byte[] b = HarClientPanel.helpers.addParameter(rr.getRequest(), p);
            rr.setRequest(b);
        }

        // Add resulting request/response to SiteMap
        HarClientPanel.callbacks.addToSiteMap(rr);
    }
}