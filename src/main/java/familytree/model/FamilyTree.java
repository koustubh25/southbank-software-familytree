package familytree.model;

import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class FamilyTree{

    public static Map<String,Person> node;

    public FamilyTree(){

        node = new HashMap<>();
        this.createPersons();
        this.establishRelationships();
    }

    private void createPersons(){
        node.put("Shan", new Person("Shan", "Anga", 'M'));
        node.put("Ish", new Person("Ish", null, 'M'));
        node.put("Chit", new Person("Chit", "Ambi", 'M'));
        node.put("Vich", new Person("Vich", "Lika", 'M'));
        node.put("Saty", new Person("Saty", "Vya", 'F'));
        node.put("Drita", new Person("Drita", "Jaya", 'M'));
        node.put("Vrita", new Person("Vrita", null, 'M'));
        node.put("Vila", new Person("Vila", "Jnki", 'M'));
        node.put("Chika", new Person("Chika", "Kpila", 'F'));
        node.put("Satvy", new Person("Satvy", "Asva", 'F'));
        node.put("Savya", new Person("Savya", "Krpi", 'M'));
        node.put("Saayan", new Person("Saayan", "Mina", 'M'));
        node.put("Jata", new Person("Jata", null, 'M'));
        node.put("Driya", new Person("Driya", "Mnu", 'F'));
        node.put("Lavnya", new Person("Lavnya", "Gru", 'F'));
        node.put("Kriya", new Person("Kriya", "null", 'M'));
        node.put("Misa", new Person("Misa", null, 'M'));

    }

    //only create parent child relationship
    private void establishRelationships(){

        Person Shan = node.get("Shan");
        Shan.addChild(node.get("Ish"))
                .addChild(node.get("Chit"))
                .addChild(node.get("Vich"))
                .addChild(node.get("Saty"));

        Person chit = node.get("Chit");
        chit.addChild(node.get("Drita"))
                .addChild(node.get("Vrita"));

        Person vich = node.get("Vich");
        vich.addChild(node.get("Vila"))
                .addChild(node.get("Chika"));

        Person saty = node.get("Saty");
        saty.addChild(node.get("Satvy"))
                .addChild(node.get("Savya"))
                .addChild(node.get("Saayan"));

        Person drita = node.get("Drita");
        drita.addChild(node.get("Jata"))
                .addChild(node.get("Driya"));

        Person vila = node.get("Vila");
        vila.addChild(node.get("Lavnya"));

        Person savya = node.get("Savya");
        savya.addChild(node.get("Kriya"));

        Person saayan = node.get("Saayan");
        saayan.addChild(node.get("Misa"));

    }

    public List<String> getchildren(String name){
        Person person = node.get(name);
        return person.getChildren().stream().map(child -> child.getName()).collect(Collectors.toList());

    }

    private  Optional<Person> findNameInPartners(String name) {

        Optional<Person> result = Optional.empty();
        for (Map.Entry<String, Person> person : node.entrySet()) {
            Optional<String> partnerName = person.getValue().getPartnerName();

            if (partnerName.isPresent()) {
                if (partnerName.get().equals(name)) {
                    result = Optional.of((Person) person.getValue());
                    break;
                }
            }
        }
        return result;
    }

    private String getParent(String name, char parentType){
        Person parents = node.get(name).getParent();
        return parents.sex == parentType ? parents.name : parents.partnerName.get();
    }

    public String getFather(String name){
        return getParent(name, 'M');
    }

    public String getMother(String name){
        return getParent(name, 'F');
    }

    public List<String> getSons(String name){
        Person person = node.get(name);
        Optional<Person> partner = Optional.empty();
        if(person == null){
            partner = findNameInPartners(name);
            if(!partner.isPresent())
                return new ArrayList<>();
            person = partner.get();
        }

        return person.getChildren().stream()
                .filter(child -> child.sex == 'M')
                .map(child -> child.name)
                .collect(Collectors.toList());
    }

    public List<String> getDaughters(String name){
        Person person = node.get(name);
        Optional<Person> partner = Optional.empty();

        if(person == null){
            partner = findNameInPartners(name);
            if(!partner.isPresent())
                return new ArrayList<>();
            person = partner.get();
        }

        return person.getChildren().stream()
                .filter(child -> child.sex == 'F')
                .map(child -> child.name)
                .collect(Collectors.toList());
    }

    public List<String> getBrothers(String name){
        Person person = node.get(name);
        List<String> brothers = person.getParent()
                .getChildren().stream()
                .filter(child -> child.getSex() == 'M')
                .map(child -> child.name)
                .collect(Collectors.toList());

        if(person.getSex() == 'M')
            brothers.remove(name);

        return brothers;
    }

    public List<String> getSisters(String name){
        Person person = node.get(name);
        List<String> sisters =  person.getParent()
                .getChildren().stream()
                .filter(child -> child.sex == 'F')
                .map(child -> child.name)
                .collect(Collectors.toList());

        if(person.getSex() == 'F')
            sisters.remove(name);

        return sisters;
    }

    public List<String> getCousins(String name){
        Person person = node.get(name);

        List<List<Person>> cousins = new ArrayList<>();

        List<Person> parentsSiblings = person.getParent().getParent().getChildren().stream()
                .filter(child -> !child.getName().equals(person.getParent().getName()))
                .collect(Collectors.toList());

        parentsSiblings.stream().forEach(sibling -> {
            cousins.add(sibling.getChildren());
        });

        return cousins.stream().
                flatMap(child -> child.stream())
                .map(ch -> ch.getName())
                .collect(Collectors.toList());
    }


    private List<String> getSpouseBrothers(String name){
        List<String> result = new ArrayList<>();
        Optional<Person> person = findNameInPartners(name);

        if(person.isPresent()){
            Person per = person.get();
            result = getBrothers(per.name);
        }
        return result;
    }

    private List<String> getSpouseSisters(String name){
        List<String> result = new ArrayList<>();
        Optional<Person> person = findNameInPartners(name);

        if(person.isPresent()){
            Person per = person.get();
            result = getSisters(per.name);
        }
        return result;
    }

    private List<String> getHusbandsOfSiblings(String name){
        List<String> result = new ArrayList<>();

        Person person = node.get(name);
        if(person != null){
            result = person.getParent()
                    .getChildren()
                    .stream().
                    filter(child -> child.getSex() == 'F' && !child.getName().equals(name) && child.getPartnerName().isPresent() && child.getPartnerName().get() != null)
                    .map(child -> child.getPartnerName().get())
                    .collect(Collectors.toList());

        }
        return result;

    }

    private List<String> getWivesOfSiblings(String name){
        List<String> result = new ArrayList<>();

        Person person = node.get(name);
        if(person != null){
            result = person.getParent()
                    .getChildren()
                    .stream().
                            filter(child -> child.getSex() == 'M' && !child.getName().equals(name) && child.getPartnerName().isPresent() && child.getPartnerName().get() != null)
                    .map(child -> child.getPartnerName().get())
                    .collect(Collectors.toList());

        }
        return result;

    }

    public List<String> getBrothersInLaw(String  name){

        List<String> spouseBrothers = getSpouseBrothers(name);

        List<String> husbandsOfSiblings = getHusbandsOfSiblings(name);

        return Stream.concat(spouseBrothers.stream(), husbandsOfSiblings.stream()).collect(Collectors.toList());

    }

    public List<String> getSistersInLaw(String name){

        List<String> spouseSisters = getSpouseSisters(name);

        List<String> wivesOfSiblings = getWivesOfSiblings(name);

        return Stream.concat(spouseSisters.stream(), wivesOfSiblings.stream()).collect(Collectors.toList());

    }

    public List<String> getMaternalAunt(String name){

        // Mother's sisters and mother's sister in law
        Person person = node.get(name);
        List<String> mothersSisters = new ArrayList<>();
        List<String> mothersSistersInLaw = new ArrayList<>();
        if(person.getParent().getSex() == 'F'){
            mothersSisters = getSisters(person.getParent().getName());
            mothersSistersInLaw = getSistersInLaw(person.getParent().getName());
        }

        return Stream.concat(mothersSisters.stream(), mothersSistersInLaw.stream()).collect(Collectors.toList());

    }


    public List<String> getMaternalUncle(String name){

        // Mother's brothers and mother's brother in law
        Person person = node.get(name);
        List<String> mothersBrothers = new ArrayList<>();
        List<String> mothersBrothersInLaw = new ArrayList<>();
        if(person.getParent().getSex() == 'F'){
            mothersBrothers = getBrothers(person.getParent().getName());
            mothersBrothersInLaw = getBrothersInLaw(person.getParent().getName());
        }

        return Stream.concat(mothersBrothers.stream(), mothersBrothersInLaw.stream()).collect(Collectors.toList());

    }

    public List<String> getPaternalUncle(String name){

        // Father's brothers and father's brother in law
        Person person = node.get(name);
        List<String> fathersBrothers = new ArrayList<>();
        List<String> fathersBrothersInLaw = new ArrayList<>();
        if(person.getParent().getSex() == 'M'){
            fathersBrothers = getBrothers(person.getParent().getName());
            fathersBrothersInLaw = getBrothersInLaw(person.getParent().getName());
        }

        return Stream.concat(fathersBrothers.stream(), fathersBrothersInLaw.stream()).collect(Collectors.toList());

    }



    public List<String> getPaternalAunt(String name){

        // Father's brothers and father's brother in law
        Person person = node.get(name);
        List<String> fatherssisters = new ArrayList<>();
        List<String> fatherssistersInLaw = new ArrayList<>();
        if(person.getParent().getSex() == 'M'){
            fatherssisters = getSisters(person.getParent().getName());
            fatherssistersInLaw = getSistersInLaw(person.getParent().getName());
        }

        return Stream.concat(fatherssisters.stream(), fatherssistersInLaw.stream()).collect(Collectors.toList());

    }

    public List<String> getGrandDaughters(String name){
        Person person = node.get(name);
        List<List<String>> daughtersOfDaughters = new ArrayList<>();
        List<List<String>> daughtersOfSons = new ArrayList<>();

        getDaughters(name).forEach(daughter -> {
            daughtersOfDaughters.add(node.get(daughter).getChildren().stream()
                    .filter(child -> child.getSex() == 'F')
                    .map(child -> child.getName()).collect(Collectors.toList()));
        });

        getSons(name).forEach(daughter -> {
            daughtersOfSons.add(node.get(daughter).getChildren().stream()
                    .filter(child -> child.getSex() == 'F')
                    .map(child -> child.getName()).collect(Collectors.toList()));
        });

//        return Stream.concat(daughtersOfDaughters.stream(), daughtersOfSons.stream()).collect(Collectors.toList());


        List<String> dod = daughtersOfDaughters.stream().flatMap(daughter -> daughter.stream()).collect(Collectors.toList());
        List<String> dos = daughtersOfSons.stream().flatMap(daughter -> daughter.stream()).collect(Collectors.toList());

        return Stream.concat(dod.stream(), dos.stream()).collect(Collectors.toList());


    }

    public boolean validateNames(String name){

        if(node.get(name) != null || findNameInPartners(name).isPresent())
            return true;
        return false;

    }

    public boolean validateRelations(String relation){
        String[] validRelations = {"paternaluncles", "maternaluncles", "paternalaunt", "maternalaunt", "sisterinlaw", "brotherinlaw", "cousins", "father", "mother", "children", "sons", "daughters", "brothers", "sisters", "granddaughter"};
        return Stream.of(validRelations).anyMatch(rel -> relation.equals(rel));

    }


}
