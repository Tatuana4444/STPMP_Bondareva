import React, {Component} from 'react';
import axios from "axios";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';

class ContractOperationsInfo extends Component{

    constructor(props) {
        super(props);
        this.state = {contractOperations: []};
    }

    componentDidMount() {
        if (this.props.match.params.id !== undefined) {
            axios.get(`/operationHistories/contract/${this.props.match.params.id}`)
                .then(response => {
                    this.setState({contractOperations: response.data});
                })
        }
        else {
            axios.get(`/operationHistories`)
                .then(response => {
                    this.setState({contractOperations: response.data});
                })
        }

    }


    render() {
        const {contractOperations, isLoading} = this.state;
        const title = <h2>{this.props.match.params.id ? `Список операций контракта №${this.props.match.params.id}` : 'Список операций'}</h2>;
        if (isLoading) {
            return <p>Loading...</p>;
        }

        const contractOperationsList = contractOperations.map(contractOperation => {
            return <tr key={contractOperation.id}>
                <td><Link to={"/accounts/" + contractOperation.id}>{contractOperation.fromAccountNum}</Link></td>
                <td><Link to={"/accounts/" + contractOperation.id}>{contractOperation.toAccountNum}</Link></td>
                <td>{contractOperation.moneyTransfer}</td>
                <td>{contractOperation.comments}</td>
                <td>{contractOperation.date}</td>
                <td>{contractOperation.currency}</td>
                </tr>
        });
        return (
            <div>
                <AppNavbar/>
                <Container fluid>

                    <h3>{title}</h3>
                    <Table className="table">
                        <thead>
                        <tr>
                            <th>Со счета</th>
                            <th>На счет</th>
                            <th>Сумма</th>
                            <th>Комментарий</th>
                            <th>Дата</th>
                        </tr>
                        </thead>
                        <tbody>
                        {contractOperationsList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export  default  withRouter(ContractOperationsInfo);