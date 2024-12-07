package comp.Rmi.model;



import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RevenueEntry {
    private final SimpleStringProperty period;
    private final SimpleFloatProperty revenue;
    private final SimpleIntegerProperty year;

    public RevenueEntry(String period, float revenue) {
        this.period = new SimpleStringProperty(period);
        this.revenue = new SimpleFloatProperty(revenue);
        this.year = new SimpleIntegerProperty(0);
    }

    public RevenueEntry(int year, float revenue) {
        this.period = new SimpleStringProperty("");
        this.revenue = new SimpleFloatProperty(revenue);
        this.year = new SimpleIntegerProperty(year);
    }

    public String getPeriod() {
        return period.get();
    }

    public SimpleStringProperty periodProperty() {
        return period;
    }

    public float getRevenue() {
        return revenue.get();
    }

    public SimpleFloatProperty revenueProperty() {
        return revenue;
    }

    public int getYear() {
        return year.get();
    }

    public SimpleIntegerProperty yearProperty() {
        return year;
    }
}


