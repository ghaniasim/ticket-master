import React, { Component } from 'react';
import {Nav, Navbar,NavItem, NavbarBrand, NavLink} from 'reactstrap';

class AppNav extends Component {
    render() {
        return (
            <div>
                <Navbar color="dark" dark expand="md">
                <NavbarBrand href="/">Ticket Master</NavbarBrand>
                <Nav className="ml-auto" navbar>
                <NavItem>
                    <NavLink href="/">Home</NavLink>
                </NavItem>
                <NavItem>
                    <NavLink href="/user">User</NavLink>
                </NavItem>
                <NavItem>
                <NavLink href="/admin">Admin</NavLink>
                </NavItem>
                </Nav>
                </Navbar>
            </div>
        );
    }
}

export default AppNav;