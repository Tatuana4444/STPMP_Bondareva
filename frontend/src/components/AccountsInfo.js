import React, {Component} from 'react';
import axios from "axios";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';

class ContractInfo extends Component{

    constructor(props) {
        super(props);
        this.state = {accounts: []};
    }

    componentDidMount() {
        if (this.props.match.params.id === undefined) {
            axios.get('/accounts')
                .then(response => {
                    this.setState({accounts: response.data});
                })
        }
        else
        {
            axios.get(`/accounts/${this.props.match.params.id}`)
                .then(response => {
                    this.setState({accounts: [response.data]});
                })
        }
    }

    render() {
        const {accounts, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const accountList = accounts.map(account => {
            console.log(account)
            return <tr key={account.id}>
                <td>{account.accountNumber}</td>
                <td>{account.accountTypeId}</td>
                <td>{account.planAccountNumber}</td>
                <td>{account.debit}</td>
                <td>{account.credit}</td>
                <td>{account.saldo}</td>
                <td>{account.user}</td>
                <td>{account.contractId}</td>
            </tr>
        });
        return (
            <div>
                <AppNavbar/>
                <Container fluid>

                    <h3>План счетов</h3>
                    <Table className="table">
                        <thead>
                        <tr>
                            <th>Номер счета </th>
                            <th>Активность счета</th>
                            <th>Код счета</th>
                            <th>Дебет</th>
                            <th>Кредит</th>
                            <th>Сальдо</th>
                            <th>ФИО клиента</th>
                            <th>Номер договора</th>
                        </tr>
                        </thead>
                        <tbody>
                        {accountList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export  default  withRouter(ContractInfo);