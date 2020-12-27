package dts;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.client.RestTemplate;

import dts.boundaries.ItemBoundary;
import dts.boundaries.ItemIdWrapperBoundary;
import dts.boundaries.LocationBoundary;
import dts.boundaries.OperationBoundary;
import dts.boundaries.UserBoundary;
import dts.boundaries.UserIdBoundary;
import dts.boundaries.UserIdWrapperBoundary;
import dts.data.UserRole;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
	private int port;
	private String url;
	private RestTemplate restTemplate;

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + port + "/dts";
		System.err.println(this.url);
		this.restTemplate = new RestTemplate();

	}
	// Server is Up //

	@Test
	public void TestLoaded() {

	}

	@AfterEach // Delete all Delete That is possible (In edition doing Delete test)
	public void tearDown() {
		String urlTest;
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + "/demo@maildomain.com";
		this.restTemplate.delete(urlTest);
		urlTest = this.url + "/operations/" + Application.APPLICATION_NAME + "/demo@maildomain.com";
		this.restTemplate.delete(urlTest);
	}

////------------- Users related API test	--------------- ////

//// Test POST	////

	@Test
	public void testDatabaseIsEmptyByDefault() throws Exception {
		// Can't be tested yet.
	}

	@Test
	public void CreateNewUser() throws Exception {
		String urlTest;
		// Set URL for POST user
		urlTest = this.url + "/users";
		// GIVEN the server is up and empty (Tested)
		// Set time
		// WHEN I POST /dts/users using (test Email)
		HashMap<Object, Object> newuser = new HashMap<>();
		newuser.put("email", "demo@maildomain.com");
		UserBoundary actual = this.restTemplate.postForObject(urlTest, newuser, UserBoundary.class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (User boundary - player) //Date bigger to
		// test if new
		assertThat(actual.getUserId()).isNotNull();
		assertThat(actual.getUserId().getEmail()).isEqualTo("demo@maildomain.com");
		assertThat(actual.getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getRole()).isEqualTo(UserRole.PLAYER);
		assertThat(actual.getUsername()).isEqualTo("Demo User");
		assertThat(actual.getAvatar()).isEqualTo("ooOO_()OOoo");
		assertThat(actual.getClass()).hasSameClassAs(UserBoundary.class);
	}

//// Test GET////

	@Test
	public void GetUser() throws Exception {
		String urlTest;
		// User already need to be existed as Create new User run's before
		// Set URL for Login
		urlTest = this.url + "/users/login/" + Application.APPLICATION_NAME + "/demo@maildomain.com";
		// GIVEN the server is up (Tested)
		// WHEN I GET /dts/users/login/{userSpace}/{userEmail}
		UserBoundary actual = this.restTemplate.getForObject(urlTest, UserBoundary.class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (User boundary - player)(DEMO)
		assertThat(actual.getUserId()).isNotNull(); // User received
		assertThat(actual.getUserId().getEmail()).isEqualTo("demo@maildomain.com");
		assertThat(actual.getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getRole()).isEqualTo(UserRole.PLAYER);
		assertThat(actual.getUsername()).isEqualTo("Demo User");
		assertThat(actual.getAvatar()).isEqualTo("ooOO_()OOoo");

	}

//// Test PUT	////	

	@Test
	public void PutUser() throws Exception {
		String urlTest;
		// GIVEN the server is up (Tested already)
		// Make new User for testing
		// Set URL for post
		urlTest = this.url + "/users";
		HashMap<Object, Object> newuser = new HashMap<>();
		newuser.put("email", "demoTEST@maildomain.com");
		UserBoundary test = this.restTemplate.postForObject(urlTest, newuser, UserBoundary.class);
		assertThat(test.getClass()).hasSameClassAs(UserBoundary.class);
		// User made

		// WHEN I GET /dts/users/{userSpace}/{userEmail}
		// Changes to newUser

		// Set new URL for PUT
		urlTest = this.url + "/users/" + Application.APPLICATION_NAME + "/demoTEST@maildomain.com";
		UserIdBoundary updateid = new UserIdBoundary(Application.APPLICATION_NAME, "demoTEST@maildomain.com");
		UserBoundary updateUser = new UserBoundary(updateid, UserRole.PLAYER, "JUnit Test", "0.-(-!-)");
		this.restTemplate.put(urlTest, updateUser);
		// THEN the result HTTP STATUS 2xx
		// Test if worked as we don't have response with body need to test again GET
		// Test GET
		urlTest = this.url + "/users/login/" + Application.APPLICATION_NAME + "/demoTEST@maildomain.com";
		UserBoundary actualGET = this.restTemplate.getForObject(urlTest, UserBoundary.class);
		assertThat(actualGET.getUserId()).isNotNull();
		// AND the server returns the JOSN (User boundary - player)(DEMO)
		assertThat(actualGET.getUserId().getEmail()).isEqualTo("demoTEST@maildomain.com");
		assertThat(actualGET.getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actualGET.getRole()).isEqualTo(UserRole.PLAYER);
		assertThat(actualGET.getUsername()).isEqualTo("JUnit Test");
		assertThat(actualGET.getAvatar()).isEqualTo("0.-(-!-)");

	}

////------------- Digital items related API test	--------------- ////

////Test POST	////

	@Test
	public void CreateNewItem() throws Exception {
		String urlTest;
		// Set URL for POST Item
		// GIVEN the server is up and Not empty (Tested)
		// Set time
		long now = System.currentTimeMillis();
		// Create new user as if no users no items available.
		urlTest = this.url + "/users";
		HashMap<Object, Object> newuser = new HashMap<>();
		newuser.put("email", "demo@maildomain.com");
		UserBoundary test = this.restTemplate.postForObject(urlTest, newuser, UserBoundary.class);
		assertThat(test.getClass()).hasSameClassAs(UserBoundary.class);

		urlTest = this.url + "/items/JunitTest/JunitTester@Gmail.com";
		// WHEN I POST /dts/items/JunitTest/JunitTester@Gmail.com using (Null Json)
		ItemBoundary actual = this.restTemplate.postForObject(urlTest, new HashMap<>(), ItemBoundary.class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (Item boundary) //Date bigger to test if new
		assertThat(actual.getItemId()).isNotNull();
		assertThat(actual.getCreatedTimestamp().getTime()).isGreaterThan(now);
		assertThat(actual.getActive()).isEqualTo(true);
		assertThat(actual.getName()).isEqualTo("TEST ITEM NAME");
		assertThat(actual.getType()).isEqualTo("TEST TYPE");
		assertThat(actual.getCreatedBy().getClass()).hasSameClassAs(UserBoundary.class);
		assertThat(actual.getLocation().getClass()).hasSameClassAs(LocationBoundary.class);

	}

////Test GET////

	@Test
	public void GetItem() throws Exception {
		String urlTest;
		String itemID;
		// Create new Item and test the Get as last test we don't save the ID ITEM! here
		// we can test !
		urlTest = this.url + "/items/JunitTest/JunitTester@Gmail.com";
		ItemBoundary newItem = this.restTemplate.postForObject(urlTest, new HashMap<>(), ItemBoundary.class);
		assertThat(newItem.getClass()).hasSameClassAs(ItemBoundary.class); // Fast test we received Item
		itemID = newItem.getItemId().getId(); // Get the UNIQ id that is Created
		// Create new user as if no users no items available.
		urlTest = this.url + "/users";
		HashMap<Object, Object> newuser = new HashMap<>();
		newuser.put("email", "demo@maildomain.com");
		UserBoundary test = this.restTemplate.postForObject(urlTest, newuser, UserBoundary.class);
		assertThat(test.getClass()).hasSameClassAs(UserBoundary.class);

		// Set URL for GET /dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + "/demo@maildomain.com/"
				+ Application.APPLICATION_NAME + "/" + itemID;
		// GIVEN the server is up (Tested)
		// WHEN I GET /dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}
		ItemBoundary actual = this.restTemplate.getForObject(urlTest, ItemBoundary.class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (Item boundary)
		assertThat(actual.getItemId()).isNotNull(); // User received
		assertThat(actual.getItemId().getId()).isEqualTo(itemID);
		assertThat(actual.getCreatedBy().getClass()).hasSameClassAs(UserBoundary.class);
		assertThat(actual.getLocation().getClass()).hasSameClassAs(LocationBoundary.class);

	}

	@Test
	public void GetArrayOfItems() throws Exception {
		String urlTest;
		// Create new Item and test the Get as last test we don't save the ID ITEM! here
		// we can test !
		urlTest = this.url + "/items/JunitTest/JunitTester@Gmail.com";
		ItemBoundary newItem = this.restTemplate.postForObject(urlTest, new HashMap<>(), ItemBoundary.class);
		assertThat(newItem.getClass()).hasSameClassAs(ItemBoundary.class); // Fast test we received Item
		// New item Created for test.

		// User already need to be existed as Create new User run's before
		// Set URL for GET array /dts/items/{userSpace}/{userEmail}
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + "/demo@maildomain.com";
		// GIVEN the server is up (Tested)
		// WHEN I GET array /dts/items/{userSpace}/{userEmail}
		ItemBoundary[] actual = this.restTemplate.getForObject(urlTest, ItemBoundary[].class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (Array of items)
		assertThat(actual).hasSizeGreaterThan(0); // if 0 not good

	}

////Test PUT	////	

	@Test
	public void PutItem() throws Exception {
		String urlTest;
		String itemId;
		// Create new Item and test the Get as last test we don't save the ID ITEM! here
		// we can test !
		urlTest = this.url + "/items/JunitTest/JunitTester@Gmail.com";
		ItemBoundary newItem = this.restTemplate.postForObject(urlTest, new HashMap<>(), ItemBoundary.class);
		assertThat(newItem.getClass()).hasSameClassAs(ItemBoundary.class); // Fast test we received Item
		itemId = newItem.getItemId().getId();
		// New item Created for test.
		HashMap<Object, Object> changeItem = new HashMap<>();
		changeItem.put("type", "Test Type");
		changeItem.put("name", "Junit Tester");

		// User already need to be existed as Create new User run's before
		// Set URL for PUT array
		// /dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + "/demo@maildomain.com/"
				+ Application.APPLICATION_NAME + "/" + itemId;
		// WHEN I PUT change
		// /dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}
		this.restTemplate.put(urlTest, changeItem);
		// THEN the result HTTP STATUS 2xx
		// AND the server changed item the JOSN
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + "/demo@maildomain.com/"
				+ Application.APPLICATION_NAME + "/" + itemId;
		// GIVEN the server is up (Tested)
		// WHEN I GET /dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}
		ItemBoundary actual = this.restTemplate.getForObject(urlTest, ItemBoundary.class);

		assertThat(actual.getItemId()).isNotNull();
		assertThat(actual.getActive()).isEqualTo(true);
		assertThat(actual.getName()).isEqualTo("Junit Tester");
		assertThat(actual.getType()).isEqualTo("Test Type");
		assertThat(actual.getCreatedBy().getClass()).hasSameClassAs(UserBoundary.class);
		assertThat(actual.getLocation().getClass()).hasSameClassAs(LocationBoundary.class);

	}

////------------- Operation related API test	--------------- ////

	@Test
	public void OperationPost() throws Exception {
		String urlTest;
		// Set URL for POST Operation
		urlTest = this.url + "/operations";

		// WHEN I POST /dts/operations
		Map<String, Object> params = new HashMap<>();
		params.put("type", "Amazing Operation Type");
		params.put("item", new ItemIdWrapperBoundary());
		params.put("invokedBy", new UserIdWrapperBoundary());

		OperationBoundary actual = this.restTemplate.postForObject(urlTest, params, OperationBoundary.class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JSON

		assertThat(actual.getOperationId()).isNotNull();
		assertThat(actual.getType()).isEqualTo("Amazing Operation Type");
		assertThat(actual.getItem().getClass()).hasSameClassAs(ItemIdWrapperBoundary.class);
		assertThat(actual.getInvokedBy().getClass()).hasSameClassAs(UserIdWrapperBoundary.class);
		assertThat(actual.getOperationAttributes()).isNotNull();

	}

	@Test
	public void OperationPostFail() throws Exception {

		// Test POST Fail -> Should fail if {type,item,invokedBy} are empty or null
		// Expect to get error code 400 - Bad Request

		String urlTest;
		urlTest = this.url + "/operations";

		assertThrows(BadRequest.class,
				() -> this.restTemplate.postForObject(urlTest, new HashMap<>(), OperationBoundary.class));

	}

	@Test
	public void OperationPostFailDueToTypeMissing() throws Exception {

		// Test POST Fail -> Should fail if {item} is empty or null
		// Expect to get error code 400 - Bad Request

		String urlTest;
		// Set URL for POST Operation
		urlTest = this.url + "/operations";

		// WHEN I POST /dts/operations
		Map<String, Object> params = new HashMap<>();
		params.put("type", "Amazing Operation Type");
		params.put("item", null);
		params.put("invokedBy", new UserIdWrapperBoundary());

		assertThrows(BadRequest.class, () -> this.restTemplate.postForObject(urlTest, params, OperationBoundary.class));

	}

	@Test
	public void OperationPostFailDueToItemMissing() throws Exception {

		// Test POST Fail -> Should fail if {invokedBy} is empty or null
		// Expect to get error code 400 - Bad Request

		String urlTest;
		// Set URL for POST Operation
		urlTest = this.url + "/operations";

		// WHEN I POST /dts/operations
		Map<String, Object> params = new HashMap<>();
		params.put("type", "Amazing Operation Type");
		params.put("item", new ItemIdWrapperBoundary());
		params.put("invokedBy", null);

		assertThrows(BadRequest.class, () -> this.restTemplate.postForObject(urlTest, params, OperationBoundary.class));

	}

	@Test
	public void OperationPostFailDueToUserMissing() throws Exception {

		// Test POST Fail -> Should fail if {type} is empty or null}
		// Expect to get error code 400 - Bad Request

		String urlTest;
		// Set URL for POST Operation
		urlTest = this.url + "/operations";

		// WHEN I POST /dts/operations
		Map<String, Object> params = new HashMap<>();
		params.put("type", "");
		params.put("item", new ItemIdWrapperBoundary());
		params.put("invokedBy", new UserIdWrapperBoundary());

		assertThrows(BadRequest.class, () -> this.restTemplate.postForObject(urlTest, params, OperationBoundary.class));

	}

	@Test
	public void OperationGet() throws Exception {
		String urlTest;

		urlTest = this.url + "/operations";

		Map<String, Object> params1 = new HashMap<>();
		params1.put("type", "First operation");
		params1.put("item", new ItemIdWrapperBoundary());
		params1.put("invokedBy", new UserIdWrapperBoundary());

		Map<String, Object> params2 = new HashMap<>();
		params2.put("type", "Second operation");
		params2.put("item", new ItemIdWrapperBoundary());
		params2.put("invokedBy", new UserIdWrapperBoundary());

		OperationBoundary firstOpertation = this.restTemplate.postForObject(urlTest, params1, OperationBoundary.class);
		assertThat(firstOpertation.getClass()).hasSameClassAs(OperationBoundary.class);
		OperationBoundary secondOpertation = this.restTemplate.postForObject(urlTest, params2, OperationBoundary.class);
		assertThat(secondOpertation.getClass()).hasSameClassAs(OperationBoundary.class);

		// Set URL for GET "/dts/operations/{adminSpace}/{adminEmail}"
		urlTest = this.url + "/operations/" + Application.APPLICATION_NAME + "/demo@maildomain.com";
		OperationBoundary[] actual = this.restTemplate.getForObject(urlTest, OperationBoundary[].class);

		assertThat(actual).isNotNull();
		assertThat(actual).hasSizeGreaterThan(1); // if 0 -> FAIL

	}

////------------- Admin API test	--------------- ////	

	/*
	 * @Test public void DeleteAllUsersAPI () throws Exception { String urlTest; int
	 * i; int limit = 10; // GIVEN the server is not Empty and have users urlTest =
	 * this.url + "/users"; // Make 10 users to fill the User for(i=0;i<limit;i++) {
	 * UserBoundary actual = this.restTemplate .postForObject(urlTest, new
	 * HashMap<>(), UserBoundary.class); // Test Every step !
	 * assertThat(actual.getUserId()) .isNotNull();
	 * assertThat(actual.getUserId().getEmail()) .isEqualTo("demo@maildomain.com");
	 * assertThat(actual.getUserId().getSpace())
	 * .isEqualTo(Application.APPLICATION_NAME); assertThat(actual.getRole())
	 * .isEqualTo(UserRole.PLAYER); assertThat(actual.getUsername())
	 * .isEqualTo("Demo User"); assertThat(actual.getAvatar())
	 * .isEqualTo("ooOO_()OOoo");
	 * assertThat(actual.getClass()).hasSameClassAs(UserBoundary.class); } //Get all
	 * the user urlTest = this.url +"/admin/users/" +Application.APPLICATION_NAME +
	 * "/demo@maildomain.com";
	 * 
	 * // Get all the Users //UserBoundary[] actualAllUser = this.restTemplate
	 * //.getForObject(urlTest, UserBoundary[].class); //assertThat(actualAllUser)
	 * //.hasSizeGreaterThan(9); //WHEN post Delete all Users
	 * "/dts/admin/users/{adminSpace}/{adminEmail}" //urlTest = this.url +
	 * "/admin/users/" + Application.APPLICATION_NAME +"/demo@maildomain.com";
	 * //this.restTemplate.delete(urlTest); //THAN get all user equals 0
	 * 
	 * //Get all the user //urlTest = this.url +"/admin/users/" +
	 * Application.APPLICATION_NAME + "/demo@maildomain.com";
	 * 
	 * // Get all the Users //UserBoundary[] deletedUsers = this.restTemplate
	 * //.getForObject(urlTest, UserBoundary[].class);
	 * //assertThat(deletedUsers).hasSize(0); }
	 * 
	 */

}
