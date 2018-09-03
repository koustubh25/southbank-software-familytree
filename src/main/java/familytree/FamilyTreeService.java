package familytree;

import familytree.model.FamilyTree;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyTreeService {

    @Autowired
    FamilyTree familyTree;

    public List<String> getPeopleBasedOnRelation(String person, String relation){

        List<String> people = new ArrayList<>();
        person = StringUtils.capitalize(person.toLowerCase());
        relation = relation.toLowerCase();

        if(!familyTree.validateNames(person))
            throw new RuntimeException("Invalid name");

        if(!familyTree.validateRelations(relation))
            throw new RuntimeException("Invalid relation");

        switch(relation.toLowerCase()){
            case "paternaluncles":
                people = familyTree.getPaternalUncle(person);
                break;

            case "maternaluncles":
                people = familyTree.getMaternalUncle(person);
                break;

            case "paternalaunt":
                people = familyTree.getPaternalAunt(person);
                break;

            case "maternalaunt":
                people = familyTree.getMaternalAunt(person);
                break;

            case "sisterinlaw":
                people = familyTree.getSistersInLaw(person);
                break;

            case "brotherinlaw":
                people = familyTree.getBrothersInLaw(person);
                break;

            case "cousins":
                people = familyTree.getBrothers(person);
                break;

            case "father":
                people.add(familyTree.getFather(person));
                break;

            case "mother":
                people.add(familyTree.getMother(person));
                break;

            case "children":
                people = familyTree.getchildren(person);
                break;

            case "sons":
                people = familyTree.getSons(person);
                break;

            case "daughters":
                people = familyTree.getDaughters(person);
                break;

            case "brothers":
                people = familyTree.getBrothers(person);
                break;

            case "sisters":
                people = familyTree.getSisters(person);
                break;

            case "granddaughter":
                people = familyTree.getGrandDaughters(person);
                break;

        }

        return people;

    }

}
