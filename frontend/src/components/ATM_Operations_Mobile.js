import React, {Component} from 'react';
import {Container} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';
import {Button} from "@material-ui/core";

class ATM_Operations_Mobile extends Component{

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

    onMTCPayment(){
        this.onPayment('MTC')
    }

    onPayment(name){
        this.props.history.push({pathname: '/ATM/Operations/Mobile/Payment', state: {card: this.state.card, mobile: name}})
    }

    onLifePayment(e){
        this.onPayment('Life')
    }

    onA1Payment(e){
        this.onPayment('A1')
    }
    
    onBack(e){
        this.props.history.push({pathname: '/ATM/Operations/', state: {card: this.state.card}})
    }

    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid style={label_center}>
                    <h1>Банкомат</h1>
                    <h5 style={margin_cards}>Выберите мобильного оператора:</h5>
                    <Button style={margin_cards} color="primary" onClick={this.onMTCPayment.bind(this)}>MTC</Button>
                    <br/>
                    <Button style={margin_cards} color="primary" nClick={this.onLifePayment.bind(this)}>Life</Button>
                    <br/>
                    <Button style={margin_cards} color="primary" onClick={this.onA1Payment.bind(this)}>A1</Button>
                    <br/>
                    <Button style={margin_cards} color="primary" onClick={this.onBack.bind(this)}>Назад</Button>
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

export  default  withRouter(ATM_Operations_Mobile);