package com.studentapp.junit.studentsinfo;

import static org.hamcrest.Matchers.hasValue;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.studentapp.cucumber.serenity.StudentSerenitySteps;
import com.studentapp.model.StudentClass;
import com.studentapp.testbase.TestBase;
import com.studentapp.utils.ReusableSpecifications;
import com.studentapp.utils.TestUtils;

import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;

@RunWith(SerenityRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentsCRUDTest extends TestBase{
	
	static String firstName = "SAI"+ TestUtils.generateRandomValue();
	static String lastName = "KUMAR"+TestUtils.generateRandomValue();
	static String programme = "Quality Engineering";
	static String email = TestUtils.generateRandomValue()+"sk@abc.com";
	static int studentId;

	
	@Steps
	StudentSerenitySteps steps;
	@Title("This Test will create a New Student")
	@Test
	public void test001() {
		
		ArrayList<String>courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("REST ASSURED");
		
		steps.createStudent(firstName, lastName, email, programme, courses)
		.statusCode(201)
		.spec(ReusableSpecifications.getGenericResponseSpec());
	}
	
	@Title("Verify if the student was added to the application")
	@Test
	public void test002() {
		
	HashMap<String, Object> value = steps.getStudentInfoByFirstName(firstName);
	assertThat(value, hasValue(firstName));
	studentId = (int)value.get("id");
	
	}
	
	@Title("Update the user informatiand and verify the same")
	@Test
	public void test003() {
		String jsonPath1 = "findAll{it.firstName=='";
		String jsonPath2 = "'}.get(0)";
		ArrayList<String>courses = new ArrayList<String>();
		courses.add("JAVA");
		courses.add("REST ASSURED");
		firstName = firstName+"_Updated";
		steps.updateStudent(studentId, firstName, lastName, email, programme, courses);
		
		HashMap<String, Object> value = steps.getStudentInfoByFirstName(firstName);
		assertThat(value, hasValue(firstName));
	}
	
	@Title("Delete the student record and verify if its deleted")
	@Test
	public void test004() {
		
		steps.deleteStudent(studentId);
		steps.getStudentById(studentId)
		.statusCode(404);
	}
	
	
}
