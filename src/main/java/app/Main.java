package app;

import entity.Category;
import interface_adapter.view_profile.ViewProfileController;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static String[] categoriesArray = new String[4];

    public static void main(String[] args) throws IOException {
        AppBuilder appBuilder = new AppBuilder();

        Category category1 = new Category("Select a Category");
        Category category2 = new Category("Technology");
        Category category3 = new Category("Furniture");
        Category category4 = new Category("Sports");
        categoriesArray[0] = category1.getName();
        categoriesArray[1] = category2.getName();
        categoriesArray[2] = category3.getName();
        categoriesArray[3] = category4.getName();

        JFrame application = appBuilder
                .addLoginView()
                .addRegisterView()
                .addHomepageView()
                .addCreateListingView()
                .addCreateListingUseCase()
                .addRegisterUseCase()
                .addLoginUseCase()
                .addViewProfileUseCase()
                .build();

        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}