package interface_adapter.messaging;

import use_case.messaging.MessagingInputBoundary;
import use_case.messaging.MessagingInputData;

public class MessagingController {
    public final MessagingInputBoundary interactor;
    public MessagingController(MessagingInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void createGmailComposeLink(String name, String email) {
        MessagingInputData inputData = new MessagingInputData(name, email);
        interactor.execute(inputData);
    }
}