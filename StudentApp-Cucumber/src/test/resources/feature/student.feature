Feature: Testing different requests on student application

@SMOKE
Scenario: Check if student application can be accessed by users
	When User sends a GET request to the list endpoint, they must get back a valid status code 200

Scenario Outline:Create a new student & veify if the student is added
	When I create a new student by providing the information firstName <firstName> lastName <lastName> email <email> programme <programme> courses <courses>
	Then I verify that the student with <email> is created
	
	Examples:
		| firstName  | lastName | email                  | programme              | courses |
		| Steve		 | Smith    | steve@smithorg.com	 | Computer Science       | Java    |
		| Mike       | Mayers   | mike.mayers@ap.com     | Computer Science		  | Java    |