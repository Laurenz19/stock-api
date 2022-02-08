-- phpMyAdmin SQL Dump
-- version 5.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 08 fév. 2022 à 14:30
-- Version du serveur :  10.4.11-MariaDB
-- Version de PHP : 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `stockdb`
--

-- --------------------------------------------------------

--
-- Structure de la table `bondeentree`
--

CREATE TABLE `bondeentree` (
  `id` varchar(10) NOT NULL DEFAULT '0',
  `produit` varchar(10) NOT NULL,
  `qteEntree` int(11) DEFAULT NULL,
  `dateEntree` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déclencheurs `bondeentree`
--
DELIMITER $$
CREATE TRIGGER `tg_bondeentree_insert` BEFORE INSERT ON `bondeentree` FOR EACH ROW BEGIN
  	INSERT INTO bondeentree_seq VALUES (NULL);
  	SET NEW.id = CONCAT('BE', LPAD(LAST_INSERT_ID(), 5, '0'));
	END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `bondeentree_seq`
--

CREATE TABLE `bondeentree_seq` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `bondesortie`
--

CREATE TABLE `bondesortie` (
  `id` varchar(10) NOT NULL DEFAULT '0',
  `produit` varchar(10) NOT NULL,
  `qteSortie` int(11) DEFAULT NULL,
  `dateSortie` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déclencheurs `bondesortie`
--
DELIMITER $$
CREATE TRIGGER `tg_bondesortie_insert` BEFORE INSERT ON `bondesortie` FOR EACH ROW BEGIN
  	INSERT INTO bondesortie_seq VALUES (NULL);
  	SET NEW.id = CONCAT("BS", LPAD(LAST_INSERT_ID(), 4, '0'));
	END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `bondesortie_seq`
--

CREATE TABLE `bondesortie_seq` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id` varchar(10) NOT NULL DEFAULT '0',
  `design` varchar(255) NOT NULL,
  `stock` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déclencheurs `produit`
--
DELIMITER $$
CREATE TRIGGER `tg_produit_insert` BEFORE INSERT ON `produit` FOR EACH ROW BEGIN
  	INSERT INTO produit_seq VALUES (NULL);
  	SET NEW.id = CONCAT('P', LPAD(LAST_INSERT_ID(), 4, '0'));
	END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `produit_seq`
--

CREATE TABLE `produit_seq` (
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `bondeentree`
--
ALTER TABLE `bondeentree`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Produit_bondeEntree` (`produit`);

--
-- Index pour la table `bondeentree_seq`
--
ALTER TABLE `bondeentree_seq`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `bondesortie`
--
ALTER TABLE `bondesortie`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK_Produit_bondesortie` (`produit`);

--
-- Index pour la table `bondesortie_seq`
--
ALTER TABLE `bondesortie_seq`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `production_unique` (`design`);

--
-- Index pour la table `produit_seq`
--
ALTER TABLE `produit_seq`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `bondeentree_seq`
--
ALTER TABLE `bondeentree_seq`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `bondesortie_seq`
--
ALTER TABLE `bondesortie_seq`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `produit_seq`
--
ALTER TABLE `produit_seq`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `bondeentree`
--
ALTER TABLE `bondeentree`
  ADD CONSTRAINT `FK_Produit_bondeEntree` FOREIGN KEY (`produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `bondesortie`
--
ALTER TABLE `bondesortie`
  ADD CONSTRAINT `FK_Produit_bondesortie` FOREIGN KEY (`produit`) REFERENCES `produit` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
