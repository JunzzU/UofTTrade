package use_case.messaging;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class MessagingInteractor implements MessagingInputBoundary {

    private final MessagingUserDataAccessInterface userDataAccess;
    private final MessagingOutputBoundary presenter;

    public MessagingInteractor(MessagingUserDataAccessInterface userDataAccess,
                               MessagingOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(MessagingInputData inputData) {
        try {

            String email = inputData.getUrl();
            String name  = inputData.getName();


            String gmailUrl = buildGmailComposeUrl(
                    email,
                    "Contact " + name,
                    ""
            );


            MessagingOutputData outputData =
                    new MessagingOutputData(name, gmailUrl);

            presenter.presentSuccessView(outputData);
        } catch (Exception e) {
            presenter.presentFailureView("Failed to create Gmail link: " + e.getMessage());
        }
    }

    private String buildGmailComposeUrl(String to, String subject, String body) {
        String encTo = URLEncoder.encode(to == null ? "" : to, StandardCharsets.UTF_8);
        String encSubject = URLEncoder.encode(subject == null ? "" : subject, StandardCharsets.UTF_8);
        String encBody = URLEncoder.encode(body == null ? "" : body, StandardCharsets.UTF_8);

        return "https://mail.google.com/mail/?view=cm&fs=1"
                + "&to=" + encTo
                + "&su=" + encSubject   //
                + "&body=" + encBody;
    }
}
