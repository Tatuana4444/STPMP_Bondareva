import React, {Component} from 'react';
import axios from "axios";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';
import {Button} from "@material-ui/core";

class ATM_Operations_MobilePaymentPay extends Component{

    constructor(props) {
        super(props);
        console.log(props)
        if(props.history.location.state !== undefined && props.history.location.state.card !== undefined) {
            this.state = {
                card: props.history.location.state.card,
                mobile: props.history.location.state.mobile,
                mobileNumber: props.history.location.state.mobileNumber,
                money: 0
            };
        }
        else {
            this.state = {
                card: '',
                mobile: '',
                mobileNumber: '',
                money: 0
            };
            this.props.history.push('/ATM/')
        }

        this.onSubmit = this.onSubmit.bind(this);
    }

    onBack(e){
        this.props.history.push({pathname: '/ATM/Operations/', state: {card: this.state.card}})
    }

    onSubmit(e){
        e.preventDefault();
        if(confirm(`Вы уверены, что хотите оплать ${this.state.money} на телефон ${this.state.mobileNumber} оператора ${this.state.mobile}`)) {


            axios.put('/cards/mobile/' + this.state.mobile + '/'+ this.state.mobileNumber + '/' + this.state.money, this.state.card)
                .then(r => {
                    if (r.data) {
                        alert("Оплачено")
                        axios.put('/cards/mobile/' + this.state.mobile+ '/'+ this.state.mobileNumber + '/download/' + this.state.money, this.state.card)
                            .then(r => {
                                var blob = new Blob([r.data], {type: "text/plain;charset=utf-8"});
                                saveAs(blob, "check.txt")
                            })
                            .catch(r => {
                                alert(`Не удалось выполнить соединение с банком. Попробуйте позже`);
                                this.props.history.push('/ATM')
                            })
                    } else {
                        alert(`Не достаточно средств на карте.`);
                    }
                    this.props.history.push({pathname: '/ATM/Operations/', state: {card: this.state.card}})
                })
                .catch(r => {
                    alert(`Не удалось выполнить соединение с банком. Попробуйте позже`);
                    this.props.history.push('/ATM')
                })
        }
    }

    moneyHandle(e){
        this.setState({money: e.target.value})
    }



    render() {

        return (
            <div>
                <AppNavbar/>
                <Container fluid style={label_center}>
                    <h1>Банкомат</h1>
                    <form onSubmit={this.onSubmit.bind(this)}>
                        <div style={form_cards}>
                            <label form="money">Сумма:</label>
                            <br/>
                            <input
                                style ={input_cards}
                                type = "number"
                                name = "money"
                                id="money"
                                value={this.state.money}
                                onChange={this.moneyHandle.bind(this)}
                            />
                            <br/>
                            <Button color="primary" type="submit">Далее</Button>
                            <br/>
                            <Button style={margin_cards} color="primary" onClick={this.onBack.bind(this)}>Назад</Button>
                            <br/>
                            <Button color="primary" style={margin_cards}><Link to="/ATM">Вернуть карту</Link></Button>
                        </div>
                    </form>
                </Container>
            </div>
        );
    }
}

const input_cards = {
    width: '30%',
    marginTop: '25px'
}

const label_center = {
    textAlign: 'center'
}

const form_cards = {
    marginTop: '50px'
}
const margin_cards = {
    marginTop: '20px'
}
export  default  withRouter(ATM_Operations_MobilePaymentPay);