package consumables;

public class GenericPowerUp {

    private String name;
    private String description;

    public GenericPowerUp(String name, String description) {
        setName(name);
        setDescription(description);
    }

    public GenericPowerUp() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}