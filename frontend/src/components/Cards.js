import React, {Component} from 'react';
import axios from "axios";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';

class Cards extends Component{

    constructor(props) {
        super(props);
        this.state = {cards: []};
    }

    componentDidMount() {
        axios.get(`/cards`)
            .then(response => {
                this.setState({cards: response.data});
            })


    }


    render() {
        const {cards, isLoading} = this.state;
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const cardsList = cards.map(card => {
            return <tr key={card.id}>
                <td><Link to={"/accounts/" + card.accountNumber}>{card.accountNumber}</Link></td>
                <td>{card.number}</td>
                <td>{card.pin}</td>
            </tr>
        });
        return (
            <div>
                <AppNavbar/>
                <Container fluid>

                    <h3>Список карт</h3>
                    <Table className="table">
                        <thead>
                        <tr>
                            <th>Cчет</th>
                            <th>Номер карты</th>
                            <th>PIN</th>
                        </tr>
                        </thead>
                        <tbody>
                        {cardsList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export  default  withRouter(Cards);