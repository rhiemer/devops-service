#!/bin/bash
set -o errexit
 
POSITIONAL=()
while [[ $# -gt 0 ]]
do
  key="$1"
  case $key in    
      -v|--verbose)
      set -x    
      VERBOSE="${1}"
      shift # past argument      
      ;;            
      --settings)
      SETTINGS="${2}"
      shift # past argument      
      shift # past argument      
      ;;
      --local-repository)
      LOCAL_REPOSITORY="${2}"
      shift # past argument      
      shift # past argument      
      ;;
      --url)
      URL_MVN="${2}"
      shift # past argument      
      shift # past argument      
      ;;
      --repositoryId)
      REPOSITORY_ID="${2}"
      shift # past argument      
      shift # past argument      
      ;;       
      --usuario)
      USUARIO_MVN="${2}"
      shift # past argument      
      shift # past argument      
      ;;      
      --senha)
      SENHA_MVN="${2}"
      shift # past argument      
      shift # past argument      
      ;;
      *)    # unknown option
      POSITIONAL+=( $1 ) # save it in an array for later
      shift # past argument
      ;;
  esac
done
 
# restore positional parameters
set -- "${POSITIONAL[@]}" 



if [ ! -z "${URL_MVN// }" ]; then
  
  REPOSITORY_ID_DEFAULT="${URL_MVN##*/}"
  REPOSITORY_ID="${REPOSITORY_ID:-$REPOSITORY_ID_DEFAULT}"  
  MIRROR_OF="${REPOSITORY_ID}-mirror"
  
  _LOCAL_REPOSITORY="${LOCAL_REPOSITORY:+<localRepository>$LOCAL_REPOSITORY</localRepository>}"
  
  touch "$SETTINGS"
  if [[ ! -z "${USUARIO_MVN// }" && ! -z "${SENHA_MVN// }" ]]; then
    CREDENCIAIS="<servers><server><id>$REPOSITORY_ID</id><username>$USUARIO_MVN</username><password>$SENHA_MVN</password></server></servers>" 
  fi  
  
cat > $SETTINGS <<_EOF
<?xml version="1.0" encoding="UTF-8"?>
<settings xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd" xmlns="http://maven.apache.org/SETTINGS/1.1.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
      $_LOCAL_REPOSITORY
      <mirrors>
          <mirror>
            <mirrorOf>$MIRROR_OF</mirrorOf>
            <url>$URL_MVN/</url>
            <id>$REPOSITORY_ID</id>
          </mirror>
      </mirrors> 
      $CREDENCIAIS     
      <profiles>        
    <profile>
      <id>$REPOSITORY_ID</id>
      <repositories>        
        <repository>
          <releases>
            <updatePolicy>always</updatePolicy>
          </releases>
          <snapshots>
            <updatePolicy>always</updatePolicy>
          </snapshots>
          <id>$REPOSITORY_ID</id>
          <url>$URL_MVN</url>
        </repository>       
      </repositories>
      <pluginRepositories>
        <pluginRepository>
          <releases>
            <updatePolicy>always</updatePolicy>
          </releases>
          <snapshots>
            <updatePolicy>always</updatePolicy>
          </snapshots>
          <id>$REPOSITORY_ID</id>
          <url>$URL_MVN</url>
        </pluginRepository>
      </pluginRepositories>
    </profile>
  </profiles>
  <activeProfiles>
    <activeProfile>$REPOSITORY_ID</activeProfile> 
  </activeProfiles>   
</settings>      
_EOF
else
  cp -rf /usr/share/maven/ref/settings-docker.xml "$SETTINGS"  
fi
    



  
