import React, { useState, useTransition } from "react";
import "./Navbar.css";
import logo from "@/assets/hoaxify.png";
import { Link } from "react-router-dom";
import { useTranslation } from "react-i18next";
import { LanguageSelector } from "../LanguageSelector";

const Navbar = () => {
  const [isOpen, setIsOpen] = useState(false);
  const{t} = useTranslation();
  const toggleMenu = () => setIsOpen(!isOpen);

  return (
    <nav className="navbar">
      <div className="container">
        <Link to="/" className="navbar-brand">
          {/* <img src={logo} width="100" alt="Hoaxify" /> */}
          <img src={logo} width="150" alt="Hoaxify" />
        </Link>
        <ul className={`nav-links ${isOpen ? "active" : ""}`}>
          <li>
            <Link to="/signup" className="navbar-brand">{t('signUp')}</Link>
          </li>
          <li>
            <a href="#about">Hakkımızda</a>
          </li>
          <li>
            <a href="#services">Hizmetler</a>
          </li>
          <li>
          <LanguageSelector/>
          </li>
        </ul>
      </div>
      
    </nav>
  );
};

export default Navbar;
