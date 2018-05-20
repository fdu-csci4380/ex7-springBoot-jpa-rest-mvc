package ex7.sb.teacher.mongodb.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import ex7.sb.teacher.mongodb.model.Teacher;

/**
 * Some doc and examples of queries
 * https://docs.spring.io/spring-data/jpa/docs/current/reference/html/
 * https://lishman.io/spring-data-jpa-repository-queries
 * 
 * 
 * db.inventory.find( { status: "A" } )
 * above is equivalent of below SQL in a RDBMS
 * SELECT * from inventory WHERE status = "A"
 * 
 * Projections to specify which fields to return. 1 means return it. 0 means don't return it.
 * db.inventory.find( { status: "A" }, { item: 1, status: 1 } )
 * Above returns item and status fields (along with _id field, which is default) of rows whose status is "A"
 * Below says do not return status and _id fields
 * db.inventory.find( { status: "A" }, { _id: 0, status: 0 } )
 * 
 * db.inventory.find( {
 *   $and : [
 *       { $or : [ { price : 0.99 }, { price : 1.99 } ] },
 *       { $or : [ { sale : true }, { qty : { $lt : 20 } } ] }
 *   ]
 * } )
 * 
 * Some example mongodb native queries that can be run in robomongo;
 * 
 * db.getCollection('Teachers').find({'name':{$regex:'Top$'}})
 * 
 * using regular expressions to find for a name ignoring case (the last i means that)
 * db.getCollection('Teachers').find({name:{$regex: /ALI/i}})            - WORKS
 * db.getCollection('Teachers').find({"name":{"$regex": /ALI/i}})        - WORKS
 * db.getCollection('Teachers').find({"name":{"$regex": "/ALI/i"}})      - does NOT work
 * db.getCollection('Teachers').find({"name":{"$regex": "(?i)(JoHn)"}})  - WORKS
 * 
 * nested find (size has h, so size.h). AND
 * db.inventory.find( { "size.h": { $lt: 15 }, "size.uom": "in", status: "D" } )
 * 
 * return just the 'name' attribute (along with default 'id') values all in lower case
 * db.getCollection('Teachers').aggregate([{$project:{name:{$toLower:'$name'}}}])
 * 
 * db.inventory.insertMany( [
 *	{ item: "journal", status: "A", size: { h: 14, w: 21, uom: "cm" }, instock: [ { warehouse: "A", qty: 5 } ] },
 *	{ item: "notebook", status: "A",  size: { h: 8.5, w: 11, uom: "in" }, instock: [ { warehouse: "C", qty: 5 } ] },
 *	{ item: "paper", status: "D", size: { h: 8.5, w: 11, uom: "in" }, instock: [ { warehouse: "A", qty: 60 } ] },
 *	{ item: "planner", status: "D", size: { h: 22.85, w: 30, uom: "cm" }, instock: [ { warehouse: "A", qty: 40 } ] },
 *	{ item: "postcard", status: "A", size: { h: 10, w: 15.25, uom: "cm" }, instock: [ { warehouse: "B", qty: 15 }, { warehouse: "C", qty: 35 } ] }
 * ]);
 * 
 *  @Query(value = "{}", fields = "{name : 1}")
 *  @Query(value = "{}", fields = "{_id : 0}")
 * 
 * querydsl-mongodb project allows you to write typesafe queries
 *  
 * @author ilker
 *
 */
@Repository
//public interface TeacherRepository extends MongoRepository<Teacher, String>, QuerydslPredicateExecutor<Teacher> {
public interface TeacherRepository extends MongoRepository<Teacher, String> {
	// https://docs.spring.io/spring-data/mongodb/docs/1.2.0.RELEASE/reference/html/mongo.repositories.html
	// NOTE refer to above for "Supported keywords for query methods" and their mapping to native query
	// NOTE ilker, below are examples of using "Generated Query Methods" following convention of Spring Data (same thing with Spring Data JPA)
	public Optional<List<Teacher>> findByTeacherId(String teacherId);
	public Optional<List<Teacher>> findByAgeLessThan(int age);
	public Optional<List<Teacher>> findByNameOrLastnameOrderByAgeDesc(String name, String lastname);
	public Optional<List<Teacher>> findFirst3ByTitle(String title);
	public Optional<List<Teacher>> findByNameStartingWith(String name);
	public void deleteByTeacherId(String teacherId);

	public Optional<List<Teacher>> findByNameRegex(String name);  // NOTE ilker, pass inputs like;  '(?i)(ALi)' to find ignoring case

	// NOTE ilker below are examples of using "JSON Query Methods" which are basically native MongoDb queries
	@Query("{}")
	public List<Teacher> findMyAll();
	
	@Query("{'name': ?0}")
	public List<Teacher> findMyByName(String name);
	
	@Query("{'name': {$regex: ?0}}")	    // NOTE ilker, pass inputs like;  '(?i)(ALi)' to find ignoring case
//	@Query("{'name': {$regex: '/ALi/i'}}")	// this compiles but does not match anything
//	@Query("{'name': {$regex: /ALi/i}}")	// this fails compilation - com.mongodb.util.JSONParseException
	public Optional<List<Teacher>> findMyByNameRegexp(String regexp);

	@Query("{'name': {$regex: '(?i)(ALi)'}}")	// WORKS
//	@Query("{'name': {$regex: '/ALi/i'}}")	    // this compiles but does not match anything
//	@Query("{'name': {$regex: /ALi/i}}")	    // this fails compilation - com.mongodb.util.JSONParseException
	public Optional<List<Teacher>> findMyByNameRegexp2b();
	
	
	// NOTE ilker, below does not find anything - @Param is not supported with spring data mongodb native queries (unlike JPA native queries)
//	@Query("{'name': '$teacherName'}")
//	public Optional<List<Teacher>> findMyByName2(@Param("teacherName") String name);
	// NOTE ilker $toLower is a "String Aggregation Operator", not supported in find query - Query failed with error code 2 and error message 'unknown operator: $toLower'
//	@Query("{'name': {$toLower: ?0}}")
//	public Optional<List<Teacher>> findMyByName3(String name);
	
	@Query("{'age': {$gt: ?0, $lt: ?1}}")
	public List<Teacher> findMyByAgeBetween(int ageGT, int ageLT);
	
}
