-- phpMyAdmin SQL Dump
-- version 3.4.11.1deb2+deb7u1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Mer 17 Décembre 2014 à 20:30
-- Version du serveur: 5.5.40
-- Version de PHP: 5.4.35-0+deb7u2

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `supmessaging`
--

-- --------------------------------------------------------

--
-- Structure de la table `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dateMessage` varchar(500) CHARACTER SET utf8 NOT NULL,
  `corpus` text CHARACTER SET utf8 NOT NULL,
  `id_user_author` int(11) NOT NULL,
  `id_user_receiver` int(11) NOT NULL,
  `mail` varchar(100) CHARACTER SET utf8 NOT NULL,
  `readMessage` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user_author` (`id_user_author`,`id_user_receiver`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='read: Si lu:1   Si non lu:0' AUTO_INCREMENT=37 ;

--
-- Contenu de la table `messages`
--

INSERT INTO `messages` (`id`, `dateMessage`, `corpus`, `id_user_author`, `id_user_receiver`, `mail`, `readMessage`) VALUES
(35, '2014/12/17 20:03:58', 'Hey', 1, 39, '1@supmessaging.com', 0),
(36, '2014/12/17 20:15:08', 'Bonjour!', 39, 38, '39@supmessaging.com', 0);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 NOT NULL,
  `firstname` varchar(50) CHARACTER SET utf8 NOT NULL,
  `mail` varchar(100) CHARACTER SET utf8 NOT NULL,
  `pseudo` varchar(50) CHARACTER SET utf8 NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 NOT NULL,
  `roleUser` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`mail`,`pseudo`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='role 0:Admin 1:User 2:anonyme' AUTO_INCREMENT=51 ;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`id`, `name`, `firstname`, `mail`, `pseudo`, `password`, `roleUser`) VALUES
(0, 'anonyme', 'anonyme', 'anonyme@supmessaging.com', 'anonyme', 'NO PASSWORD', 0),
(1, 'Angelini', 'Adrien', '158113@supinfo.com', 'adrienxp3', '512ab1eaef3c77c7c1e9c51691f14d5711e2882049e67fec6f5ea5906b4a24e02b35e5e14ade3b2c9c864c979b3162c9073cad724c13b4dc2ebf46bf521', 2),
(38, 'Chapalain', 'Matthieu', 'matthieu.magouille@supinfo.com', 'chapalou14', 'b05f7c192eabc5755debf562d8d2558c6fb3bf2995680c6e6a322e51bedd07b216231d562b8a89b55875f486fc6548926f8a786016e2df3b6163c43704ab1', 1),
(39, 'Rambo', 'John-rachid', 'Deojoed@mail.com', 'Carmain', '50545a33f70359abbc2d64a87a24c94f291ee421e73d41f28c8252c696795bdc632a26583e3db48774a63c4571d6253f51bfda739daaa7f1dc9c875a2a9f', 1),
(41, 'Blake', 'James', 'j.blake@tennis.com', 'blake43', 'b05f7c192eabc5755debf562d8d2558c6fb3bf2995680c6e6a322e51bedd07b216231d562b8a89b55875f486fc6548926f8a786016e2df3b6163c43704ab1', 1);

-- --------------------------------------------------------

--
-- Structure de la table `user_friendship`
--

CREATE TABLE IF NOT EXISTS `user_friendship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_users1` int(11) NOT NULL,
  `id_users2` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='table intermédiaire pour les contacts' AUTO_INCREMENT=86 ;

--
-- Contenu de la table `user_friendship`
--

INSERT INTO `user_friendship` (`id`, `id_users1`, `id_users2`) VALUES
(84, 39, 1),
(85, 39, 38);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
