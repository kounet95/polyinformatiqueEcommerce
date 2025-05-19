-- Initialize database schema for polyinfDb

-- Create extension for pgvector
CREATE EXTENSION IF NOT EXISTS vector;

-- Create schemas if they don't exist
CREATE SCHEMA IF NOT EXISTS blog;
CREATE SCHEMA IF NOT EXISTS ecommerce;

-- Blog schema tables
CREATE TABLE IF NOT EXISTS blog.articles (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    author_id UUID NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS blog.utilisateurs (
    id UUID PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS blog.commentaires (
    id UUID PRIMARY KEY,
    article_id UUID NOT NULL REFERENCES blog.articles(id),
    utilisateur_id UUID NOT NULL REFERENCES blog.utilisateurs(id),
    content TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Ecommerce schema tables
CREATE TABLE IF NOT EXISTS ecommerce.produits (
    id UUID PRIMARY KEY,
    nom VARCHAR(255) NOT NULL,
    description TEXT,
    prix DECIMAL(10, 2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ecommerce.categories (
    id UUID PRIMARY KEY,
    nom VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE IF NOT EXISTS ecommerce.produit_categories (
    produit_id UUID REFERENCES ecommerce.produits(id),
    categorie_id UUID REFERENCES ecommerce.categories(id),
    PRIMARY KEY (produit_id, categorie_id)
);

CREATE TABLE IF NOT EXISTS ecommerce.commandes (
    id UUID PRIMARY KEY,
    utilisateur_id UUID NOT NULL REFERENCES blog.utilisateurs(id),
    statut VARCHAR(50) NOT NULL,
    montant_total DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS ecommerce.commande_items (
    id UUID PRIMARY KEY,
    commande_id UUID NOT NULL REFERENCES ecommerce.commandes(id),
    produit_id UUID NOT NULL REFERENCES ecommerce.produits(id),
    quantite INTEGER NOT NULL,
    prix_unitaire DECIMAL(10, 2) NOT NULL
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_articles_author ON blog.articles(author_id);
CREATE INDEX IF NOT EXISTS idx_commentaires_article ON blog.commentaires(article_id);
CREATE INDEX IF NOT EXISTS idx_commentaires_utilisateur ON blog.commentaires(utilisateur_id);
CREATE INDEX IF NOT EXISTS idx_commandes_utilisateur ON ecommerce.commandes(utilisateur_id);
CREATE INDEX IF NOT EXISTS idx_commande_items_commande ON ecommerce.commande_items(commande_id);
CREATE INDEX IF NOT EXISTS idx_commande_items_produit ON ecommerce.commande_items(produit_id);

-- Grant privileges
GRANT ALL PRIVILEGES ON SCHEMA blog TO admin;
GRANT ALL PRIVILEGES ON SCHEMA ecommerce TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA blog TO admin;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA ecommerce TO admin;