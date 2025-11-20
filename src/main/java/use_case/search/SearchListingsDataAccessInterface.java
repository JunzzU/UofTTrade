package use_case.search;
import entity.Listing;

import java.util.List;
public interface SearchListingsDataAccessInterface {
    List<Listing> findByKeywordAndCategory(String keyword, String categoryName);
    /**
     * Returns listings whose name contains the provided keyword (case-insensitive).
     */
    List<Listing> findByKeyword(String keyword);

    /**
     * Returns listings that belong to the provided category name.
     */
    List<Listing> findByCategory(String categoryName);

    /**
     * Returns all known category names.
     */
    List<String> getAllCategories();
}
