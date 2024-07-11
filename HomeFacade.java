import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class HomeFacade {
    private Home home;

    public HomeFacade(Home home) {
        this.home = home;
    }

    public void showHomePanel() {
        home.showMainPanel();
    }

    public void showCourseSelectionPanel() {
        home.showCourseSelectionPanel();
    }

    public void showFinancialPanel() {
        home.showFinancialPanel();
    }

    public void showSelectedPanel() {
        home.showSelectedPanel();
    }

    public void showInvoicePanel() {
        home.showInvoicePanel();
    }

    public void clearCacheFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("database/cache.txt"))) {
            writer.write(""); // Write an empty string to clear the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearFeeCacheFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("database/feecache.txt"))) {
            writer.write(""); // Write an empty string to clear the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
