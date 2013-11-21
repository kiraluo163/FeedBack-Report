/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mlloganaysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author itemize
 */
public class MLlogAnaysis {

    /**
     * @param args the command line arguments
     */
    private static String targetDirPath = "C:/Users/itemize/Desktop/Jing Luo ML files/ml log analysis/log"; 
    private static String logFileNamePrefix = "ml.log";
    private List<File> logFiles= new ArrayList<>(); 
    private AnalysisSummary summary = new AnalysisSummary();
    private void loadLogFiles(String target_log_path)
    {
        logFiles.clear();
        File logFilesDir = new File(target_log_path);
        if (logFilesDir.isDirectory())
        {
            for (File f:logFilesDir.listFiles())
            {
                String fname = f.getName();
                if (fname.startsWith(logFileNamePrefix))
                {
                    logFiles.add(f);
                }
            }
        }
        
    }
    public String toPercentage(double a, int place)
    {
         
        long factor = (long)Math.pow(10,(double)(2+place));
        a *=factor;
        a = Math.round(a);
        return Double.toString(a/100.0) + "%";
    }
    public void printAnalysisSummary()
    {
        System.out.println("print out feedback message tracking report.");
        System.out.println("--------------------------------------------");
        System.out.print(summary.getNumOfMsgReceived() + " messages received; ");
        System.out.print(summary.getNumOfTrainSampleCreated() + " of them are created as training samples.");
        System.out.print('\t' + toPercentage((double)summary.getNumOfTrainSampleCreated() / (double)summary.getNumOfMsgReceived() , 2));
        System.out.print('\n');
        System.out.print(summary.getNumOfBadTrainSample() + " are bad training samples, because L3 receipt is treated as L1.");
        System.out.print('\t' + toPercentage((double)summary.getNumOfBadTrainSample() / (double)summary.getNumOfMsgReceived() , 2));
        System.out.print('\n');
        System.out.print(summary.getNumOfCorrectOneOffLabeling() +" current feedback oneoff model is able to label correct, so there is no need to create a trainig sample.");
        System.out.print('\t' + toPercentage((double)summary.getNumOfCorrectOneOffLabeling() / (double)summary.getNumOfMsgReceived(), 2));
        System.out.print('\n');
        System.out.print(summary.getNumOfCorrectGeneralModelLabeling() +" current feedback general model is able to label correct, so create the oneoff model for that merchant.");
        System.out.print('\t' + toPercentage((double)summary.getNumOfCorrectGeneralModelLabeling() / (double)summary.getNumOfMsgReceived(), 2));
        System.out.print('\n');
        System.out.print(summary.getNumOfMappingFails() +" are failed in mapping.");
        System.out.print('\t' + toPercentage((double)summary.getNumOfMappingFails() / (double)summary.getNumOfMsgReceived(), 2));
        System.out.print('\n');
        System.out.print(summary.getNumOfSuspectTraining() +" are suspect training sample, need to be reviewed.");
        System.out.print('\t' + toPercentage((double)summary.getNumOfSuspectTraining() / (double)summary.getNumOfMsgReceived(), 2));
        System.out.print('\n');
        System.out.print(summary.getNumOfEmailDomainFiltered() +" contains the black email domain (gmail, yahoo, etc), and those are filtered out");
        System.out.print('\t' + toPercentage((double)summary.getNumOfEmailDomainFiltered() / (double)summary.getNumOfMsgReceived(), 2));
        System.out.print('\n');
        System.out.print(summary.getNumOfException() +" are throwing exceptions");
        System.out.print('\t' + toPercentage((double)summary.getNumOfException() / (double)summary.getNumOfMsgReceived(), 2));
        System.out.print('\n');
        System.out.print(summary.getNumOfNoGrandTotal() +" do not have grand total");
        System.out.print('\t' + toPercentage((double)summary.getNumOfNoGrandTotal() / (double)summary.getNumOfMsgReceived(), 2));
        System.out.print('\n');
    }
    private void readLogFile(File f)
    {
        BufferedReader br = null; 
        try {

            String sCurrentLine;

            br = new BufferedReader(new FileReader(f));
            boolean messageProcessEnd = false;
            boolean messageProcessBegin = false;
            boolean getInfo = false;
            int cnt = 0;
            while ((sCurrentLine = br.readLine()) != null) {               
                if (!messageProcessBegin && sCurrentLine.contains("Message received with guid :"))
                {
                    messageProcessBegin = true;
                    messageProcessEnd  = false;
                    summary.incrNumOfMsgReceived();
                    getInfo = false;                    
                }    
                if (messageProcessBegin && !messageProcessEnd)
                {
                    if (sCurrentLine.contains("Completed live execution flow for receipt GUID"))
                    {
                        messageProcessEnd = true;
                        messageProcessBegin = false;
                    }
                    else if(!getInfo){
                        if (sCurrentLine.contains("it is not a good training sample because L3 receipt is treated as L1"))
                        {
                            summary.incrNumOfBadTrainSample();
                            getInfo = true;
                        }
                        else if (sCurrentLine.contains("label very correct"))
                        {
                            if (sCurrentLine.contains("OneOff")){
                                summary.incrNumOfCorrectOneOffModelLabeling();
                            }else{
                                summary.incrNumOfCorrectGeneralModelLabeling();
                            }
                            
                            getInfo = true;
                        }
                        else if(sCurrentLine.contains("move to the suspect folder")){
                            summary.incrNumOfSuspectTraining();
                            getInfo = true;
                        }
                        else if(sCurrentLine.contains("it is filtered out and will not be created as oneoff")){
                            summary.incrNumOfEmailDomainFiltered();
                            getInfo = true;
                        }
                        else if (sCurrentLine.contains("returning experiment_path"))
                        {
                            summary.incrNumOfTrainSampleCreated();
                            getInfo = true;
                        }
                        else if (sCurrentLine.contains("mapping fails, try next one"))
                        {
                            summary.incrNumOfMappingFails();
                            getInfo = true;
                        }
                        else if (sCurrentLine.contains("There is no grand total in this receipt"))
                        {
                            summary.incrNumOfNoGrandTotal();
                            getInfo = true;
                        }
                        else if (sCurrentLine.contains("com.itemize.ml.core.Exception.MLException: Exception")){
                            summary.incrNumOfException();
                        }
                        else if (sCurrentLine.contains("ML processing Failed. Sending failure message to transaction complete."))
                        {
                            summary.incrNumOfException();
                            messageProcessEnd = true;
                            messageProcessBegin = false;
                        }
                        
                        
                    }
                    
                }
                cnt++;
            }

        } catch (IOException e) {
            System.out.println(e);
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                System.out.println(ex);
            }
        }
    }
    public void run(String target_log_path)
    {
        loadLogFiles(target_log_path);
        for (File f:logFiles)
        {
           readLogFile(f); 
        }
        printAnalysisSummary();
        
    }
    public void run(){
        run(targetDirPath);
    }
    public static void main(String[] args) {
        // TODO code application logic here
        
        if ( args.length == 0 || args[0] == null || args[0].isEmpty()){
            new MLlogAnaysis().run();
        }else{
            String target_log_path = args[0]; 
            new MLlogAnaysis().run(target_log_path);
        }
        
    }
}
