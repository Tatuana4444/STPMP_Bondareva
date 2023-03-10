import React, {Component} from 'react';
import axios from "axios";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';

class ContractInfo extends Component{

    constructor(props) {
        super(props);
        this.state = {contracts: [], isWithdrawalThroughCash: false};
    }

    componentDidMount() {
        axios.get('/contracts')
            .then(response =>
            {
                this.setState({contracts:response.data});
            })
        axios.get('/config/isWithdrawalThroughCash')
            .then(response =>
            {
                this.setState({isWithdrawalThroughCash:response.data});
            })

    }
    setWithdrawalThroughCash(){
        axios.put('/config/setWithdrawalThroughCash/' + !this.state.isWithdrawalThroughCash)
            .then(response =>
            {
                this.setState({isWithdrawalThroughCash:response.data});
            })
    }

    render() {
        const {contracts, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const contractList = contracts.map(contract => {
            return <tr key={contract.id}>
                <td><Link to={"/agreements/details/" + contract.id}>{contract.id}</Link></td>
                <td>{contract.user}</td>
                <td>{contract.startDate}</td>
                <td>{contract.endDate}</td>
                <td>{contract.contractType}</td>
                <td>{contract.currency}</td>
                <td>{contract.sum}</td>
                <td>{contract.percent}</td>
                <td><Link to={"/accounts/" + contract.currentAccount}>{contract.currentAccount}</Link></td>
                <td><Link to={"/accounts/" + contract.persentAccount}>{contract.persentAccount}</Link></td>
                <td>
                    <Link to={"/agreements/details/" + contract.id}>View</Link>
                </td>
            </tr>
        });
        return (
            <div>
                <AppNavbar/>
                <Container fluid>

                    <h3>???????????? ????????????????????</h3>
                    <Table className="table">
                        <thead>
                        <tr>
                            <th>?????????? ????????????????</th>
                            <th>????????????</th>
                            <th>???????? ????????????</th>
                            <th>???????? ??????????????????</th>
                            <th>?????? ????????????????</th>
                            <th>?????? ????????????</th>
                            <th>?????????? ????????????</th>
                            <th>????????????????</th>
                            <th>?????????? ?????????????????? ??????????</th>
                            <th>?????????? ???????????????????? ??????????</th>
                        </tr>
                        </thead>
                        <tbody>
                        {contractList}
                        </tbody>
                    </Table>
                    <div className="float-right">
                        <Link className="btn btn-default" to="/agreements/new">???????????????? ????????????????</Link>
                    </div>
                    <input
                        type="checkbox"
                        id="subscribeNews"
                        name="subscribe"
                        checked={this.state.isWithdrawalThroughCash}
                        onClick={this.setWithdrawalThroughCash.bind(this)}
                        value="newsletter"/>
                    <label htmlFor="subscribeNews">???????????????? ?????????? ???????????????? ?????????? ??????????</label>
                </Container>
            </div>
        );
    }
}

export  default  withRouter(ContractInfo);