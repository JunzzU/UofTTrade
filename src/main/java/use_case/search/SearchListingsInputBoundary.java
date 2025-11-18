package use_case.search;

/**
 * Input boundary for the search listings use case.
 */
public interface SearchListingsInputBoundary {

    /**
     * Handles a search request that may include both keyword and category filters.
     *
     * @param inputData raw values passed in from the controller
     */
    void execute(SearchListingsInputData inputData);
}
