import React, {Component} from 'react';
import axios from "axios";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';
import InputMask from "react-input-mask";
import {NotificationManager} from "react-notifications";
import {Button} from "@material-ui/core";

class ATM extends Component{

    constructor(props) {
        super(props);
        this.state = {cardNumber:'',
            cardNumberDirty:false,
            cardNumberError:'Поле фамилия не может быть пустым'
        };
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
    }

    onSubmit(e){
        e.preventDefault();
        if(this.state.cardNumber)
        {
            this.props.history.push('/ATM/' + this.state.cardNumber)

        }


    }

    NumberHandler =(e)=>{
        this.setState({cardNumber: e.target.value});
        let error = ''

        if(e.target.value !== '' && !/^[0-9]{16}$/.test((e.target.value)))
        {
            error = "Не корректный формат поля Номера карты"
        }

        this.setState({cardNumberError: error})
    }

    blurHandler =(e) => {
        if (e.target.name === 'cardNumber') {
            this.setState({cardNumberDirty: true})
            if (this.state.cardNumberError) {
                NotificationManager.error(this.state.cardNumberError)
            }
        } else {
            this.setState({cardNumberDirty: false})
        }
    }

        render() {

        return (
            <div>
                <AppNavbar/>
                <Container fluid style={label_center}>
                    <form onSubmit={this.onSubmit.bind(this)}>
                    <h1>Банкомат</h1>
                    <div style={form_cards}>
                        <label form="cardNumber">Введите номер карты:</label>
                        <br/>
                        <InputMask
                            style ={input_cards}
                            name = "cardNumber"
                            id="cardNumber"
                            mask="9999999999999999"
                            maskChar=""
                            alwaysShowMask
                            value={this.state.cardNumber}
                            onBlur={this.blurHandler}
                            onChange={this.NumberHandler}
                        />
                    </div>
                    <Button color="secondary" type="submit" disabled={this.state.cardNumberError}>Далее</Button>
                    </form>
                </Container>
            </div>
        );
    }
}

const label_center = {
 textAlign: 'center'
}

const input_cards = {
    width: '30%',
    marginTop: '25px'
}

const form_cards = {
    marginTop: '50px'
}

export  default  withRouter(ATM);