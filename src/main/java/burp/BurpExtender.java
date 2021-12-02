package burp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.text.SimpleDateFormat;

import sting.HarClientPanel;

public class BurpExtender extends HarClientPanel {
    // nothing to do...
}

// public class BurpExtender implements IBurpExtender
// {
//     private static String name = "Har Import Sitemap Cli";
//     private static IBurpExtenderCallbacks callbacks;
//     private static IExtensionHelpers helpers;
// 	private PrintWriter stdout;
		
// 	@Override
// 	public void registerExtenderCallbacks(final IBurpExtenderCallbacks callbacks)
// 	{
//         // keep a reference to our callbacks object
//         BurpExtender.callbacks = callbacks;
//         BurpExtender.helpers = callbacks.getHelpers();
        
//         stdout = new PrintWriter(BurpExtender.callbacks.getStdout(), true);
//         stdout.println("Loaded [" + BurpExtender.name + "] Extension");
//         stdout.println("");
//         stdout.println("░░                                                                      ");
//         stdout.println("  ▒▒░░                                                                  ");
//         stdout.println("      ▓▓                                                                ");
//         stdout.println("        ▒▒▒▒                                            ░░░░            ");
//         stdout.println("          ░░▓▓░░                                    ▒▒██                ");
//         stdout.println("              ▓▓▓▓░░                              ██▓▓                  ");
//         stdout.println("                ░░████░░                      ▒▒██▓▓▒▒                  ");
//         stdout.println("                    ▒▒▓▓▓▓░░░░░░            ░░████▓▓                    ");
//         stdout.println("                      ░░▓▓▓▓▓▓▓▓▒▒░░  ░░░░▒▒▓▓▓▓▓▓▒▒                    ");
//         stdout.println("                ▒▒░░░░  ░░▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▒▒▒▒▓▓▓▓▒▒                  ");
//         stdout.println("                    ▒▒██▓▓░░▒▒▓▓██▓▓▒▒████████▓▓▓▓▓▓▒▒▓▓░░              ");
//         stdout.println("                      ░░▓▓▒▒▒▒▒▒▓▓▒▒▒▒▒▒██████████▓▓▓▓▒▒▒▒▒▒            ");
//         stdout.println("                          ▓▓▓▓▒▒▒▒▒▒▒▒▒▒▓▓▓▓▓▓██████▓▓▒▒▒▒▒▒▒▒          ");
//         stdout.println("                            ▓▓▓▓▒▒░░░░▒▒▒▒▒▒▒▒▓▓██████▓▓▓▓▒▒▒▒▒▒        ");
//         stdout.println("                            ░░▓▓▓▓▒▒▒▒▒▒▒▒▒▒▒▒▓▓████████▓▓▓▓▒▒▒▒░░      ");
//         stdout.println("                              ▒▒▓▓▓▓▓▓▓▓▒▒▒▒░░▒▒▒▒▓▓██████▓▓▓▓▒▒▓▓      ");
//         stdout.println("                                ▒▒▓▓▓▓▓▓▒▒▓▓▒▒▒▒▒▒▒▒▓▓████████▓▓▒▒░░    ");
//         stdout.println("                                ████▓▓▒▒████▓▓▒▒░░▒▒▒▒▒▒▓▓████▓▓▓▓▓▓    ");
//         stdout.println("                              ▒▒▓▓    ▒▒▓▓██▓▓▓▓▓▓▒▒▒▒▒▒▓▓▓▓████▓▓▓▓▒▒  ");
//         stdout.println("                              ▓▓        ▒▒██▓▓▒▒▒▒▓▓▓▓▓▓▒▒▓▓▓▓████▓▓▓▓  ");
//         stdout.println("                                          ██▓▓▒▒▓▓░░░░▒▒▓▓▒▒▓▓▓▓████▓▓▒▒");
//         stdout.println("                                          ▓▓▓▓    ▓▓▒▒░░▒▒▒▒▒▒▓▓████▓▓▓▓");
//         stdout.println("                                            ▓▓      ░░▓▓░░▒▒▒▒▒▒▓▓████▓▓");
//         stdout.println("                                            ▓▓          ▓▓▒▒▒▒▓▓▒▒████▓▓");
//         stdout.println("                                              ▒▒          ▒▒░░▓▓▒▒████▓▓");
//         stdout.println("                                                            ▓▓▒▒▓▓▓▓██▓▓");
//         stdout.println("                                                            ▒▒▒▒▓▓▓▓██▓▓");
//         stdout.println("                                                            ░░░░▓▓▓▓██▒▒");
//         stdout.println("                                              ░░            ░░▒▒▓▓▓▓▓▓  ");
//         stdout.println("                                              ▒▒            ▒▒▒▒▓▓██▓▓  ");
//         stdout.println("                                              ▒▒            ▓▓▒▒▓▓▓▓    ");
//         stdout.println("                                            ▒▒▒▒        ░░▓▓██▓▓██      ");
//         stdout.println("                                            ▓▓▓▓    ▓▓▓▓▓▓▓▓▒▒██        ");
//         stdout.println("                                            ▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓▓            ");
//         stdout.println("                                          ▓▓▓▓▒▒  ▓▓  ▓▓▓▓              ");
//         stdout.println("                                          ▓▓▓▓        ▓▓                ");
//         stdout.println("                                          ▓▓        ▒▒▒▒                ");
//         stdout.println("                                        ▓▓▒▒                            ");
//         stdout.println("                                      ░░▓▓                              ");
//         stdout.println("                                                                        ");
//         stdout.println("                                                                        ");
//         stdout.println("                                                                        ");
//         stdout.println("                                                                        ");
//         stdout.println("                                                                        ");
//         stdout.println("      ░░░░  ░░░░░░░░  ░░5w02df15h░░░░53cu217y░░░░░░░░  ░░░░  ░░░░       ");
        
//         // set our extension name
//         callbacks.setExtensionName(BurpExtender.name);
        
//         // register context menu
//         //callbacks.registerContextMenuFactory(new ContextMenu(helpers, stdout));

//         // TODO --- 
//         try (FileOutputStream fileOut = new FileOutputStream(new File("file_log_extension.log"), true)) {
//         	PrintWriter writer = new PrintWriter(fileOut);
// 			String timeStamp = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(Calendar.getInstance().getTime());
// 			writer.println("Extenstion loaded: " + timeStamp);
// 			writer.close();	
// 		} catch (IOException e) {
// 		    //handle exception
// 		}
// 	}    
// }
