package int221.backend.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="colors")
public class Color {
    @Id private int colorId;
    private String colorName;
    private String colorCode;

    public Color() {
    }

    public Color(int colorId, String colorName, String colorCode) {
        this.colorId = colorId;
        this.colorName = colorName;
        this.colorCode = colorCode;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public String getColorName() {
        return colorName;
    }

    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }
}
