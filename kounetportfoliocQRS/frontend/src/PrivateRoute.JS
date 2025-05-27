import React from "react";
import { useKeycloak } from "@react-keycloak/web";

const PrivateRoute = ({ children, requiredRole }) => {
  const { keycloak, initialized } = useKeycloak();

  if (!initialized) return <div>Chargement de Keycloak...</div>;

  if (!keycloak.authenticated) return <div>Non autorisé</div>;

  if (requiredRole && !keycloak.hasRealmRole(requiredRole)) {
    return <div>Accès refusé</div>;
  }

  return children;
};

export default PrivateRoute;
