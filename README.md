# LDAP API

Developed by Dante Judson :) 

# Instalation 
## Requirements
- This application requires docker-compose to run, to install docker-compose in your machine please folow this [LINK](https://docs.docker.com/compose/install/)
- You need to have port 8080 available, else go to how setup custom port.
Docker-compose will take care of all the stuff you need to have the app up and running, you just need to navigate to the projetct's root folder and run the command.

```sh
docker-compose up
```

## Api Documentation

To see the api endpoints documentation access:
Where default port is 8080
```
http://localhost:<port>/swagger-ui.html
```

## How to setup custom port

If you want to setup a custom port for the api please find the docker-compose.yml file and change the line 26 to:

```
-"<custom-port>:8080"
```

and replace the `<custom-port>` with the number of port you want to use for api.