/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.CurrencyConvertor;
import java.io.Serializable;
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
@Named("convertor")
@ConversationScoped
public class UserView implements Serializable {

    @EJB
    private CurrencyConvertor convertor;
    private Currency currencyFrom;
    private Currency currencyTo;
    private double amount;
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
}
