package interface_adapter.messaging;

import use_case.messaging.MessagingInputBoundary;
import use_case.messaging.MessagingInputData;

public class MessagingController {
    public final MessagingInputBoundary interactor;
    public MessagingController(MessagingInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void createGmailComposeLink(String email, String name){
        MessagingInputData inputData = new MessagingInputData(email, name);
        interactor.execute(inputData);
    }
}

