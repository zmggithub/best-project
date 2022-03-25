package com.wh.mongodb.dao;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.wh.mongodb.domian.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PersonDao {
    @Autowired
    private MongoTemplate mongoTemplate; //引入MongoDB的模板

    /**
     * 新增
     * @param person
     */
    public void  savePerson(Person person){
        mongoTemplate.save(person,"person");
    }

    /**
     * 根据姓名查询
     * @param name
     * @return
     */
    public List<Person> findByName(String name){
        Query findQuery = Query.query(Criteria.where("name").is(name));
        List<Person> people = mongoTemplate.find(findQuery, Person.class);
        return people;
    }

    /**
     * 根据ID查询 返回单个
     * @param id
     * @return
     */
    public Person findById(String id){
        Person byId = mongoTemplate.findById(id, Person.class);
        return byId;
    }

    /**
     * 查询所有
     * @return
     */
    public List<Person> findAll(){
        List<Person> list = mongoTemplate.findAll(Person.class);
        return list;
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    public DeleteResult testdel(String id){
        Query delQuery = Query.query(Criteria.where("id").is(id));
        DeleteResult result = mongoTemplate.remove(delQuery, Person.class);
        return result;
    }

    /**
     * 修改
     * @param person
     * @return
     */
    public UpdateResult testUpdate(Person person){
        Query updateQuery = Query.query(Criteria.where("id").is(person.getId()));
        Update age = Update.update("age", person.getAge());
        UpdateResult result = mongoTemplate.updateFirst(updateQuery, age, Person.class);
        return  result;
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public List<Person>queryPersonList(Integer page ,Integer size){
        Query query = new Query().limit(size).skip((page - 1) * size);
        List<Person> list = mongoTemplate.find(query, Person.class);
        return list;
    }
}
