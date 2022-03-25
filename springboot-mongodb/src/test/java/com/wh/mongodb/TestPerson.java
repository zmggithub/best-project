package com.wh.mongodb;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.wh.mongodb.dao.PersonDao;
import com.wh.mongodb.domian.Address;
import com.wh.mongodb.domian.Person;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestPerson {
    @Autowired
    private PersonDao personDao;

    @Test
    public void testInsert(){
        Person person = new Person();
        person.setName("托马斯2");
        person.setAge(24);
        person.setId(ObjectId.get());
        person.setAddress(new Address("五一路","北京 ","10086"));
        personDao.savePerson(person);
    }

    @Test
    public void testFindByName(){
        List<Person> name = personDao.findByName("托马斯");
        for (Person person : name) {
            System.out.println(person);
        }
    }

    @Test
    public void testById(){
        Person byId = personDao.findById("623d54d59eb7da085f6f886c");
        System.out.println(byId);
    }

    @Test
    public void testFindAll(){
        List<Person> list = personDao.findAll();
        for (Person person : list) {
            System.out.println(person);
        }
    }

    @Test
    public void testUpdate(){
        Person person = new Person();
        person.setId(new ObjectId("623d62aafd7fff075d94f542"));
        person.setAge(9119);
        UpdateResult result = personDao.testUpdate(person);
        System.out.println(result);
    }

    @Test
    public void testFindPageList(){
        List<Person> list = personDao.queryPersonList(2, 2);
        for (Person person : list) {
            System.out.println(person);
        }
    }

    @Test
    public void testDelById(){
        DeleteResult result = personDao.testdel("623d62aafd7fff075d94f542");
        System.out.println(result);
    }


}
