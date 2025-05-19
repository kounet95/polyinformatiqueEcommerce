# Étape 1 : Construction
FROM node:18.19-alpine AS build

WORKDIR /usr/local/app

# Copier package.json et package-lock.json pour optimiser le cache Docker
COPY package*.json ./

# Mettre à jour npm et installer toutes les dépendances avec legacy-peer-deps
ENV CI=true
RUN npm install -g npm@latest && \
    npm install --legacy-peer-deps && \
    npm cache clean --force

# Copier le reste du code source
COPY . .

# Générer le build de l'application
RUN CI=true npm run build

# Étape 2 : Serveur
FROM nginx:1.23.4

# Copier les fichiers construits depuis l'étape 1
COPY --from=build /usr/local/app/build /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80

# Démarrer nginx
CMD ["nginx", "-g", "daemon off;"]
