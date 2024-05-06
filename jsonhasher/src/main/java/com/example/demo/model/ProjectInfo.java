package com.example.demo.model;

import java.util.Collections;
import java.util.List;

public class ProjectInfo {
    private String id;
    private String name;
    private String description;
    private String url;
    private Object datatwo; // null can be represented as Object type
    private List<String> tags;
    private List<String> maintainers;
    private Object dataone; // null can be represented as Object type
    private List<PersonDetail> people;
    private Object relationships; // Assuming it's an empty object, use Object or a specific class
    private List<Object> relatedapps; // Assuming it's an empty array, use List<Object> if no further details are known
    private List<PersonDetail> owners;
    private List<Object> categories; // Assuming it's an empty array, use List<Object> if no further details are known
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Object getDatatwo() {
        return datatwo;
    }
    public void setDatatwo(Object datatwo) {
        this.datatwo = datatwo;
    }
    public List<String> getTags() {
        return tags;
    }
    public void setTags(List<String> tags) {
        this.tags = tags;
    }
    public List<String> getMaintainers() {
        return maintainers;
    }
    public void setMaintainers(List<String> maintainers) {
        this.maintainers = maintainers;
    }
    public Object getDataone() {
        return dataone;
    }
    public void setDataone(Object dataone) {
        this.dataone = dataone;
    }
    public List<PersonDetail> getPeople() {
        return people;
    }
    public void setPeople(List<PersonDetail> people) {
        this.people = people;
    }
    public Object getRelationships() {
        return relationships;
    }
    public void setRelationships(Object relationships) {
        this.relationships = relationships;
    }
    public List<Object> getRelatedapps() {
        return relatedapps;
    }
    public void setRelatedapps(List<Object> relatedapps) {
        this.relatedapps = relatedapps;
    }
    public List<PersonDetail> getOwners() {
        return owners;
    }
    public void setOwners(List<PersonDetail> owners) {
        this.owners = owners;
    }
    public List<Object> getCategories() {
        return categories;
    }
    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

    public void normalize() {
        if (people != null) {
            Collections.sort(people);
        }
        if (owners != null) {
            Collections.sort(owners);
        }
    }

}
