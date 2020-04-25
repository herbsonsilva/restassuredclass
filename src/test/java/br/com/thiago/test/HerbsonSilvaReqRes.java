package br.com.thiago.test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.thiago.servicos.Servicos;
import br.com.thiago.test.entidate.PessoaRequest;
import br.com.thiago.test.entidate.PessoaResponse;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;

public class HerbsonSilvaReqRes {

	@Before
	public void configuraApi() {
		baseURI = 	"https://reqres.in/";
	}
	
	// GET
	@Test
	public void methodGet() {
		given()
		.when()
			.get(Servicos.getUser_ID.getValor(), 7)
		.then().contentType(ContentType.JSON)
			.statusCode(HttpStatus.SC_OK)
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/userGET.json"))
			.log().all();
	}
	
	// POST
	@Test
	public void methodPost() {
		PessoaRequest pessoaRequest = new PessoaRequest("Herbson","QA");
		
		PessoaResponse response = given()
			.contentType("application/json")
		.body(pessoaRequest)
		.when()
			.post(Servicos.postUser.getValor())
		.then()
			.statusCode(HttpStatus.SC_CREATED)
			.and()
			.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("Schemas/userPOST.json"))
			.log().all()
			.extract().response().as(PessoaResponse.class);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(pessoaRequest.getName(), response.getName());
		Assert.assertEquals(pessoaRequest.getJob(), response.getJob());
		Assert.assertNotNull(response.getId());
		Assert.assertNotNull(response.getCreatedAt());
	}
	
	// PUT
	@Test
	public void methodPut() {
		PessoaRequest pessoaRequest = new PessoaRequest("Silva","Dev");
		
		PessoaResponse response = given()
			.contentType(ContentType.JSON)
		.body(pessoaRequest)
		.when()
			.put(Servicos.putUser_ID.getValor(), 7)
		.then()
			.assertThat()
			.statusCode(HttpStatus.SC_OK).log().all()
			.extract().response().as(PessoaResponse.class);
		
		Assert.assertNotNull(response);
		Assert.assertEquals(pessoaRequest.getName(), response.getName());
		Assert.assertEquals(pessoaRequest.getJob(), response.getJob());
		Assert.assertNotNull(response.getUpdatedAt());
	}
	
	// DELETE
	@Test
	public void methodDelete() {
		given()
			.contentType("application/json")
		.when()
			.delete(Servicos.deleteUser_ID.getValor(), 7)
		.then()
			.assertThat()
			.statusCode(HttpStatus.SC_NO_CONTENT)
			.log().all();
	}
	

}
