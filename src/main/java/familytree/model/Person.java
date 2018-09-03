package familytree.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class Person {

    String name;
    Optional<String> partnerName;
    char sex;
    Person parent;
    List<Person> children;

    Person(String name, String partnerName, char sex){
        this.name = name;
        this.partnerName = Optional.ofNullable(partnerName);
        this.sex = sex;
        this.parent = null;
        this.children = new ArrayList<>();
    }

    public Person addChild(Person child){
        this.children.add(child);
        child.setParent(this);
        return this;
    }

    private void setParent(Person parent){
        this.parent = parent;
    }

    public Person getParent(){
        return (this.parent);
    }

    public List<Person> getChildren(){
        return this.children;
    }
}
