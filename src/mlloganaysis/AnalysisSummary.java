/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mlloganaysis;

/**
 *
 * @author itemize
 */
public class AnalysisSummary {
    
    private int numOfMsgReceived;
    private int numOfTrainSampleCreated;
    private int numOfBadTrainSample;
    private int numOfMappingFails;
    private int numOfCorrectOneOffLabeling;
    private int numOfCorrectGeneralLabeling;
    private int numOfException;
    private int numOfNoGrandTotal;
    private int numOfSuspectTraining;
    private int numOfEmailDomainFiltered;
    
    public AnalysisSummary()
    {
        numOfMsgReceived = 0;
        numOfTrainSampleCreated = 0;
        numOfMappingFails = 0;
        numOfCorrectOneOffLabeling = 0;
        numOfCorrectGeneralLabeling = 0;
        numOfBadTrainSample = 0;
        numOfException = 0;
        numOfNoGrandTotal = 0;
        numOfSuspectTraining = 0;
        numOfEmailDomainFiltered = 0;
    }
    
    public int getNumOfMsgReceived() { return this.numOfMsgReceived; }
    public void setNumOfMsgReceived(int num) { this.numOfMsgReceived = num; }
    public int getNumOfTrainSampleCreated() { return this.numOfTrainSampleCreated; }
    public void setNumOfTrainSampleCreated(int num) { this.numOfTrainSampleCreated = num; }
    public int getNumOfMappingFails() { return this.numOfMappingFails; }
    public void setNumOfMappingFails(int num) { this.numOfMappingFails = num; }
    public int getNumOfCorrectGeneralModelLabeling() { return this.numOfCorrectGeneralLabeling; }
    public void setNumOfCorrectGeneralModelLabeling(int num) { this.numOfCorrectGeneralLabeling = num; }
    public int getNumOfCorrectOneOffLabeling() { return this.numOfCorrectOneOffLabeling; }
    public void setNumOfCorrectOneOffLabeling(int num) { this.numOfCorrectOneOffLabeling = num; }
    public int getNumOfBadTrainSample() { return this.numOfBadTrainSample; }
    public void setNumOfBadTrainSample(int num) { this.numOfBadTrainSample = num; }
    public int getNumOfException() { return this.numOfException; }
    public void setNumOfException(int num) { this.numOfException = num; }
    public int getNumOfNoGrandTotal() { return this.numOfNoGrandTotal; }
    public void setNumOfNoGrandTotal(int num) { this.numOfNoGrandTotal = num; }
    public int getNumOfSuspectTraining() { return this.numOfSuspectTraining; }
    public void setNumOfSuspectTraining(int num) { this.numOfSuspectTraining = num; }
    public int getNumOfEmailDomainFiltered() { return this.numOfEmailDomainFiltered; }
    public void setNumOfEmailDomainFiltered(int num) { this.numOfEmailDomainFiltered = num; }
    
    public void incrNumOfMsgReceived() { this.numOfMsgReceived++; }
    public void incrNumOfTrainSampleCreated() { this.numOfTrainSampleCreated++; }
    public void incrNumOfMappingFails() { this.numOfMappingFails++; }
    public void incrNumOfCorrectOneOffModelLabeling() { this.numOfCorrectOneOffLabeling++; }
    public void incrNumOfCorrectGeneralModelLabeling() { this.numOfCorrectGeneralLabeling++; }
    public void incrNumOfBadTrainSample() {this.numOfBadTrainSample++; } 
    public void incrNumOfException() {this.numOfException++; } 
    public void incrNumOfNoGrandTotal() {this.numOfNoGrandTotal++; } 
    public void incrNumOfSuspectTraining() { this.numOfSuspectTraining++; }
    public void incrNumOfEmailDomainFiltered() { this.numOfEmailDomainFiltered++; }
}
