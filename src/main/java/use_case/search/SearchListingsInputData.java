package use_case.search;

/**
 * Raw values provided by the UI when a search request is triggered.
 */
public class SearchListingsInputData {
    private String keyword;
    private String categoryName;

    /**
     * @param keyword      keyword
     * @param categoryName selected category label that constrains or backs up the search
     */
    public SearchListingsInputData(String keyword, String categoryName) {
        this.keyword = keyword;
        this.categoryName = categoryName;

    }
    /**
     * @return the raw keyword string (not normalized)
     */
    public String getKeyword() {
        return keyword;

    }
    /**
     * @return the raw category string chosen by the user
     */
    public String getCategoryName() {
        return categoryName;
    }

}
