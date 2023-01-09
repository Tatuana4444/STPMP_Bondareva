import React, {Component} from 'react';
import {Container} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';
import {Button} from "@material-ui/core";

class ATM_Operations extends Component{

    constructor(props) {
        super(props);
        if(props.history.location.state !== undefined && props.history.location.state.card !== undefined) {
            this.state = {
                card: props.history.location.state.card
            };
        }
        else {
            this.props.history.push('/ATM/')
        }
    }

    onCheckRemainder(){
        this.props.history.push({pathname: '/ATM/Operations/Remainder', state: {card: this.state.card}})
    }

    onWithdraw(e){
        this.props.history.push({pathname: '/ATM/Operations/Withdraw', state: {card: this.state.card}})
    }

    onMobile(e){
        this.props.history.push({pathname: '/ATM/Operations/Mobile', state: {card: this.state.card}})
    }

    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid style={label_center}>
                    <h1>Банкомат</h1>
                    <h5 style={margin_cards}>Выбирите операцию:</h5>
                    <Button style={margin_cards} color="primary" type="submit" onClick={this.onCheckRemainder.bind(this)}>Состояние счета</Button>
                    <br/>
                    <Button style={margin_cards} color="primary" type="submit" onClick={this.onWithdraw.bind(this)}>Снять со счета</Button>
                    <br/>
                    <Button style={margin_cards} color="primary" type="submit" onClick={this.onMobile.bind(this)}>Оплатить мобильго оператора</Button>
                    <br/>
                    <Button style={margin_cards} color="primary" ><Link to="/ATM">Вернуть карту</Link></Button>
                </Container>
            </div>
        );
    }
}

const label_center = {
    textAlign: 'center'
}

const margin_cards = {
    marginTop: '20px'
}

export  default  withRouter(ATM_Operations);