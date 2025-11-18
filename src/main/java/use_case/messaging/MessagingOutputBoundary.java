package use_case.messaging;

import use_case.create_listing.CreateListingOutputData;

public interface MessagingOutputBoundary {
    void presentSuccessView(MessagingOutputData data);
    void presentFailureView(String errorMessage);
}

