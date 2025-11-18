package use_case.search;

public interface SearchListingsOutputBoundary {
    /**
     * Populates the UI with successful results (including fallback context when applicable).
     *
     * @param outputData normalized payload containing listings and fallback info
     */
    void prepareSuccessView(SearchListingsOutputData outputData);

    /**
     * Notifies the UI that nothing could be found even after keyword/category fallback.
     *
     * @param errorMessage explanation to display
     * @param keyword      keyword that failed to match
     * @param categoryName category that was attempted
     */
    void prepareFailView(String errorMessage, String keyword, String categoryName);
}
