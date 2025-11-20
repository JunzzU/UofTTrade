package interface_adapter.messaging;

import interface_adapter.ViewModel;

import javax.swing.*;

public class MessagingViewModel extends ViewModel<MessagingViewModel.State> {

    public static final String VIEW_NAME = "Messaging";

    public static class State {
        public String title;
        public String gmailUrl;
        public String error;
    }

    public MessagingViewModel() {
        super(VIEW_NAME);
        State intialState = new State();
        intialState.title = "";
        intialState.gmailUrl = "";
        intialState.error = "";
        setState(intialState);
    }

}
