import React, {Component} from 'react';
import {Container, Navbar, NavbarBrand} from 'reactstrap';
import {Link} from "react-router-dom";

export default class AppNavbar extends Component {
    constructor(props) {
        super(props);
        this.state = {isOpen: false};
        this.toggle = this.toggle.bind(this);
    }

    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    render() {
        return (
            <nav className="navbar navbar-expand-lg navbar-dark  bg-dark">
                <Link className="navbar-brand navbar-dark" to="/">Клиенты</Link>
                <ul className="navbar-nav">
                    <li className="nav-item">
                        <Link className="nav-link" to=""></Link>
                    </li>
                </ul>
            </nav>
        )
    }
}