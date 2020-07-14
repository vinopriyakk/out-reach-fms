# outreachfms
<h2>Spring Boot</h2>

<h3>First step is to configure the application</h3>

<b>Configuring application for local setup</b>

As this application is communicating with database, the first step is to setup the database.

The dump file for the dabase is kept inside the resource folder within the application. Just executing the "cts_outreach_fms.sql" file will create the database with some master data information.
The link of the dabase dumb is given below.
<pre><a href="https://github.com/vinopriyakk/outreachfms/tree/master/cts-outreach-fms-services/src/main/resources/config">https://github.com/vinopriyakk/outreachfms/tree/master/cts-outreach-fms-services/src/main/resources/dbscript</a></pre>

<b>Note:</b> Admin credentials are already preconfigured for easy login to the application.

<pre>
username: 564876
password: test@123
</pre>

PMO, POC and participants data will be stored by processing the excels.

<b>Setting up the Local environment in spring Boot</b>
  
Download and install the STS. Then open the sts. From the window menu, under show view open git prespective. Then from there clone the git repository of this outreach-fms application. The git clone url is given below
  
  <pre>https://github.com/vinopriyakk/out-reach-fms</pre>
  
Once done with the cloning of repository right click the git application icon and import the project to the workspace.
Then the application is available in the workspace package explorer.

<b>Configuring the application for launching in the local server</b>

some values need to be setup in the below application-dev.yml
<pre><a href="https://github.com/vinopriyakk/outreachfms/tree/master/cts-outreach-fms-services/src/main/resources/config">application-dev.yml</a></pre>

MySQL database configuration

<pre>spring.datasource.url=jdbc:mysql://localhost:3306/cts_outreach_fms</pre>
The line in the above line in properties file describes the environment, port and database name. Set the database name as <b>cts_outreach_fms</b>

<pre>
spring.datasource.username=root
spring.datasource.password=admin
</pre>

This above line has the database username and password that needs to be set accordingly, of how the database is initially configured.

<pre>
fms.shared.file.path= /batchUploadpath
fm.shared.file.new-path-name= /batchArchivepath
</pre>

The above line has the local file system path where the excel files need to be uploaded and where the archive need to be backed up. Set the path accordingly.
In this case uploaded files will be intially kept inside "batchUploadpath" and the archive file will be save inside "excelArchive" folder which is inside "batchArchivepath" folder.


As the application is configured for gmail smtp, Email and password needs to be provided for sending email from the given address.

<pre>
spring.mail.username=email@email.com
spring.mail.password=password
</pre>

This above values must be configured with email address and password of gmail.
<b>Note:</b> Turn on enable less secure apps in gmail account setting to transport mail from within the outreachfms application.
If enable less secure apps is turned off email will not be send and produce error.

<pre>
email.properties.node-url=http://localhost:4200
</pre>
The above url may change according to the running enviroment. Just like http://localhost:4200 or htp://192.168.1.1:4200 in cloud environment IP.

This is the most important setup just to provide the angular connectivity for accessing feedback form by clicking the email link.
The above property must be the url of in which angular application will be served on calling from browser.










