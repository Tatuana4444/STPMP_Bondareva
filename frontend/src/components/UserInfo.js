import React, {Component} from 'react';
import axios from "axios";
import {Button, ButtonGroup} from "@material-ui/core";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';

class UserInfo extends Component{

    constructor(props) {
        super(props);
        this.state = {users: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        axios.get('/users')
            .then(response =>
            {
                this.setState({users:response.data});
            })

    }

    remove = (id) =>{
        if(confirm("Вы уверены, что хотите удалть клиента?")) {
            axios.delete(`/users/${id}`)
                .then(
                    response => this.setState( //Updating UI
                        {
                            users: [...this.state.users.filter(
                                user => user.id !== id
                            )]
                        }
                    )
                );
        }
    }

    render() {
        const {users, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const userList = users.map(user => {
            return <tr key={user.id}>
                <td><Link to={"/clients/" + user.id}>{user.lastname}</Link></td>
                <td>{user.firstname}</td>
                <td>{user.surname}</td>
                <td>{user.birthday}</td>
                <td>{user.series}</td>
                <td>{user.passportNumber}</td>
                <td>{user.identifiedNumber}</td>
                <td>{user.homePhone}</td>
                <td>{user.mobilePhone}</td>
                <td>{user.email}</td>
                <td>
                    <Link to={"/clients/" + user.id}>Edit</Link>
                    |
                    <Link to="/" onClick={() => this.remove(user.id)}>Delete</Link>
                </td>
            </tr>
        });
        return (
            <div>
                <AppNavbar/>
                <Container fluid>

                    <h3>Список клиентов</h3>
                    <Table className="table">
                        <thead>
                        <tr>
                            <th>Фамилия</th>
                            <th>Имя</th>
                            <th>Отчество</th>
                            <th>Дата рождения</th>
                            <th>Серия паспорта</th>
                            <th>№ паспорта</th>
                            <th>Идент. номер</th>
                            <th>Телефон дом</th>
                            <th>Телефон моб</th>
                            <th>E-mail</th>
                        </tr>
                        </thead>
                        <tbody>
                        {userList}
                        </tbody>
                    </Table>
                    <div className="float-right">
                        <Link className="btn btn-default" to="/clients/new">Добавить клиента</Link>
                    </div>
                </Container>
            </div>
        );
    }
}

export  default  withRouter(UserInfo);