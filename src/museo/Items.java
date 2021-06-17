package museo;

public class Items {
    private Integer id;
    private String name;
    private String description;
    private String image;
    private float price;
    private String eraName;
    private String biblioTitle;
    private String biblioLink;
    private String tagName;
    private String subStateName;
    private String stateName;


    public Items(Integer id, String name, String description, String image, float price, String eraName, String biblioTitle,String biblioLink, String tagName, String subStateName, String stateName) {
        this.id           = id;
        this.name         = name;
        this.description  = description;
        this.image        = image;
        this.price        = price;
        this.eraName      = eraName;
        this.biblioTitle  = biblioTitle;
        this.biblioLink   = biblioLink;
        this.tagName      = tagName;
        this.subStateName = subStateName;
        this.stateName    = stateName;
    }

    public Integer getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public float getPrice() {
        return price;
    }

    public String getEraName() { return eraName; }

    public String getBiblioTitle() { return biblioTitle; }

    public String getBiblioLink() { return biblioLink; }

    public String getTagName() { return tagName;  }

    public String getSubStateName() { return subStateName; }

    public String getStateName() {  return stateName;  }
}
