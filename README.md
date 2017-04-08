**1. Overview**
	This application is a collection of various apps compiled into one. The different features include:
	 - a speech to text converter
	 - a text to speech converter 
	 - a language translator
	 - an address book that uses ClearDB
	 
**2. Technology used:**
	 - *Language Translator*: translates text from one language     to another. The service offers multiple domain-specific models that you can customize based on your unique terminology and language. Use Language Translator to take news from across the globe and present it in your language, communicate with your customers in their own language, and more.
	 - *Text To Speech*: Text to Speech converts written text into natural sounding audio in a variety of languages and voices. You can customize and control the pronunciation of specific words to deliver a seamless voice interaction that catered s to your audience. Use text to speech to develop interactive toys for children, automate call center interactions, and communicate directions hands-free.
	 - *Speech To Text*: Watson Speech to Text converts audio voice into written text. Use Speech to Text to transcribe calls in a contact center to identify what is being discussed, when to escalate calls, and to understand content from multiple speakers. Use speech to text to create voice-controlled applications – even customize the model to improve accuracy for the language and content you care about most such as product names, sensitive subjects, or names of individuals.
	 - *ClearDB*: ClearDB Inc. is a pioneer of cloud-based technologies and solutions that optimize the computing efficiency and utility of any database application. ClearDB cloud services provide customers with SLA-backed guaranteed high availability, accelerated performance, easy administration to deliver unprecedented levels of database reliability, efficiency and simplicity on major cloud platforms and on-premise data centers.
	 - *CloudantDB*: Cloudant NoSQL DB is a fully managed data layer designed for modern web and mobile applications that leverages a flexible JSON schema. Cloudant is built upon and compatible with Apache CouchDB and accessible through a secure HTTPS API, which scales as your application grows. Cloudant is ISO27001 and SOC2 Type 1 certified, and all data is stored in triplicate across separate physical nodes in a cluster for HA/DR within a data center.
	 - *Personality Analytics*: Personality Insights extracts personality characteristics based on how a person writes. You can use the service to match individuals to other individuals, opportunities, and products, or tailor their experience with personalized messaging and recommendations. Characteristics include the Big 5 Personality Traits, Values, and Needs. At least 1200 words of input text are recommended when using this service.

**3. How to deploy in bluemix**
 	1. fork the following github links:
	https://github.com/artorralbaiii/blmx_languagetranslator
	https://github.com/artorralbaiii/blmx_speechtotext
	https://github.com/artorralbaiii/blmx_texttospeech
	https://github.com/artorralbaiii/blmx_mysql_v2

	2.Create and connect a Bluemix Devops Project
	
	-Link it to the created repo
	-Make sure default settings are set and the region is IBM Bluemix US South
	-Open the Devops Git option and click “Commit” and “Push” to have your Devops project sync 	
	 with each other. 
	-edit in gradle.build change all "compile only" to "compile
	-commit and push again
	-Go to the “Build and Deploy”
	-Add the Build Stage

	Input Type	SCM Repository
	GIT URL	https://github.com//devops-delivery-pipeline.gitBranch	Master
	Stage Trigger	Run jobs whenever a change is pushed to Git	

		The following values should be set for the Jobs tab and select as Add Job:
		Job Name	Gradle Assemble
		Builder Type	Gradle
		Build Shell Command		#!/bin/bash gradle assemble
		Stop running this stage if this job fails	checked
	
	-Save




	Add the Test Stage 
	The following values should be set as on the Input Tab:
		Input Type		Build Artifacts
		Stage	Build Stage
		Job	Gradle Assemble
		Stage Trigger Run	jobs whenever a change is pushed to Git
	
	The following values should be set for the Jobs tab and select as test:
		Job name	JUnit Test through Gradle
		Tester 	Type	Gradle
		Build Shell Command	#!/bin/bash Gradle Test
		Stop running this stage if this job fails	checked

	-Save
	

	Add the Deploy Stage and name it as is
	The following values should be set as on the Input Tab:
		Input Type	Build Artifacts
		Stage	Build Stage
		Job	Gradle Assemble
		Stage Trigger	Run jobs whenever a change is pushed to Git

	The following values should be set for the Jobs tab and select as test:
		Job name	Cloud foundry push to Dev Space
		Deployer Type		Gradle
		Target	IBM Bluemix US South	https://api.ng.bluemix.netOrganization 		Default
		Space   	Default
		Application Name	blank
		Build Shell Command	#!/bin/bash cf push <name_of_the_application> -m 256M -p build/libs/<.war 
		filename> -b liberty-for-java_v3_7-20170118-2046build/libs/calcuapp.war
		Stop running this stage if this job fails	checked
	

	-Save

	Deploy the Java Application in Bluemix

	Delivery Pipeline

		-Click the “Run Stage” part of the Build stage
		
	Automatically Start the Application on Bluemix
		-Push and commit the change of the code
		-The application should start automatically

	How to Bind a Bluemix Service to your application.
	-Deploy the application using Delivery Pipeline with the repo being set to  	 	
	https://github.com/artorralbaiii/blmx_languagetranslator. 
	-Move to Bluemix www.bluemix.com 
	-Go to the Dashboard
	-Click create a service button
	-Bind it to your application by selecting the application name from the Connect To dropdown 
	 and click the Create Button.
	-Apply
	-Restage.
	(Repeat the process for all individual applications)
	
	3.Run the main application that binds all the applications together
	
	
