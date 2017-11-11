
## Installation
1. Open the terminal on project folder

2. Compile the idl file using the idlj command as below:

	```bash
	idlj  -fall  Addition.idl
	```

3. After, tun the following command to compile java files:

	```bash
	javac *.java
	```
4. Run this command:

	```bash
	start orbd -ORBInitialPort 1050
	```

5. Than, run this command to star server:

	```bash
	java StartServer -ORBInitialPort 1050 -ORBInitialHost localhost&
	```

6. And now, this command to star client:

	```bash
	java StartClient -ORBInitialPort 1050 -ORBInitialHost localhost
	```

## Mapping Error Number

Code | Message
:---: | ---
**0** | Success
**1** | File already exist
**2** | File not founded
**3** | Error on writer
**4** | ERROR
**5** | Copy File already exist
**6** | Error reading file
