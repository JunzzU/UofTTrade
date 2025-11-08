package entity;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class Listing {
    private String name;
    private BufferedImage photo;
    private List<Category> categories;
    private final User owner;

    public Listing(String name, BufferedImage photo, List<Category> categories, User owner) {
        this.name = name;
        this.photo = photo;
        this.categories = categories;
        this.owner = owner;
    }

    public void change_category(List<Category> new_categories) {
        this.categories = new_categories;
    }

    public void change_name(String new_name) {
        this.name = new_name;
    }

    public void change_img(BufferedImage new_img) {
        this.photo = new_img;
    }

    public List<Category> get_categories() { return categories; }

    public String get_name() { return name; }

    public BufferedImage get_img() { return photo; }

    public User get_owner() { return owner; }
}
