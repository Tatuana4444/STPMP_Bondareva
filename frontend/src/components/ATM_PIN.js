import React, {Component} from 'react';
import axios from "axios";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';
import InputMask from "react-input-mask";
import {NotificationManager} from "react-notifications";
import {Button} from "@material-ui/core";

class ATM extends Component{

    errorCount = 0;
    emptyCard = {
        id:'',
        number:'',
        pin:'',
    };

    constructor(props) {
        super(props);
        this.state = {
            card: this.emptyCard,
            pinDirty:false,
            pinError:'Поле фамилия не может быть пустым'
        };

        this.errorCount = 0;
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(e){
        e.preventDefault();
        axios.put('/cards/check/', this.state.card)
            .then(r  => {
                if(r.data === true) {
                    this.props.history.push({pathname: '/ATM/Operations/', state: {card: this.state.card}})
                }
                else {
                    this.errorHandler()
                }
            })
            .catch(r =>
            {
                this.errorHandler()
            })
    }

    errorHandler(){
        this.errorCount++;
        alert(`Неверный пароль. Осталось попыток:${3 - this.errorCount}`);
        if(this.errorCount === 3){
            this.props.history.push('/ATM')
        }
    }

    componentDidMount() {
        let card = {...this.state.card};
        card.number = this.props.match.params.id;
        this.setState({card});
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let card = {...this.state.card};
        card[name] = value;
        this.setState({card});
    }

    pinHandler =(e)=>{
        this.handleChange(e)
        let error = ''

        if(e.target.value !== '' && !/^[0-9]{4}$/.test((e.target.value)))
        {
            error = "Не корректный формат поля PIN"
        }

        this.setState({pinError: error})
    }

    blurHandler =(e) => {
        if (e.target.name === 'pin') {
            this.setState({pinDirty: true})
            if (this.state.pinError) {
                NotificationManager.error(this.state.pinError)
            }
        } else {
            this.setState({pinDirty: false})
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
                        <label form="pin">Введите PIN:</label>
                        <br/>
                        <InputMask
                            style ={input_cards}
                            type = "text"
                            name = "pin"
                            id="pin"
                            mask="9999"
                            maskChar=""
                            alwaysShowMask
                            value={this.state.card.pin}
                            onBlur={this.blurHandler}
                            onChange={this.pinHandler}
                        />
                    </div>
                    <Button color="primary" type="submit" disabled={this.state.pinError}>Далее</Button>
                    <br/>
                    <Button  style={margin_cards} color="primary" ><Link to="/ATM">Вернуть карту</Link></Button>
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

const margin_cards = {
    marginTop: '20px'
}

export  default  withRouter(ATM);