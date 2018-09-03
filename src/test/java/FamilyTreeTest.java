import familytree.model.FamilyTree;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.stream.Collectors;

public class FamilyTreeTest{

    static FamilyTree ft;


    @BeforeClass
    public static void setup() throws Exception{
        // Initialize familytree
        ft = new FamilyTree();
    }


    @Test
    public void testChildren() throws Exception{

        String[] expectedChildren = new String[]{"Ish", "Chit", "Vich", "Saty"};
        String name = "King Shan";
        Assert.assertArrayEquals(expectedChildren, ft.getchildren(name).stream().map(child -> child.getName()).collect(Collectors.toList()).toArray());

    }

    @Test
    public void testBrothers() throws Exception{
        String name = "Drita";
        String[] expectedBrothers = new String[]{"Vrita"};
        Assert.assertArrayEquals(expectedBrothers, ft.getBrothers(name).toArray());

    }

    @Test
    public void testSisters() throws Exception{
        String name = "Saty";
        String[] expectedSisters = new String[]{};
        Assert.assertArrayEquals(expectedSisters, ft.getSisters(name).toArray());
    }

    @Test
    public void testSons() throws Exception{
        String name = "Ambi";
        String[] expectedSons = new String[]{"Drita", "Vrita"};
        Assert.assertArrayEquals(expectedSons, ft.getSons(name).toArray());
    }

    @Test
    public void testDaughters() throws Exception{
        String name = "Queen Anga";
        String[] expectedDaughters = new String[]{"Saty"};
        Assert.assertArrayEquals(expectedDaughters, ft.getDaughters(name).toArray());
    }

    @Test
    public void testFather() throws Exception{
        String name = "Vich";
        String expectedFather = "King Shan";
        Assert.assertEquals(expectedFather, ft.getFather(name));
    }

    @Test
    public void testMother() throws Exception{
        String name = "Vich";
        String expectedMother = "Queen Anga";
        Assert.assertEquals(expectedMother, ft.getMother(name));
    }

    @Test
    public void testCousins() throws  Exception{
        String name = "Drita";
        String[] expectedCousins = new String[]{"Vila", "Chika", "Satvy", "Savya", "Saayan"};
        Assert.assertArrayEquals(expectedCousins, ft.getCousins(name).toArray());
    }

    @Test
    public void testBrothersInLaw() throws Exception{
        String name = "Ambi";
        String[] expectedBrothersInLaw = new String[]{"Ish","Vich"};
        Assert.assertArrayEquals(expectedBrothersInLaw, ft.getBrothersInLaw(name).toArray());

    }

    @Test
    public void testSistersInLaw() throws Exception{
        String name = "Vich";
        String[] expectedSistersInLaw = new String[]{"Ambi"};
        Assert.assertArrayEquals(expectedSistersInLaw, ft.getSistersInLaw(name).toArray());

    }

    @Test
    public void testMaternalAunt() throws Exception{
        String name = "Satvy";
        String[] expectedMaternalAunts = new String[]{"Ambi", "Lika"};
        Assert.assertArrayEquals(expectedMaternalAunts, ft.getMaternalAunt(name).toArray());

    }


    @Test
    public void getMaternalUncle() throws Exception{
        String name = "Satvy";
        String[] expectedMaternalUncles = new String[]{"Ish", "Chit", "Vich"};
        Assert.assertArrayEquals(expectedMaternalUncles, ft.getMaternalUncle(name).toArray());

    }

    @Test
    public void getPaternalUncle() throws Exception{
        String name = "Vila";
        String[] expectedPaternalUncles = new String[]{"Ish", "Chit", "Vya"};
        Assert.assertArrayEquals(expectedPaternalUncles, ft.getPaternalUncle(name).toArray());

    }

    @Test
    public void getPaternalAunt() throws Exception{
        String name = "Vila";
        String[] expectedPaternalAunts = new String[]{"Saty", "Ambi"};
        Assert.assertArrayEquals(expectedPaternalAunts, ft.getPaternalAunt(name).toArray());

    }

}