node {
  stage name:"Checkout"
  checkout scm;

  stage name:"Build"
  build()
}

def build() {
  
  def mvnHome = tool 'latest'
  env.MAVEN_OPTS="-Xmx2G";
  withJavaEnv {
    sh "${mvnHome}/bin/mvn clean install"
  }
}
