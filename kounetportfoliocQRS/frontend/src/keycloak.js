// src/Keycloak.js
import Keycloak from "keycloak-js";

// On utilise une seule instance partagée
let keycloakInstance = null;

if (!keycloakInstance) {
  keycloakInstance = new Keycloak({
    url: "http://localhost:8080",
    realm: "polyinformatiqueEcommerce",
    clientId: "frontend",
  });
}

export default keycloakInstance;
