import React, {Component} from 'react';
import axios from "axios";
import {Container, Table} from "reactstrap";
import AppNavbar from "../AppNavbar";
import {Link, withRouter} from 'react-router-dom';
import InputMask from "react-input-mask";
import {NotificationManager} from "react-notifications";
import {Button} from "@material-ui/core";

class ATM_Operations_MobilePayment extends Component{


    constructor(props) {
        super(props);
        if(props.history.location.state !== undefined && props.history.location.state.card !== undefined) {
            this.state = {
                card: props.history.location.state.card,
                mobile: props.history.location.state.mobile,
                mobileNum: '',
                mobilePhoneDirty:false,
                mobilePhoneError:'Поле мобильный телефон не может быть пустым',
            };
        }
        else {
            this.props.history.push('/ATM/')
        }

        this.errorCount = 0;
        this.onSubmit = this.onSubmit.bind(this);
    }

    onSubmit(e){
        e.preventDefault();
        console.log(this.state)
        this.props.history.push({pathname: '/ATM/Operations/Mobile/Payment/Pay',
            state: {card: this.state.card, mobile:  this.state.mobile, mobileNumber: this.state.mobileNum}})
    }

    componentDidMount() {
    }

    mobilePhoneHandler =(e)=>{
        console.log(this.state)
        console.log(this.state.mobileNum)
        console.log(e.target.value)
        this.setState({mobileNum: e.target.value})
        let error = ''
        if(e.target.value === ''){
            error = "Поле мобильный телефон не может быть пустым"
        }
        if(e.target.value !== '+375(' && !/^\+375\([0-9]{2}\)-[0-9]{3}-[0-9]{2}-[0-9]{2}$/.test(e.target.value))
        {
            error = "Не корректный формат мобильного телефона "
        }

        this.setState({mobilePhoneError: error})
    }

    blurHandler =(e) => {
        if (e.target.name === 'mobileNum') {
            this.setState({pinDirty: true})
            if (this.state.mobilePhoneError) {
                NotificationManager.error(this.state.mobilePhoneError)
            }
        } else {
            this.setState({mobilePhoneDirty: false})
        }
    }

    onBack(e){
        this.props.history.push({pathname: '/ATM/Operations/', state: {card: this.state.card}})
    }

    render() {

        return (
            <div>
                <AppNavbar/>
                <Container fluid style={label_center}>
                    <form onSubmit={this.onSubmit.bind(this)}>
                        <h1>Банкомат</h1>
                        <div style={form_cards}>
                            <label form="mobilePhone">Введите PIN:</label>
                            <br/>
                            <InputMask
                                style ={input_cards}
                                type = "text"
                                name = "mobilePhone"
                                id="mobilePhone"
                                mask="+375(99)-999-99-99"
                                maskChar=""
                                alwaysShowMask
                                value={this.state.mobileNum}
                                onBlur={this.blurHandler}
                                onChange={this.mobilePhoneHandler.bind(this)}
                            />
                        </div>
                        <Button color="primary" type="submit" disabled={this.state.mobilePhoneError}>Далее</Button>
                        <br/>
                        <Button style={margin_cards} color="primary" onClick={this.onBack.bind(this)}>Назад</Button>
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

export  default  withRouter(ATM_Operations_MobilePayment);