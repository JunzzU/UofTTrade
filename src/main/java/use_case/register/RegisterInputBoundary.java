package use_case.register;

import java.io.IOException;

public interface RegisterInputBoundary {

    void execute(RegisterInputData registerInputData) throws IOException;
    void switchToLoginView();

}
