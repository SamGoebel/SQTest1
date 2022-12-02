package com.example.sqtest;

public class CategoryModel {
    private int categoryId;
    private String categoryName;



    // constructors

    public CategoryModel(int id, String name) {
        this.categoryId = id;
        this.categoryName = name;


    }

    public CategoryModel() {
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "id=" + categoryId +
                ", name='" + categoryName;
    }

    // getters and setters
    public int getId() {
        return categoryId;
    }

    public void setId(int id) {
        this.categoryId = id;
    }

    public String getName() {
        return categoryName;
    }

    public void setName(String name) {
        this.categoryName = name;
    }


}
