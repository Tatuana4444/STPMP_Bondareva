import React, {Component} from 'react';
import { Navbar, Nav, Container, Offcanvas } from 'react-bootstrap';
import axios from "axios";

export default class AppNavbar extends Component {
    constructor(props) {
        super(props);
        this.state = {today: ''};
    }
    componentDidMount() {
        axios.get('/financialOperation')
            .then(response => {
                this.setState({today: response.data});
            })
    }

    closeDay = () =>{
        axios.put(`/financialOperation/closeDay`)
            .then(response => {
                this.setState({today: response.data});
            })
    }

    render() {
        return (
            <Navbar collapseOnSelect  expand='sm' bg='dark' variant='dark'>
                <Container fluid>
                    <Navbar.Toggle aria-controls='responsive-navbar-nav'/>
                    <Navbar.Offcanvas
                        id={`offcanvasNavbar-expand-sm`}
                        aria-labelledby={`offcanvasNavbarLabel-expand-sm`}
                        placement="end"
                    >
                        <Offcanvas.Body>
                            <Nav className="flex-grow-1 pe-3">
                                <Nav.Link href="/#/">Клиенты</Nav.Link>
                                <Nav.Link href="/#/agreements/">Контракты</Nav.Link>
                                <Nav.Link href="/#/accounts/">Счета</Nav.Link>
                                <Nav.Link href="/#/operations/">Операции</Nav.Link>
                                <Nav.Link href="/#/cards/">Карты</Nav.Link>
                                <Nav.Link href="/#/ATM/">Банкомат</Nav.Link>
                                <Nav.Link onClick={() => this.closeDay()}>Закончить день</Nav.Link>
                            </Nav>
                        </Offcanvas.Body>
                    </Navbar.Offcanvas>
                    <span className="navbar-text d-flex my-2 my-lg-0">{this.state.today}</span>
                </Container>
            </Navbar>

        )
    }
}