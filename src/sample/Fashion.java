package sample;

public class Fashion {
    private int id;
    private String gender;
    private String masterCategory;
    private String subCategory;
    private String articleType;
    private String baseColour;
    private String season;
    private int year;
    private String usage;
    private String productDisplayName;
    private String filename;
    private String status;
    private int isFavourite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMasterCategory() {
        return masterCategory;
    }

    public void setMasterCategory(String masterCategory) {
        this.masterCategory = masterCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getBaseColour() {
        return baseColour;
    }

    public void setBaseColour(String baseColour) {
        this.baseColour = baseColour;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year > 0)
            this.year = year;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getProductDisplayName() {
        return productDisplayName;
    }

    public void setProductDisplayName(String productDisplayName) {
        this.productDisplayName = productDisplayName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(int isFavourite) {
        this.isFavourite = isFavourite;
    }

    @Override
    public String toString() {
        return "fashion{" +
                "id=" + id +
                ", gender='" + gender + '\'' +
                ", masterCategory='" + masterCategory + '\'' +
                ", subCategory='" + subCategory + '\'' +
                ", articleType='" + articleType + '\'' +
                ", baseColour='" + baseColour + '\'' +
                ", season='" + season + '\'' +
                ", year=" + year +
                ", usage='" + usage + '\'' +
                ", productDisplayName='" + productDisplayName + '\'' +
                ", filename='" + filename + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public Fashion(int id, String gender, String masterCategory, String subCategory, String articleType, String baseColour, String season, int year, String usage, String productDisplayName, String filename, String status) {
        this.id = id;
        this.gender = gender;
        this.masterCategory = masterCategory;
        this.subCategory = subCategory;
        this.articleType = articleType;
        this.baseColour = baseColour;
        this.season = season;
        this.year = year;
        this.usage = usage;
        this.productDisplayName = productDisplayName;
        this.filename = filename;
        this.status = status;

    }

}
