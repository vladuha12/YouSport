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

import dts.boundaries.IdBoundary;
import dts.boundaries.ItemBoundary;
import dts.boundaries.ItemIdBoundary;
import dts.boundaries.ItemIdWrapperBoundary;
import dts.boundaries.LocationBoundary;
import dts.boundaries.NewUserBoundary;
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

	private String playerEmail = "/player@maildomain.com";
	private String managerEmail = "/manager@maildomain.com";
	private String adminEmail = "/admin@maildomain.com";

	private float itemLat = (float) 31.890000802019093;
	private float itemLng = (float) 31.890000802019093;

	// Helping Function
	// create new user
	public NewUserBoundary createNewPlayerUser() {
		NewUserBoundary newUser = new NewUserBoundary();
		newUser.setEmail("player@maildomain.com");
		newUser.setRole(UserRole.PLAYER);
		newUser.setUsername("Demo User");
		newUser.setAvatar("ooOO_()OOoo");
		return newUser;
	}

	public NewUserBoundary createNewManagerUser() {
		NewUserBoundary newUser = new NewUserBoundary();
		newUser.setEmail("manager@maildomain.com");
		newUser.setRole(UserRole.MANAGER);
		newUser.setUsername("Demo User");
		newUser.setAvatar("ooOO_()OOoo");
		return newUser;
	}

	public NewUserBoundary createNewAdminUser() {
		NewUserBoundary newUser = new NewUserBoundary();
		newUser.setEmail("admin@maildomain.com");
		newUser.setRole(UserRole.ADMIN);
		newUser.setUsername("Demo User");
		newUser.setAvatar("ooOO_()OOoo");
		return newUser;
	}

	public ItemBoundary creatNewFieldItemBoundary(UserBoundary test) {
		ItemBoundary item = new ItemBoundary();
		LocationBoundary itemLocation = new LocationBoundary(this.itemLat, this.itemLng);
		UserIdWrapperBoundary id = new UserIdWrapperBoundary(test.getUserId());
		item.setCreatedBy(id);
		item.setLocation(itemLocation);
		item.setName("Demo Field");
		item.setType("sport_field");
		return item;
	}

	public ItemBoundary creatNewSportItemBoundary(UserBoundary test) {
		ItemBoundary item = new ItemBoundary();
		UserIdWrapperBoundary id = new UserIdWrapperBoundary(test.getUserId());
		item.setCreatedBy(id);
		item.setName("Demo Sport");
		item.setType("sport_type");
		return item;
	}

	@LocalServerPort
	public void setPort(int port) {
		this.port = port;
	}

	@PostConstruct
	public void init() {
		this.url = "http://localhost:" + port + "/dts";
		this.restTemplate = new RestTemplate();

	}

	// Server is Up //
	@Test
	public void contextLoads() {

	}

	@AfterEach // Delete all Delete That is possible (In edition doing Delete test)
	public void tearDown() {
		String urlTest;
		urlTest = this.url + "/users"; // Set URL for post new user
		UserBoundary admin = this.restTemplate.postForObject(urlTest, createNewAdminUser(), UserBoundary.class);
		urlTest = this.url + "/admin/items/" + Application.APPLICATION_NAME + adminEmail;
		this.restTemplate.delete(urlTest);
		urlTest = this.url + "/admin/operations/" + Application.APPLICATION_NAME + adminEmail;
		this.restTemplate.delete(urlTest);
		urlTest = this.url + "/admin/users/" + Application.APPLICATION_NAME + adminEmail;
		this.restTemplate.delete(urlTest);
	}

	@Test
	public void testDatabaseIsEmpty() throws Exception {
		String urlTest;
		// GIVEN the server AND the server contains a user and user admin
		urlTest = this.url + "/users"; // Set URL for post new user
		UserBoundary player = this.restTemplate.postForObject(urlTest, createNewPlayerUser(), UserBoundary.class);
		UserBoundary admin = this.restTemplate.postForObject(urlTest, createNewAdminUser(), UserBoundary.class);
		assertThat(player.getClass()).hasSameClassAs(UserBoundary.class);
		assertThat(admin.getClass()).hasSameClassAs(UserBoundary.class);// check new user was created

		// WHEN I GET /dts/admin/users/{adminSpace}/{adminEmail}
		// AND GET /dts/admin/operations/{adminSpace}/{adminEmail}
		// AND GET /dts/items/{userSpace}/{userEmail}
		urlTest = this.url + "/admin/users/" + Application.APPLICATION_NAME + adminEmail;
		UserBoundary[] users = this.restTemplate.getForObject(urlTest, UserBoundary[].class);
		urlTest = this.url + "/admin/operations/" + Application.APPLICATION_NAME + adminEmail;
		OperationBoundary[] operations = this.restTemplate.getForObject(urlTest, OperationBoundary[].class);
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + playerEmail;
		ItemBoundary[] items = this.restTemplate.getForObject(urlTest, ItemBoundary[].class);

		// THEN Operations and item arrays should return empty arrays
		// AND users array contains 2 users(admin, users)
		assertThat(users).hasSize(2);
		assertThat(operations).isEmpty();
		assertThat(items).isEmpty();
	}

////------------- Users related API test	--------------- ////

////Test POST	////

	@Test
	public void testCreateNewUser() throws Exception {
		String urlTest;
		// Set URL for POST user
		urlTest = this.url + "/users";
		// GIVEN the server is up and empty (Tested)
		// Set time
		// WHEN I POST /dts/users using (test Email)
		NewUserBoundary newUser = new NewUserBoundary();
		newUser.setEmail("player@maildomain.com");
		newUser.setRole(UserRole.PLAYER);
		newUser.setUsername("Demo User");
		newUser.setAvatar("ooOO_()OOoo");
		UserBoundary actual = this.restTemplate.postForObject(urlTest, newUser, UserBoundary.class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (User boundary - player) //Date bigger to
		// test if new
		assertThat(actual.getUserId()).isNotNull();
		assertThat(actual.getUserId().getEmail()).isEqualTo("player@maildomain.com");
		assertThat(actual.getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getRole()).isEqualTo(UserRole.PLAYER);
		assertThat(actual.getUsername()).isEqualTo("Demo User");
		assertThat(actual.getAvatar()).isEqualTo("ooOO_()OOoo");
		assertThat(actual.getClass()).hasSameClassAs(UserBoundary.class);
	}

//// Test GET////

	@Test
	public void testGetUser() throws Exception {
		String urlTest;

		// GIVEN the server is up (Tested) AND the server contains a user(player)
		urlTest = this.url + "/users"; // Set URL for post new user
		UserBoundary DemoUser = this.restTemplate.postForObject(urlTest, createNewPlayerUser(), UserBoundary.class);
		assertThat(DemoUser.getClass()).hasSameClassAs(UserBoundary.class); // check new user was created
		// WHEN I GET /dts/users/login/{userSpace}/{userEmail}
		urlTest = this.url + "/users/login/" + Application.APPLICATION_NAME + playerEmail; // Set URL for get user
		UserBoundary actual = this.restTemplate.getForObject(urlTest, UserBoundary.class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (User boundary - player)
		assertThat(actual.getUserId()).isNotNull(); // User received
		assertThat(actual.getUserId().getEmail()).isEqualTo("player@maildomain.com");
		assertThat(actual.getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getRole()).isEqualTo(UserRole.PLAYER);
		assertThat(actual.getUsername()).isEqualTo("Demo User");
		assertThat(actual.getAvatar()).isEqualTo("ooOO_()OOoo");

	}

	@Test
	public void testGetNonExistingPlaye() throws Exception {
		String urlTest;

		urlTest = this.url + "/users/login/" + Application.APPLICATION_NAME + "/*****@maildomain.com";
		// GIVEN the server is up (Tested)
		// WHEN I GET /dts/users/login/{userSpace}/{nonExistionUserEmail}
		// THEN the result HTTP NOT STATUS 2xx
		assertThrows(Exception.class, () -> this.restTemplate.getForObject(urlTest, UserBoundary.class));
	}

//// Test PUT	////	

	@Test
	public void testUpdateExistingUserNameAndAvatarAndRole() throws Exception {
		String urlTest;
		// GIVEN the server is up (Tested already)
		// AND the server contains a User

		urlTest = this.url + "/users"; // Set URL for post new user
		UserBoundary DemoUser = this.restTemplate.postForObject(urlTest, createNewPlayerUser(), UserBoundary.class);
		assertThat(DemoUser.getClass()).hasSameClassAs(UserBoundary.class); // check new user was created

		// WHEN I PUT /dts/users/{userSpace}/{userEmail}
		// using (User boundary – player when “Demo User” change to “change User”)

		urlTest = this.url + "/users/" + Application.APPLICATION_NAME + playerEmail; // Set new URL for PUT
		UserIdBoundary updateid = new UserIdBoundary(Application.APPLICATION_NAME, "demo@maildomain.com");
		UserBoundary updateUser = new UserBoundary(updateid, UserRole.MANAGER, "Change User", "()()change()()");
		this.restTemplate.put(urlTest, updateUser);

		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (User boundary - player)(DEMO)
		// *(check get protocol that the user was updated)
		// Test GET
		urlTest = this.url + "/users/login/" + Application.APPLICATION_NAME + playerEmail;
		UserBoundary actualGET = this.restTemplate.getForObject(urlTest, UserBoundary.class);
		assertThat(actualGET.getUserId()).isNotNull();

		assertThat(actualGET.getUserId().getEmail()).isEqualTo("player@maildomain.com");
		assertThat(actualGET.getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actualGET.getRole()).isEqualTo(UserRole.MANAGER);
		assertThat(actualGET.getUsername()).isEqualTo("Change User");
		assertThat(actualGET.getAvatar()).isEqualTo("()()change()()");
	}

	@Test
	public void testDoNotallowUpdateExistingUserEmailAndId() throws Exception {
		String urlTest;
		// GIVEN the server is up (Tested already)
		// AND the server contains a User

		urlTest = this.url + "/users"; // Set URL for post new user
		UserBoundary DemoUser = this.restTemplate.postForObject(urlTest, createNewPlayerUser(), UserBoundary.class);
		assertThat(DemoUser.getClass()).hasSameClassAs(UserBoundary.class); // check new user was created

		// WHEN I PUT /dts/users/{userSpace}/{userEmail}
		// using (User boundary – player when “Demo User” change to “change User”)

		urlTest = this.url + "/users/" + Application.APPLICATION_NAME + playerEmail; // Set new URL for PUT
		UserIdBoundary updateid = new UserIdBoundary("vvvsa", "new@maildomain.com");
		UserBoundary updateUser = new UserBoundary(updateid, UserRole.PLAYER, "Demo User", "ooOO_()OOoo");
		this.restTemplate.put(urlTest, updateUser);

		// THEN the result HTTP STATUS 2xx
		// *(check get protocol that the user was updated)
		// Test GET
		urlTest = this.url + "/users/login/" + Application.APPLICATION_NAME + playerEmail;
		UserBoundary actualGET = this.restTemplate.getForObject(urlTest, UserBoundary.class);
		assertThat(actualGET.getUserId()).isNotNull();

		assertThat(actualGET.getUserId().getEmail()).isEqualTo("player@maildomain.com");
		assertThat(actualGET.getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actualGET.getRole()).isEqualTo(UserRole.PLAYER);
		assertThat(actualGET.getUsername()).isEqualTo("Demo User");
		assertThat(actualGET.getAvatar()).isEqualTo("ooOO_()OOoo");
	}

////------------- Digital items related API test	--------------- ////

////Test POST	////

	@Test
	public void testCreateNewSportTypeItem() throws Exception {
		String urlTest;
		long now = System.currentTimeMillis(); // Set time

		// GIVEN the server is up AND the server contains a User(MANAGER)
		urlTest = this.url + "/users"; // Set URL for POST user
		UserBoundary test = this.restTemplate.postForObject(urlTest, createNewManagerUser(), UserBoundary.class);
		assertThat(test.getClass()).hasSameClassAs(UserBoundary.class);

		// WHEN I POST /dts/items/{managerSpace}/{managerEmail} Using (Item boundary)
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + managerEmail; // set URL	
		ItemBoundary newSportBoundary = creatNewSportItemBoundary(test);

		ItemBoundary actual = this.restTemplate.postForObject(urlTest,newSportBoundary, ItemBoundary.class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (Item boundary)
		assertThat(actual.getItemId()).isNotNull();
		assertThat(actual.getItemId().getId()).isNotEqualTo(newSportBoundary.getItemId().getId());
		assertThat(actual.getItemId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getCreatedTimestamp().getTime()).isGreaterThan(now);
		assertThat(actual.getActive()).isEqualTo(true);
		assertThat(actual.getCreatedBy().getUserId().getEmail()).isEqualTo("manager@maildomain.com");
		assertThat(actual.getCreatedBy().getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getName()).isEqualTo("Demo Sport");
		assertThat(actual.getType()).isEqualTo("sport_type");
	}

	@Test
	public void testCreateNewFieldTypeItem() throws Exception {
		String urlTest;
		long now = System.currentTimeMillis(); // Set time

		// GIVEN the server is up AND the server contains a User(MANAGER)
		urlTest = this.url + "/users"; // Set URL for POST user
		UserBoundary test = this.restTemplate.postForObject(urlTest, createNewManagerUser(), UserBoundary.class);
		assertThat(test.getClass()).hasSameClassAs(UserBoundary.class);

		// WHEN I POST /dts/items/{managerSpace}/{managerEmail} Using (Item boundary)
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + managerEmail; // set URL

		ItemBoundary newFieldBoundary = creatNewFieldItemBoundary(test);

		ItemBoundary actual = this.restTemplate.postForObject(urlTest, newFieldBoundary, ItemBoundary.class);
		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (Item boundary)
		assertThat(actual.getItemId()).isNotNull();
		assertThat(actual.getItemId().getId()).isNotEqualTo(newFieldBoundary.getItemId().getId());
		assertThat(actual.getItemId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getCreatedTimestamp().getTime()).isGreaterThan(now);
		assertThat(actual.getActive()).isEqualTo(true);
		assertThat(actual.getCreatedBy().getUserId().getEmail()).isEqualTo("manager@maildomain.com");
		assertThat(actual.getCreatedBy().getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getLocation().getLat()).isEqualTo(this.itemLat);
		assertThat(actual.getLocation().getLng()).isEqualTo(this.itemLng);
		assertThat(actual.getName()).isEqualTo("Demo Field");
		assertThat(actual.getType()).isEqualTo("sport_field");
	}

////Test PUT////

	@Test
	public void testUpdateExistingItem() throws Exception {
		String urlTest;

		// GIVEN the server is up
		// AND the server contains a User(PLAYER)
		// AND the server contains a User(MANAGER)
		// AND the server contains a item
		urlTest = this.url + "/users"; // Set URL for POST user
		// create 2 users
		UserBoundary player = this.restTemplate.postForObject(urlTest, createNewPlayerUser(), UserBoundary.class);
		UserBoundary manager = this.restTemplate.postForObject(urlTest, createNewManagerUser(), UserBoundary.class);
		assertThat(player.getClass()).hasSameClassAs(UserBoundary.class);
		assertThat(manager.getClass()).hasSameClassAs(UserBoundary.class);

		// create an item
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + managerEmail; // set URL for POST item
		ItemBoundary item = this.restTemplate.postForObject(urlTest, creatNewFieldItemBoundary(manager), ItemBoundary.class);
		assertThat(item.getClass()).hasSameClassAs(ItemBoundary.class);

		// WHEN I POST /dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}
		// Using (changed Item boundary)
		LocationBoundary itemLocation = new LocationBoundary(5, 4);
		item.setLocation(itemLocation);
		item.setName("change");
		item.setType("type_change");
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + managerEmail + "/"
				+ Application.APPLICATION_NAME + "/1"; // set URL for PUT item
		this.restTemplate.put(urlTest, item);

		// THEN the result HTTP STATUS 2xx
		// *(check get protocol that the item was updated)
		// Test GET
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + playerEmail + "/" + Application.APPLICATION_NAME
				+ "/1"; // set URL for GET item
		ItemBoundary actual = this.restTemplate.getForObject(urlTest, ItemBoundary.class);

		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (Item boundary)
		assertThat(actual.getItemId()).isNotNull();
		assertThat(actual.getItemId().getId()).isEqualTo(item.getItemId().getId());
		assertThat(actual.getItemId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getActive()).isEqualTo(true);
		assertThat(actual.getCreatedBy().getUserId().getEmail()).isEqualTo("manager@maildomain.com");
		assertThat(actual.getCreatedBy().getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getLocation().getLat()).isEqualTo(5);
		assertThat(actual.getLocation().getLng()).isEqualTo(4);
		assertThat(actual.getName()).isEqualTo("change");
		assertThat(actual.getType()).isEqualTo("type_change");

	}

	@Test
	public void testBindChildAndGetAllChildrenFromParents() throws Exception {
		String urlTest;

		// GIVEN the server is up
		// AND the server contains a User(PLAYER)
		// AND the server contains a User(MANAGER)
		// AND the server contains a item Field(parents)
		// AND the server contains a item sport(child)

		// create 2 users
		urlTest = this.url + "/users"; // Set URL for POST user
		UserBoundary player = this.restTemplate.postForObject(urlTest, createNewPlayerUser(), UserBoundary.class);
		UserBoundary manager = this.restTemplate.postForObject(urlTest, createNewManagerUser(), UserBoundary.class);
		assertThat(player.getClass()).hasSameClassAs(UserBoundary.class);
		assertThat(manager.getClass()).hasSameClassAs(UserBoundary.class);

		// create 2 item
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + managerEmail; // set URL for POST item
		ItemBoundary parentsItem = this.restTemplate.postForObject(urlTest, creatNewFieldItemBoundary(manager),
				ItemBoundary.class);
		assertThat(parentsItem.getClass()).hasSameClassAs(ItemBoundary.class);
		ItemBoundary childItem = this.restTemplate.postForObject(urlTest, creatNewSportItemBoundary(manager),
				ItemBoundary.class);
		assertThat(childItem.getClass()).hasSameClassAs(ItemBoundary.class);

		// WHEN I POST
		// /dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}/children Using
		// (Item boundary)
		ItemIdBoundary itemId = new ItemIdBoundary();
		itemId.setId(childItem.getItemId().getId());
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + managerEmail + "/"
				+ Application.APPLICATION_NAME + "/"+parentsItem.getItemId().getId() +"/children"; // set URL for PUT item
		this.restTemplate.put(urlTest, itemId);
		// THEN the result HTTP STATUS 2xx
		// *(check get protocol that the item was bind)
		// Test GET
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + playerEmail + "/" + Application.APPLICATION_NAME
				+ "/"+parentsItem.getItemId().getId() +"/children"; // set URL for GET item
		ItemBoundary[] actual = this.restTemplate.getForObject(urlTest, ItemBoundary[].class);

		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (Item boundary)
		assertThat(actual[0].getItemId()).isNotNull();
		assertThat(actual[0].getItemId().getId()).isEqualTo(childItem.getItemId().getId());
		assertThat(actual[0].getItemId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual[0].getActive()).isEqualTo(true);
		assertThat(actual[0].getCreatedBy().getUserId().getEmail()).isEqualTo("manager@maildomain.com");
		assertThat(actual[0].getCreatedBy().getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
//		assertThat(actual[0].getLocation().getLat()).isEqualTo(this.itemLat);
//		assertThat(actual[0].getLocation().getLng()).isEqualTo(this.itemLng);
		assertThat(actual[0].getName()).isEqualTo("Demo Sport");
		assertThat(actual[0].getType()).isEqualTo("sport_type");

	}

	@Test
	public void testBindChildAndGetAllParentsFromChildren() throws Exception {
		String urlTest;

		// GIVEN the server is up
		// AND the server contains a User(PLAYER)
		// AND the server contains a User(MANAGER)
		// AND the server contains a item Field(parents)
		// AND the server contains a item sport(child)

		// create 2 users
		urlTest = this.url + "/users"; // Set URL for POST user
		UserBoundary player = this.restTemplate.postForObject(urlTest, createNewPlayerUser(), UserBoundary.class);
		UserBoundary manager = this.restTemplate.postForObject(urlTest, createNewManagerUser(), UserBoundary.class);
		assertThat(player.getClass()).hasSameClassAs(UserBoundary.class);
		assertThat(manager.getClass()).hasSameClassAs(UserBoundary.class);

		// create 2 item
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + managerEmail; // set URL for POST item
		ItemBoundary parentsItem = this.restTemplate.postForObject(urlTest, creatNewFieldItemBoundary(manager),
				ItemBoundary.class);
		assertThat(parentsItem.getClass()).hasSameClassAs(ItemBoundary.class);
		ItemBoundary childItem = this.restTemplate.postForObject(urlTest, creatNewSportItemBoundary(manager),
				ItemBoundary.class);
		assertThat(childItem.getClass()).hasSameClassAs(ItemBoundary.class);

		// WHEN I POST
		// /dts/items/{managerSpace}/{managerEmail}/{itemSpace}/{itemId}/children Using
		// (Item boundary)
		ItemIdBoundary itemId = new ItemIdBoundary();
		itemId.setId(childItem.getItemId().getId());
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + managerEmail + "/"
				+ Application.APPLICATION_NAME + "/" + parentsItem.getItemId().getId() + "/children"; // set URL for PUT
																										// item
		this.restTemplate.put(urlTest, itemId);
		// THEN the result HTTP STATUS 2xx
		// *(check get protocol that the item was bind)
		// Test GET
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + playerEmail + "/" + Application.APPLICATION_NAME
				+ "/" + childItem.getItemId().getId() + "/parents"; // set URL for GET item
		ItemBoundary[] actual = this.restTemplate.getForObject(urlTest, ItemBoundary[].class);

		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (Item boundary)
		assertThat(actual[0].getItemId()).isNotNull();
		assertThat(actual[0].getItemId().getId()).isEqualTo(parentsItem.getItemId().getId());
		assertThat(actual[0].getItemId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual[0].getActive()).isEqualTo(true);
		assertThat(actual[0].getCreatedBy().getUserId().getEmail()).isEqualTo("manager@maildomain.com");
		assertThat(actual[0].getCreatedBy().getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual[0].getLocation().getLat()).isEqualTo(this.itemLat);
		assertThat(actual[0].getLocation().getLng()).isEqualTo(this.itemLng);
		assertThat(actual[0].getName()).isEqualTo("Demo Field");
		assertThat(actual[0].getType()).isEqualTo("sport_field");

	}
////Test GET////

	@Test
	public void testGetFieldItemById() throws Exception {
		String urlTest;

		// GIVEN the server is up
		// AND the server contains a User(PLAYER)
		// AND the server contains a User(MANAGER)
		// AND the server contains a item
		urlTest = this.url + "/users"; // Set URL for POST user
		// create 2 users
		UserBoundary player = this.restTemplate.postForObject(urlTest, createNewPlayerUser(), UserBoundary.class);
		UserBoundary manager = this.restTemplate.postForObject(urlTest, createNewManagerUser(), UserBoundary.class);
		assertThat(player.getClass()).hasSameClassAs(UserBoundary.class);
		assertThat(manager.getClass()).hasSameClassAs(UserBoundary.class);
		// create an item
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + managerEmail; // set URL for POST item
		ItemBoundary item = this.restTemplate.postForObject(urlTest, creatNewFieldItemBoundary(manager), ItemBoundary.class);
		assertThat(item.getClass()).hasSameClassAs(ItemBoundary.class);

		// WHEN I POST /dts/items/{userSpace}/{userEmail}/{itemSpace}/{itemId}
		urlTest = this.url + "/items/" + Application.APPLICATION_NAME + playerEmail + "/" + Application.APPLICATION_NAME
				+ "/" +item.getItemId().getId(); // set URL for GET item
		ItemBoundary actual = this.restTemplate.getForObject(urlTest, ItemBoundary.class);

		// THEN the result HTTP STATUS 2xx
		// AND the server returns the JOSN (Item boundary)
		assertThat(actual.getItemId()).isNotNull();
		assertThat(actual.getItemId().getId()).isEqualTo(item.getItemId().getId());
		assertThat(actual.getItemId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getActive()).isEqualTo(true);
		assertThat(actual.getCreatedBy().getUserId().getEmail()).isEqualTo("manager@maildomain.com");
		assertThat(actual.getCreatedBy().getUserId().getSpace()).isEqualTo(Application.APPLICATION_NAME);
		assertThat(actual.getLocation().getLat()).isEqualTo(this.itemLat);
		assertThat(actual.getLocation().getLng()).isEqualTo(this.itemLng);
		assertThat(actual.getName()).isEqualTo("Demo Field");
		assertThat(actual.getType()).isEqualTo("sport_field");
	}

}
