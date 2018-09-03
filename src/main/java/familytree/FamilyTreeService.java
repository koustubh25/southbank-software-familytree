package familytree;

import familytree.model.FamilyTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FamilyTreeService {

    @Autowired
    FamilyTree familyTree;

    public List<String> getPeopleBasedOnRelation(String person, String relation){

        List<String> list = new ArrayList<>();
        list.add(person);
        list.add(relation);
        return list;

    }

}
