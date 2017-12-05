/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CurrencyConvertor;
import exceptions.EntityAlreadyExistsException;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.Conversation;
import javax.inject.Inject;
import javax.inject.Named;
import model.Currency;

/**
 *
 * @author Griffone
 */
@Named("admin_view")
@Stateless
public class AdminView {

    @EJB
    private CurrencyConvertor convertor;
    private String currencyName;
    private double currencyRate;
    private String errorMsg;
    @Inject
    private Conversation conversation;
    private Exception exception;
    
    private void startConversation() {
        if (conversation.isTransient())
            conversation.begin();
    }
    
    private void stopConversation() {
        if (!conversation.isTransient())
            conversation.end();
    }
    
    public String getCurrencyName() {
        return currencyName;
    }
    
    public void setCurrencyName(String name) {
        this.currencyName = name;
    }
    
    public double getCurrencyRate() {
        return currencyRate;
    }
    
    public void setCurrencyRate(double rate) {
        this.currencyRate = rate;
    }
    
    public void addCurrency() {
        try {
            startConversation();
            exception = null;
            if (currencyName == null || currencyName.length() == 0)
                errorMsg = "Please select a currency name!";
            else if (currencyRate == 0.0)
                errorMsg = "Please select a currency rate!";
            else {
                errorMsg = null;
                try {
                    convertor.createCurrency(currencyName, currencyRate);
                } catch (EntityAlreadyExistsException ex) {
                    errorMsg = "Currency already exists!\nThe convertor does not support dynamic currencies!\nTo update a currency please delete it first!";
                }
            }
            stopConversation();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public String getErrorMessage() {
        return errorMsg;
    }
    
    public List<Currency> getCurrencies() {
        return convertor.getCurrencies();
    }
    
    public void deleteCurrency(long id) {
        try {
            exception = null;
            startConversation();
            convertor.deleteCurrency(id);
            stopConversation();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public boolean getSuccess() {
        return exception != null;
    }
    
    public Exception getException() {
        return exception;
    }
    
    private void handleException(Exception e) {
        stopConversation();
        exception = e;
        exception.printStackTrace(System.err);
    }
}
