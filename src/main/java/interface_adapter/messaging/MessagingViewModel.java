package interface_adapter.messaging;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MessagingViewModel {


    public static class State {
        public String title;
        public String gmailUrl;
        public String error;
    }

    private State state = new State();


    private final PropertyChangeSupport support = new PropertyChangeSupport(this);



    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }



    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }


}
