/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CurrencyConvertor;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Currency;

/**
 *
 * @author Griffone
 */
@Named("user_view")
@ConversationScoped
public class ConvertorView implements Serializable {

    @EJB
    private CurrencyConvertor convertor;
    private long currencyIDFrom;
    private long currencyIDTo;
    private double amountFrom;
    private double amountTo;
    @Inject
    private Conversation conversation;
    private Exception exception;
    private String messageFrom;
    private String messageTo;
    
    private void startConversation() {
        if (conversation.isTransient())
            conversation.begin();
    }
    
    private void stopConversation() {
        if (!conversation.isTransient())
            conversation.end();
    }
    
    private void handleException(Exception e) {
        stopConversation();
        e.printStackTrace(System.err);
        exception = e;
    }
    
    public boolean getSuccess() {
        return exception == null;
    }
    
    public Exception getException() {
        return exception;
    }
    
    public long getCurrencyFrom() {
        return currencyIDFrom;
    }
    
    public void setCurrencyFrom(long id) {
        this.currencyIDFrom = id;
    }
    
    public long getCurrencyTo() {
        return currencyIDTo;
    }
    
    public void setCurrencyTo(long id) {
        this.currencyIDTo = id;
    }
    
    public double getAmountFrom() {
        return amountFrom;
    }
    
    public void setAmountFrom(double amount) {
        this.amountFrom = amount;
    }
    
    public double getAmountTo() {
        return amountTo;
    }
    
    public void setAmountTo(double amount) {
        this.amountTo = amount;
    }
    
    public List<Currency> getCurrencies() {
        return convertor.getCurrencies();
    }
    
    public void convert() {
        try {
            startConversation();
            if (currencyIDFrom != 0 && currencyIDTo != 0) {
                amountTo = convertor.convert(currencyIDFrom, currencyIDTo, amountFrom);
                messageFrom = null;
                messageTo = null;
            } else
                messageFrom = "Please select currencies!";
            stopConversation();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public void convertReverse() {
        try {
            startConversation();
            if (currencyIDFrom != 0 && currencyIDTo != 0) {
                amountFrom = convertor.convert(currencyIDTo, currencyIDFrom, amountTo);
                messageFrom = null;
                messageTo = null;
            } else
                messageTo = "Please select currencies!";
            stopConversation();
        } catch (Exception e) {
            handleException(e);
        }
    }
    
    public String getMessageTo() {
        return messageTo;
    }
    
    public String getMessageFrom() {
        return messageFrom;
    }
}
